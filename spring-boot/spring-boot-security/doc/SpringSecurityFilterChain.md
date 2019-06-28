# Spring Security SpringSecurityFilterChain

## SpringSecurityFilterChain
### Java Configuration
```
public abstract class AbstractSecurityWebApplicationInitializer implements WebApplicationInitializer {
	public static final String DEFAULT_FILTER_NAME = "springSecurityFilterChain";

	public final void onStartup(ServletContext servletContext) throws ServletException {
		beforeSpringSecurityFilterChain(servletContext);
		if (this.configurationClasses != null) {
			AnnotationConfigWebApplicationContext rootAppContext = new AnnotationConfigWebApplicationContext();
			rootAppContext.register(this.configurationClasses);
			servletContext.addListener(new ContextLoaderListener(rootAppContext));
		}
		if (enableHttpSessionEventPublisher()) {
			servletContext.addListener("org.springframework.security.web.session.HttpSessionEventPublisher");
		}
		servletContext.setSessionTrackingModes(getSessionTrackingModes());
		insertSpringSecurityFilterChain(servletContext);
		afterSpringSecurityFilterChain(servletContext);
	}

	/**
	 * Registers the springSecurityFilterChain
	 */
	private void insertSpringSecurityFilterChain(ServletContext servletContext) {
		String filterName = DEFAULT_FILTER_NAME;
		DelegatingFilterProxy springSecurityFilterChain = new DelegatingFilterProxy(filterName);
		String contextAttribute = getWebApplicationContextAttribute();
		if (contextAttribute != null) {
			springSecurityFilterChain.setContextAttribute(contextAttribute);
		}
		registerFilter(servletContext, true, filterName, springSecurityFilterChain);
	}

	private final void registerFilter(ServletContext servletContext, boolean insertBeforeOtherFilters, String filterName, Filter filter) {
		Dynamic registration = servletContext.addFilter(filterName, filter);
		if (registration == null) {
			throw new IllegalStateException("Duplicate Filter registration for '" + filterName + "'. Check to ensure the Filter is only configured once.");
		}
		registration.setAsyncSupported(isAsyncSecuritySupported());
		EnumSet<DispatcherType> dispatcherTypes = getSecurityDispatcherTypes();
		registration.addMappingForUrlPatterns(dispatcherTypes, !insertBeforeOtherFilters, "/*");
	}
}
```

### XML Configuration
```
<filter>
	<filter-name>springSecurityFilterChain</filter-name>
	<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
</filter>
<filter-mapping>
	<filter-name>springSecurityFilterChain</filter-name>
	<url-pattern>/*</url-pattern>
</filter-mapping>
```

### SpringBoot Configuration
```
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties
@ConditionalOnClass({ AbstractSecurityWebApplicationInitializer.class, SessionCreationPolicy.class })
@AutoConfigureAfter(SecurityAutoConfiguration.class)
public class SecurityFilterAutoConfiguration {
	// springSecurityFilterChain
	private static final String DEFAULT_FILTER_NAME = AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME;

	@Bean
	@ConditionalOnBean(name = DEFAULT_FILTER_NAME)
	public DelegatingFilterProxyRegistrationBean securityFilterChainRegistration(SecurityProperties securityProperties) {
		DelegatingFilterProxyRegistrationBean registration = new DelegatingFilterProxyRegistrationBean(DEFAULT_FILTER_NAME);
		registration.setOrder(securityProperties.getFilterOrder());
		registration.setDispatcherTypes(getDispatcherTypes(securityProperties));
		return registration;
	}

	@Bean
	@ConditionalOnMissingBean
	public SecurityProperties securityProperties() {
		return new SecurityProperties();
	}
}
```

### DelegatingFilterProxy
```
package org.springframework.web.filter;
public class DelegatingFilterProxy extends GenericFilterBean {
	private WebApplicationContext webApplicationContext;
	private String targetBeanName;
	private volatile Filter delegate;
	private final Object delegateMonitor = new Object();

	public DelegatingFilterProxy(String targetBeanName, WebApplicationContext wac) {
		Assert.hasText(targetBeanName, "target Filter bean name must not be null or empty");
		this.setTargetBeanName(targetBeanName);
		this.webApplicationContext = wac;
		if (wac != null) {
			this.setEnvironment(wac.getEnvironment());
		}
	}

	@Override
	protected void initFilterBean() throws ServletException {
		synchronized (this.delegateMonitor) {
			if (this.delegate == null) {
				// If no target bean name specified, use filter name.
				if (this.targetBeanName == null) {
					this.targetBeanName = getFilterName();
				}
				// Fetch Spring root application context and initialize the delegate early, if possible. If the root application context will be started after this filter proxy, we'll have to resort to lazy initialization.
				WebApplicationContext wac = findWebApplicationContext();
				if (wac != null) {
					this.delegate = initDelegate(wac);
				}
			}
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		// Lazily initialize the delegate if necessary.
		Filter delegateToUse = this.delegate;
		if (delegateToUse == null) {
			synchronized (this.delegateMonitor) {
				if (this.delegate == null) {
					WebApplicationContext wac = findWebApplicationContext();
					if (wac == null) {
						throw new IllegalStateException("No WebApplicationContext found: " + "no ContextLoaderListener or DispatcherServlet registered?");
					}
					this.delegate = initDelegate(wac);
				}
				delegateToUse = this.delegate;
			}
		}
		// Let the delegate perform the actual doFilter operation.
		invokeDelegate(delegateToUse, request, response, filterChain);
	}

	protected Filter initDelegate(WebApplicationContext wac) throws ServletException {
		Filter delegate = wac.getBean(getTargetBeanName(), Filter.class);
		if (isTargetFilterLifecycle()) {
			delegate.init(getFilterConfig());
		}
		return delegate;
	}

	protected void invokeDelegate(Filter delegate, ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		delegate.doFilter(request, response, filterChain);
	}
}
```

### FilterChainProxy
```
package org.springframework.security.web;
public class FilterChainProxy extends GenericFilterBean {
	private List<SecurityFilterChain> filterChains;
	
	public FilterChainProxy(SecurityFilterChain chain) {
		this(Arrays.asList(chain));
	}

	public FilterChainProxy(List<SecurityFilterChain> filterChains) {
		this.filterChains = filterChains;
	}

	@Override
	public void afterPropertiesSet() {
		filterChainValidator.validate(this);
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		boolean clearContext = request.getAttribute(FILTER_APPLIED) == null;
		if (clearContext) {
			try {
				request.setAttribute(FILTER_APPLIED, Boolean.TRUE);
				doFilterInternal(request, response, chain);
			}
			finally {
				SecurityContextHolder.clearContext();
				request.removeAttribute(FILTER_APPLIED);
			}
		}
		else {
			doFilterInternal(request, response, chain);
		}
	}

	private void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		FirewalledRequest fwRequest = firewall.getFirewalledRequest((HttpServletRequest) request);
		HttpServletResponse fwResponse = firewall.getFirewalledResponse((HttpServletResponse) response);
		List<Filter> filters = getFilters(fwRequest);
		if (filters == null || filters.size() == 0) {
			if (logger.isDebugEnabled()) {
				logger.debug(UrlUtils.buildRequestUrl(fwRequest) + (filters == null ? " has no matching filters" : " has an empty filter list"));
			}
			fwRequest.reset();
			chain.doFilter(fwRequest, fwResponse);
			return;
		}
		VirtualFilterChain vfc = new VirtualFilterChain(fwRequest, chain, filters);
		vfc.doFilter(fwRequest, fwResponse);
	}

	/**
	 * Returns the first filter chain matching the supplied URL.
	 */
	private List<Filter> getFilters(HttpServletRequest request) {
		for (SecurityFilterChain chain : filterChains) {
			if (chain.matches(request)) {
				return chain.getFilters();
			}
		}
		return null;
	}
}
```

### SecurityFilterChain
```
package org.springframework.security.web;
public final class DefaultSecurityFilterChain implements SecurityFilterChain {
	private final RequestMatcher requestMatcher;
	private final List<Filter> filters;

	public List<Filter> getFilters() {
		return filters;
	}

	public boolean matches(HttpServletRequest request) {
		return requestMatcher.matches(request);
	}
}
```

### WebSecurity
```
@Configuration
public class WebSecurityConfiguration implements ImportAware, BeanClassLoaderAware {
	@Bean(name = AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME)
	public Filter springSecurityFilterChain() throws Exception {
		boolean hasConfigurers = webSecurityConfigurers != null && !webSecurityConfigurers.isEmpty();
		if (!hasConfigurers) {
			WebSecurityConfigurerAdapter adapter = objectObjectPostProcessor.postProcess(new WebSecurityConfigurerAdapter() {});
			webSecurity.apply(adapter);
		}
		return webSecurity.build();
	}
}
```
```
package org.springframework.security.config.annotation.web.builders;
public final class WebSecurity extends AbstractConfiguredSecurityBuilder<Filter, WebSecurity> implements SecurityBuilder<Filter>, ApplicationContextAware {
	@Override
	protected Filter performBuild() throws Exception {
		Assert.state(!securityFilterChainBuilders.isEmpty(), "At least one SecurityBuilder<? extends SecurityFilterChain> needs to be specified. Typically this done by adding a @Configuration that extends WebSecurityConfigurerAdapter. More advanced users can invoke " + WebSecurity.class.getSimpleName() + ".addSecurityFilterChainBuilder directly");
		int chainSize = ignoredRequests.size() + securityFilterChainBuilders.size();
		List<SecurityFilterChain> securityFilterChains = new ArrayList<SecurityFilterChain>(chainSize);
		for (RequestMatcher ignoredRequest : ignoredRequests) {
			securityFilterChains.add(new DefaultSecurityFilterChain(ignoredRequest));
		}
		for (SecurityBuilder<? extends SecurityFilterChain> securityFilterChainBuilder : securityFilterChainBuilders) {
			securityFilterChains.add(securityFilterChainBuilder.build());
		}
		FilterChainProxy filterChainProxy = new FilterChainProxy(securityFilterChains);
		if (httpFirewall != null) {
			filterChainProxy.setFirewall(httpFirewall);
		}
		filterChainProxy.afterPropertiesSet();
		Filter result = filterChainProxy;
		if (debugEnabled) {
			logger.warn("\n\n" + "********************************************************************\n" + "**********        Security debugging is enabled.       *************\n" + "**********    This may include sensitive information.  *************\n" + "**********      Do not use in a production system!     *************\n" + "********************************************************************\n\n");
			result = new DebugFilter(filterChainProxy);
		}
		postBuildAction.run();
		return result;
	}
}
```

## References
- [Spring Security(六)—SpringSecurityFilterChain加载流程深度解析](https://www.cnkirito.moe/spring-security-7/)