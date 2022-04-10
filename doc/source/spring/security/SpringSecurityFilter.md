# Spring Security Filter

## Filter
### Overview
```
DEBUG 8348 --- [nio-8072-exec-9] o.s.security.web.FilterChainProxy:
/ at position 1 of 12 in additional filter chain; firing Filter: 'WebAsyncManagerIntegrationFilter'
/ at position 2 of 12 in additional filter chain; firing Filter: 'SecurityContextPersistenceFilter'
/ at position 3 of 12 in additional filter chain; firing Filter: 'HeaderWriterFilter'
/ at position 4 of 12 in additional filter chain; firing Filter: 'LogoutFilter'
/ at position 5 of 12 in additional filter chain; firing Filter: 'UsernamePasswordAuthenticationFilter'
/ at position 6 of 12 in additional filter chain; firing Filter: 'BasicAuthenticationFilter'
/ at position 7 of 12 in additional filter chain; firing Filter: 'RequestCacheAwareFilter'
/ at position 8 of 12 in additional filter chain; firing Filter: 'SecurityContextHolderAwareRequestFilter'
/ at position 9 of 12 in additional filter chain; firing Filter: 'AnonymousAuthenticationFilter'
/ at position 10 of 12 in additional filter chain; firing Filter: 'SessionManagementFilter'
/ at position 11 of 12 in additional filter chain; firing Filter: 'ExceptionTranslationFilter'
/ at position 12 of 12 in additional filter chain; firing Filter: 'FilterSecurityInterceptor'
```

### SecurityContextPersistenceFilter
```
package org.springframework.security.web.context;
public class SecurityContextPersistenceFilter extends GenericFilterBean {
	static final String FILTER_APPLIED = "__spring_security_scpf_applied";
	private SecurityContextRepository repo;
	
	public SecurityContextPersistenceFilter() {
		this(new HttpSessionSecurityContextRepository());
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if (request.getAttribute(FILTER_APPLIED) != null) {
			// ensure that filter is only applied once per request
			chain.doFilter(request, response);
			return;
		}
		final boolean debug = logger.isDebugEnabled();
		request.setAttribute(FILTER_APPLIED, Boolean.TRUE);
		if (forceEagerSessionCreation) {
			HttpSession session = request.getSession();
			if (debug && session.isNew()) {
				logger.debug("Eagerly created session: " + session.getId());
			}
		}
		HttpRequestResponseHolder holder = new HttpRequestResponseHolder(request, response);
		SecurityContext contextBeforeChainExecution = repo.loadContext(holder);
		try {
			SecurityContextHolder.setContext(contextBeforeChainExecution);
			chain.doFilter(holder.getRequest(), holder.getResponse());
		}
		finally {
			SecurityContext contextAfterChainExecution = SecurityContextHolder.getContext();
			// Crucial removal of SecurityContextHolder contents - do this before anything else.
			SecurityContextHolder.clearContext();
			repo.saveContext(contextAfterChainExecution, holder.getRequest(), holder.getResponse());
			request.removeAttribute(FILTER_APPLIED);
			if (debug) {
				logger.debug("SecurityContextHolder now cleared, as request processing completed");
			}
		}
	}
}
```
```
package org.springframework.security.web.context;
public class HttpSessionSecurityContextRepository implements SecurityContextRepository {
	/**
	 * The default key under which the security context will be stored in the session.
	 */
	public static final String SPRING_SECURITY_CONTEXT_KEY = "SPRING_SECURITY_CONTEXT";
	private String springSecurityContextKey = SPRING_SECURITY_CONTEXT_KEY;

	public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
		HttpServletRequest request = requestResponseHolder.getRequest();
		HttpServletResponse response = requestResponseHolder.getResponse();
		HttpSession httpSession = request.getSession(false);
		SecurityContext context = readSecurityContextFromSession(httpSession);
		if (context == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("No SecurityContext was available from the HttpSession: " + httpSession + ". " + "A new one will be created.");
			}
			context = generateNewContext();
		}
		SaveToSessionResponseWrapper wrappedResponse = new SaveToSessionResponseWrapper(response, request, httpSession != null, context);
		requestResponseHolder.setResponse(wrappedResponse);
		if (isServlet3) {
			requestResponseHolder.setRequest(new Servlet3SaveToSessionRequestWrapper(request, wrappedResponse));
		}
		return context;
	}
}
```

### UsernamePasswordAuthenticationFilter
![UsernamePasswordAuthenticationFilter-min](https://s0.wailian.download/2019/06/26/UsernamePasswordAuthenticationFilter-min.jpg)

```
package org.springframework.security.web.authentication;
public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		if (username == null) {
			username = "";
		}
		if (password == null) {
			password = "";
		}
		username = username.trim();
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}
}
```
```
public abstract class AbstractAuthenticationProcessingFilter extends GenericFilterBean implements ApplicationEventPublisherAware, MessageSourceAware {
	private AuthenticationManager authenticationManager;
	private RememberMeServices rememberMeServices = new NullRememberMeServices();
	private RequestMatcher requiresAuthenticationRequestMatcher;
	private AuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
	private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if (!requiresAuthentication(request, response)) {
			chain.doFilter(request, response);
			return;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Request is to process authentication");
		}
		Authentication authResult;
		try {
			authResult = attemptAuthentication(request, response);
			if (authResult == null) {
				// return immediately as subclass has indicated that it hasn't completed authentication
				return;
			}
			sessionStrategy.onAuthentication(authResult, request, response);
		}
		catch (InternalAuthenticationServiceException failed) {
			logger.error("An internal error occurred while trying to authenticate the user.", failed);
			unsuccessfulAuthentication(request, response, failed);
			return;
		}
		catch (AuthenticationException failed) {
			// Authentication failed
			unsuccessfulAuthentication(request, response, failed);
			return;
		}
		// Authentication success
		if (continueChainBeforeSuccessfulAuthentication) {
			chain.doFilter(request, response);
		}
		successfulAuthentication(request, response, chain, authResult);
	}
}
```

### AnonymousAuthenticationFilter
```
package org.springframework.security.web.authentication;
public class AnonymousAuthenticationFilter extends GenericFilterBean implements InitializingBean {
	private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
	private String key;
	private Object principal;
	private List<GrantedAuthority> authorities;

	public AnonymousAuthenticationFilter(String key) {
		this(key, "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
	}

	public AnonymousAuthenticationFilter(String key, Object principal, List<GrantedAuthority> authorities) {
		Assert.hasLength(key, "key cannot be null or empty");
		Assert.notNull(principal, "Anonymous authentication principal must be set");
		Assert.notNull(authorities, "Anonymous authorities must be set");
		this.key = key;
		this.principal = principal;
		this.authorities = authorities;
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			SecurityContextHolder.getContext().setAuthentication(createAuthentication((HttpServletRequest) req));
			if (logger.isDebugEnabled()) {
				logger.debug("Populated SecurityContextHolder with anonymous token: '" + SecurityContextHolder.getContext().getAuthentication() + "'");
			}
		}
		else {
			if (logger.isDebugEnabled()) {
				logger.debug("SecurityContextHolder not populated with anonymous token, as it already contained: '" + SecurityContextHolder.getContext().getAuthentication() + "'");
			}
		}
		chain.doFilter(req, res);
	}

	protected Authentication createAuthentication(HttpServletRequest request) {
		AnonymousAuthenticationToken auth = new AnonymousAuthenticationToken(key, principal, authorities);
		auth.setDetails(authenticationDetailsSource.buildDetails(request));
		return auth;
	}
}
```

### ExceptionTranslationFilter
```
public class ExceptionTranslationFilter extends GenericFilterBean {
private void handleSpringSecurityException(HttpServletRequest request, HttpServletResponse response, FilterChain chain, RuntimeException exception) throws IOException, ServletException {
		if (exception instanceof AuthenticationException) {
			logger.debug("Authentication exception occurred; redirecting to authentication entry point", exception);
			sendStartAuthentication(request, response, chain, (AuthenticationException) exception);
		}
		else if (exception instanceof AccessDeniedException) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authenticationTrustResolver.isAnonymous(authentication) || authenticationTrustResolver.isRememberMe(authentication)) {
				logger.debug("Access is denied (user is " + (authenticationTrustResolver.isAnonymous(authentication) ? "anonymous" : "not fully authenticated") + "); redirecting to authentication entry point", exception);
				sendStartAuthentication(request, response, chain, new InsufficientAuthenticationException("Full authentication is required to access this resource"));
			}
			else {
				logger.debug("Access is denied (user is not anonymous); delegating to AccessDeniedHandler", exception);
				accessDeniedHandler.handle(request, response, (AccessDeniedException) exception);
			}
		}
	}
}
```

![AuthenticationEntryPoint-min](https://s0.wailian.download/2019/06/26/AuthenticationEntryPoint-min.png)

```
public abstract class AbstractAuthenticationFilterConfigurer<B extends HttpSecurityBuilder<B>, T extends AbstractAuthenticationFilterConfigurer<B, T, F>, F extends AbstractAuthenticationProcessingFilter> extends AbstractHttpConfigurer<T, B> {
	private LoginUrlAuthenticationEntryPoint authenticationEntryPoint;
	private boolean customLoginPage;
	private String loginPage;
	private String loginProcessingUrl;
	private AuthenticationFailureHandler failureHandler;
}
```
```
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		if (!response.isCommitted()) {
			if (errorPage != null) {
				// Put exception into request scope (perhaps of use to a view)
				request.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);
				// Set the 403 status code.
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				// forward to error page.
				RequestDispatcher dispatcher = request.getRequestDispatcher(errorPage);
				dispatcher.forward(request, response);
			}
			else {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
			}
		}
	}
}
```

### FilterSecurityInterceptor
```
@Override
protected void configure(HttpSecurity http) throws Exception {
	http
		.authorizeRequests()
			.antMatchers("/resources/**", "/signup", "/about").permitAll()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
			.anyRequest().authenticated()
			.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
				public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
					fsi.setPublishAuthorizationSuccess(true);
					return fsi;
				}
			});
}
```

## IP_Login
1. `IpAuthenticationProcessingFilter` –> `UsernamePasswordAuthenticationFilter`
1. `IpAuthenticationToken` –> `UsernamePasswordAuthenticationToken`
1. `ProviderManager` –> `ProviderManager`
1. `IpAuthenticationProvider` –> `DaoAuthenticationProvider`
1. `ConcurrentHashMap` –> `UserDetailsService`

## Authorization
![securityAuthorizationDiagram-min-min](https://s0.wailian.download/2019/06/28/securityAuthorizationDiagram-min-min.png)

![AuthorizationSequenceDiagram-min-min](https://s0.wailian.download/2019/06/28/AuthorizationSequenceDiagram-min-min.png)

Breakpoints
```
package org.springframework.security.web.access.intercept;
public class DefaultFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	public Collection<ConfigAttribute> getAttributes(Object object) {
		return entry.getValue();
	}
}
```
```
package org.springframework.security.access.intercept;
public abstract class AbstractSecurityInterceptor implements InitializingBean, ApplicationEventPublisherAware, MessageSourceAware {
	protected InterceptorStatusToken beforeInvocation(Object object) {
		this.accessDecisionManager.decide(authenticated, object, attributes);
	}
}
```

## References
- [Spring Security(四)--核心过滤器源码分析](https://www.cnkirito.moe/spring-security-4/)
- [Spring Security(五)--动手实现一个IP_Login](https://www.cnkirito.moe/spring-security-5/)
- [Spring Security 源码分析二：Spring Security 授权过程](http://www.iocoder.cn/Spring-Security/longfei/The-authorization-process/)