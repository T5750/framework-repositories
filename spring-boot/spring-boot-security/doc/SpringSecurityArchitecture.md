# Spring Security Architecture Overview

## Classes
### SecurityContextHolder
```
public class SecurityContextHolder {
	public static final String MODE_THREADLOCAL = "MODE_THREADLOCAL";

	private static void initialize() {
		if (!StringUtils.hasText(strategyName)) {
			// Set default
			strategyName = MODE_THREADLOCAL;
		}
		if (strategyName.equals(MODE_THREADLOCAL)) {
			strategy = new ThreadLocalSecurityContextHolderStrategy();
		}
		else if (strategyName.equals(MODE_INHERITABLETHREADLOCAL)) {
			strategy = new InheritableThreadLocalSecurityContextHolderStrategy();
		}
		else if (strategyName.equals(MODE_GLOBAL)) {
			strategy = new GlobalSecurityContextHolderStrategy();
		}
		else {
			// Try to load a custom strategy
			try {
				Class<?> clazz = Class.forName(strategyName);
				Constructor<?> customStrategy = clazz.getConstructor();
				strategy = (SecurityContextHolderStrategy) customStrategy.newInstance();
			}
			catch (Exception ex) {
				ReflectionUtils.handleReflectionException(ex);
			}
		}
		initializeCount++;
	}
}
```
```
final class ThreadLocalSecurityContextHolderStrategy implements SecurityContextHolderStrategy {
	private static final ThreadLocal<SecurityContext> contextHolder = new ThreadLocal<SecurityContext>();
}
```
```
Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
if (principal instanceof UserDetails) {
	String username = ((UserDetails)principal).getUsername();
} else {
	String username = principal.toString();
}
```

### Authentication
```
package org.springframework.security.core;
public interface Authentication extends Principal, Serializable {
    Collection<? extends GrantedAuthority> getAuthorities();
    Object getCredentials();
    Object getDetails();
    Object getPrincipal();
    boolean isAuthenticated();
    void setAuthenticated(boolean var1) throws IllegalArgumentException;
}
```

### AuthenticationManager
```
public class ProviderManager implements AuthenticationManager, MessageSourceAware, InitializingBean {
	private List<AuthenticationProvider> providers = Collections.emptyList();
	
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		Class<? extends Authentication> toTest = authentication.getClass();
		AuthenticationException lastException = null;
		Authentication result = null;
		boolean debug = logger.isDebugEnabled();
		for (AuthenticationProvider provider : getProviders()) {
			if (!provider.supports(toTest)) {
				continue;
			}
			if (debug) {
				logger.debug("Authentication attempt using "
						+ provider.getClass().getName());
			}
			try {
				result = provider.authenticate(authentication);
				if (result != null) {
					copyDetails(authentication, result);
					break;
				}
			}
			catch (AccountStatusException e) {
				prepareException(e, authentication);
				// SEC-546: Avoid polling additional providers if auth failure is due to
				// invalid account status
				throw e;
			}
			catch (InternalAuthenticationServiceException e) {
				prepareException(e, authentication);
				throw e;
			}
			catch (AuthenticationException e) {
				lastException = e;
			}
		}
		if (result == null && parent != null) {
			// Allow the parent to try.
			try {
				result = parent.authenticate(authentication);
			}
			catch (ProviderNotFoundException e) {
				// ignore as we will throw below if no other exception occurred prior to
				// calling parent and the parent
				// may throw ProviderNotFound even though a provider in the child already
				// handled the request
			}
			catch (AuthenticationException e) {
				lastException = e;
			}
		}
		if (result != null) {
			if (eraseCredentialsAfterAuthentication
					&& (result instanceof CredentialsContainer)) {
				// Authentication is complete. Remove credentials and other secret data
				// from authentication
				((CredentialsContainer) result).eraseCredentials();
			}
			eventPublisher.publishAuthenticationSuccess(result);
			return result;
		}
		// Parent was null, or didn't authenticate (or throw an exception).
		if (lastException == null) {
			lastException = new ProviderNotFoundException(messages.getMessage(
					"ProviderManager.providerNotFound",
					new Object[] { toTest.getName() },
					"No AuthenticationProvider found for {0}"));
		}
		prepareException(lastException, authentication);
		throw lastException;
	}
}
```

### DaoAuthenticationProvider
```
public class DaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	private PasswordEncoder passwordEncoder;
	private SaltSource saltSource;
	private UserDetailsService userDetailsService;

	protected final UserDetails retrieveUser(String username,
		UsernamePasswordAuthenticationToken authentication)
		throws AuthenticationException {}

	protected void additionalAuthenticationChecks(UserDetails userDetails,
		UsernamePasswordAuthenticationToken authentication)
		throws AuthenticationException {}

	public Authentication authenticate(Authentication authentication)
		throws AuthenticationException {}
}
```

### UserDetails
```
public interface UserDetails extends Serializable {
	Collection<? extends GrantedAuthority> getAuthorities();
	String getPassword();
	String getUsername();
	boolean isAccountNonExpired();
	boolean isAccountNonLocked();
	boolean isCredentialsNonExpired();
	boolean isEnabled();
}
```

### UserDetailsService
```
public interface UserDetailsService {
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
```

## Architecture Overview
![Spring_Security-min-min](https://www.wailian.work/images/2019/06/26/Spring_Security-min-min.png)

### Filter
- [Filter Ordering](SpringSecurityReference.md#133-filter-ordering)

### WEB http认证授权
- request请求经过认证处理Filter时，Filter从request中获取需要的账号，凭证，生成未认证的`Authentication`，委托给`AuthenticationManager`进行认证，由`AuthenticationManager`的实现类`ProviderManager`进行认证
- `ProviderManager`持有多个`AuthenticationProvider`实例，循环找出支持当前`Authentication`的provider实例，进行认证
- 在provider中，首先使用`UserdetailsService`的`loadUserByUsername`获取当前用户名对应的用户在系统中的信息，之后对用户状态进行检查。如果通过检查，则使用`PasswordEncoder`进行凭证对比，如果一致，则认证成功，否则认证失败。无论认证成功或失败，`ProviderManager`都会通过预先配置的`AuthenticationEventPublisher`发布相应事件
- 另外，在认证处理Filter中，可预先设置成功或失败的Handler，进行自定义处理

### 扩展点
1. 自定义`Authentication`，继承自`AbstractAuthenticationToken`
1. 自定义`AuthenticationProvider`，实现`AuthenticationProvider`接口。`supports`方法支持1中定义的`Authentication`。`authenticate`方法根据自身业务，加载系统中用户，与request中用户做对比，完成认证授权
1. 自定义认证Filter，继承自`AbstractAuthenticationProcessingFilter`，实现`attemptAuthentication`方法，从request中生成1中定义的`Authentication`，使用`AuthenticationManager`进行认证
1. 通过`WebSecurityConfigurerAdapter`将自定义的provider配置到`AuthenticationManagerBuilder`，将自定义的认证Filter配置到`HttpSecurity`

## References
- [Spring Security(一)--Architecture Overview](https://www.cnkirito.moe/spring-security-1/)
- [Spring Security流程简要分析](https://dr-yanglong.github.io/2018/09/24/spring-security/)