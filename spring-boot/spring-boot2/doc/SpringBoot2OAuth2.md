# Spring Boot 2 OAuth2

## Overview
### Roles
OAuth defines four roles –
- **Resource Owner** – The user of the application.
- **Client** – the application (user is using) which require access to user data on the resource server.
- **Resource Server** – store user’s data and http services which can return user data to authenticated clients.
- **Authorization Server** – responsible for authenticating user’s identity and gives an authorization token. This token is accepted by resource server and validate your identity.

![Oauth2 Flow](https://www.wailian.work/images/2019/11/24/Oauth2-Flow-min.png)

### Access Token vs Refresh Token
An **access token** is a string representing an authorization issued to the client. Tokens represent specific scopes and duration of access, granted by the resource owner, and enforced by the resource server and authorization server.

**Refresh token** is issued (along with access token) to the client by the authorization server and is used to obtain a new access token when the current access token becomes invalid or expires, or to obtain additional access tokens with identical or narrower scope (access tokens may have a shorter lifetime and fewer permissions than authorized by the resource owner). Issuing a refresh token is optional at the discretion of the authorization server.
- The responsibility of access token is to access data before it gets expired.
- The responsibility of refresh token is to request for a new access token when the existing access token is expired.

## Oauth2 – Authorization Server
- `OAuth2AuthorizationServer`
```
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()")
				.checkTokenAccess("isAuthenticated()")
				.allowFormAuthenticationForClients();
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("clientapp")
				.secret(passwordEncoder.encode("123456"))
				.authorizedGrantTypes("password", "authorization_code", "refresh_token")
				.authorities("READ_ONLY_CLIENT").scopes("read_profile_info")
				.resourceIds("oauth2-resource")
				.redirectUris("http://localhost:18094/login")
				.accessTokenValiditySeconds(5000)
				.refreshTokenValiditySeconds(50000);
	}
}
```
- Spring security oauth exposes two endpoints for checking tokens (`/oauth/check_token` and `/oauth/token_key`) which are by default protected behind `denyAll()`. tokenKeyAccess() and checkTokenAccess() methods open these endpoints for use.
- `ClientDetailsServiceConfigurer` is used to define an in-memory or JDBC implementation of the client details service. we have used in-memory implementation. It has following important attribute:
    - clientId – (required) the client id.
    - secret – (required for trusted clients) the client secret, if any.
    - scope – The scope to which the client is limited. If scope is undefined or empty (the default) the client is not limited by scope.
    - authorizedGrantTypes – Grant types that are authorized for the client to use. Default value is empty.
    - authorities – Authorities that are granted to the client (regular Spring Security authorities).
    - redirectUris – redirects the user-agent to the client’s redirection endpoint. It must be an absolute URL.

## Oauth2 – Resource Server
- `OAuth2ResourceServer`
- `SecurityConfig`

## Oauth2 protected REST resources
- `RestResource`
- `UserProfile`

## Demo
### Get authorization grant code from user
- http://localhost:18093/oauth/authorize?client_id=clientapp&response_type=code&scope=read_profile_info
- T5750, 123456
- http://localhost:18094/login?code=rfQzEz

### Get access token from authorization server
- http://localhost:18093/oauth/token?grant_type=authorization_code&code=rfQzEz
- clientapp, 123456
- `{"access_token":"389b48fc-914b-4ded-a0cb-a80e32b5c512","token_type":"bearer","refresh_token":"64afecbb-6993-4bda-a8d7-9a08f35fcb3f","expires_in":4999,"scope":"read_profile_info"}`

```
#Access token request from postman
http://localhost:18093/oauth/token

Headers:

Content-Type: application/x-www-form-urlencoded
authorization: Basic Y2xpZW50YXBwOjEyMzQ1Ng==

Form data - application/x-www-form-urlencoded:
grant_type=authorization_code
code=rfQzEz
redirect_uri=http://localhost:18094/login
```
Or make similar request from cURL.
```
curl -X POST --user clientapp:123456 http://localhost:18093/oauth/token -H "content-type: application/x-www-form-urlencoded" -d "code=rfQzEz&grant_type=authorization_code&redirect_uri=http%3A%2F%2Flocalhost%3A18094%2Flogin&scope=read_user_info"
```

### Access user data from resource server
- http://localhost:18093/api/users/me?authorization=Bearer%20389b48fc-914b-4ded-a0cb-a80e32b5c512
```
curl -X GET http://localhost:18093/api/users/me -H "authorization: Bearer 389b48fc-914b-4ded-a0cb-a80e32b5c512"
```

## References
- [Spring Boot 2 – OAuth2 Auth and Resource Server](https://howtodoinjava.com/spring-boot2/oauth2-auth-server/)