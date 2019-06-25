# Spring Security Reference

## 6. Security Namespace Configuration
### 6.3. Advanced Web Features
**Standard Filter Aliases and Ordering**

Alias | Filter Class | Namespace Element or Attribute
---|---|---
CHANNEL_FILTER | ChannelProcessingFilter | http/intercept-url@requires-channel
SECURITY_CONTEXT_FILTER | SecurityContextPersistenceFilter | http
CONCURRENT_SESSION_FILTER | ConcurrentSessionFilter | session-management/concurrency-control
HEADERS_FILTER | HeaderWriterFilter | http/headers
CSRF_FILTER | CsrfFilter | http/csrf
LOGOUT_FILTER | LogoutFilter | http/logout
X509_FILTER | X509AuthenticationFilter | http/x509
PRE_AUTH_FILTER | AbstractPreAuthenticatedProcessingFilter Subclasses | N/A
CAS_FILTER | CasAuthenticationFilter | N/A
FORM_LOGIN_FILTER | UsernamePasswordAuthenticationFilter | http/form-login
BASIC_AUTH_FILTER | BasicAuthenticationFilter | http/http-basic
SERVLET_API_SUPPORT_FILTER | SecurityContextHolderAwareRequestFilter | http/@servlet-api-provision
JAAS_API_SUPPORT_FILTER | JaasApiIntegrationFilter | http/@jaas-api-provision
REMEMBER_ME_FILTER | RememberMeAuthenticationFilter | http/remember-me
ANONYMOUS_FILTER | AnonymousAuthenticationFilter | http/anonymous
SESSION_MANAGEMENT_FILTER | SessionManagementFilter | session-management
EXCEPTION_TRANSLATION_FILTER | ExceptionTranslationFilter | http
FILTER_SECURITY_INTERCEPTOR | FilterSecurityInterceptor | http
SWITCH_USER_FILTER | SwitchUserFilter | N/A

## 9. Technical Overview
### 9.3 Authentication
#### 9.3.1 What is authentication in Spring Security?
1. The username and password are obtained and combined into an instance of `UsernamePasswordAuthenticationToken` (an instance of the `Authentication` interface, which we saw earlier).
1. The token is passed to an instance of `AuthenticationManager` for validation.
1. The `AuthenticationManager` returns a fully populated `Authentication` instance on successful authentication.
1. The security context is established by calling `SecurityContextHolder.getContext().setAuthentication(…​)`, passing in the returned authentication object.

- `AuthenticationExample`

## 13. The Security Filter Chain
### 13.3 Filter Ordering
- `ChannelProcessingFilter`, because it might need to redirect to a different protocol
- `SecurityContextPersistenceFilter`, so a `SecurityContext` can be set up in the `SecurityContextHolder` at the beginning of a web request, and any changes to the `SecurityContext` can be copied to the `HttpSession` when the web request ends (ready for use with the next web request)
- `ConcurrentSessionFilter`, because it uses the `SecurityContextHolder` functionality and needs to update the `SessionRegistry` to reflect ongoing requests from the principal
- Authentication processing mechanisms - `UsernamePasswordAuthenticationFilter`, `CasAuthenticationFilter`, `BasicAuthenticationFilter` etc - so that the `SecurityContextHolder` can be modified to contain a valid `Authentication` request token
- The `SecurityContextHolderAwareRequestFilter`, if you are using it to install a Spring Security aware `HttpServletRequestWrapper` into your servlet container
- The `JaasApiIntegrationFilter`, if a `JaasAuthenticationToken` is in the `SecurityContextHolder` this will process the `FilterChain` as the `Subject` in the `JaasAuthenticationToken`
- `RememberMeAuthenticationFilter`, so that if no earlier authentication processing mechanism updated the `SecurityContextHolder`, and the request presents a cookie that enables remember-me services to take place, a suitable remembered `Authentication` object will be put there
- `AnonymousAuthenticationFilter`, so that if no earlier authentication processing mechanism updated the `SecurityContextHolder`, an anonymous `Authentication` object will be put there
- `ExceptionTranslationFilter`, to catch any Spring Security exceptions so that either an HTTP error response can be returned or an appropriate `AuthenticationEntryPoint` can be launched
- `FilterSecurityInterceptor`, to protect web URIs and raise exceptions when access is denied

## 26. Expression-Based Access Control
### 26.1 Overview
#### 26.1.1 Common Built-In Expressions
Expression | Description
---|---
hasRole([role]) | Returns true if the current principal has the specified role. By default if the supplied role does not start with 'ROLE_' it will be added. This can be customized by modifying the defaultRolePrefix on DefaultWebSecurityExpressionHandler.
hasAnyRole([role1,role2]) | Returns true if the current principal has any of the supplied roles (given as a comma-separated list of strings). By default if the supplied role does not start with 'ROLE_' it will be added. This can be customized by modifying the defaultRolePrefix on DefaultWebSecurityExpressionHandler.
hasAuthority([authority]) | Returns true if the current principal has the specified authority.
hasAnyAuthority([authority1,authority2]) | Returns true if the current principal has any of the supplied roles (given as a comma-separated list of strings)
principal | Allows direct access to the principal object representing the current user
authentication | Allows direct access to the current Authentication object obtained from the SecurityContext
permitAll | Always evaluates to true
denyAll | Always evaluates to false
isAnonymous() | Returns true if the current principal is an anonymous user
isRememberMe() | Returns true if the current principal is a remember-me user
isAuthenticated() | Returns true if the user is not anonymous
isFullyAuthenticated() | Returns true if the user is not an anonymous or a remember-me user
hasPermission(Object target, Object permission) | Returns true if the user has access to the provided target for the given permission. For example, hasPermission(domainObject, 'read')
hasPermission(Object targetId, String targetType, Object permission) | Returns true if the user has access to the provided target for the given permission. For example, hasPermission(1, 'com.example.domain.Message', 'read')

## References
- [Spring Security Reference](https://docs.spring.io/spring-security/site/docs/4.2.2.RELEASE/reference/htmlsingle/)