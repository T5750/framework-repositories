# Keycloak Security

## Spring Boot and Keycloak

### Setting up a Keycloak server
```
./standalone.sh(bat)
```
http://localhost:8080/auth

1. Creating a new Realm: `Security`
    - Themes:
        * Internationalization Enabled
        * Default Locale: `zh-CN`
2. Creating the client:
    - Client ID: `spring-boot2-keycloak`
    - Valid Redirect URIs: `http://localhost:18095/*`
    - Base URL: `http://localhost:18095/`
3. Creating the role: `user`
4. Creating the user:
    - Username: `tester`
    - Locale: `zh-CN`
    - Credentials -> choose a password -> turn off the “Temporary”
    - Role Mappings -> assign the role “user”

### Defining Keycloak’s configuration
```
keycloak.realm=Security
keycloak.auth-server-url=http://127.0.0.1:8080/auth
keycloak.resource=spring-boot2
keycloak.public-client=true
keycloak.security-constraints[0].authRoles[0]=user
keycloak.security-constraints[0].securityCollections[0].patterns[0]=/keycloak/*
```

## Spring Security support
```
implementation("org.springframework.boot:spring-boot-starter-security")
```

### Creating a SecurityConfig class
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
    /**
     * Registers the KeycloakAuthenticationProvider with the authentication
     * manager.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider
                .setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    /**
     * Defines the session authentication strategy.
     */
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(
                new SessionRegistryImpl());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.authorizeRequests().antMatchers("/keycloak/**").hasRole("user")
                .anyRequest().permitAll();
    }
}
```

- `configureGlobal`: Here we change the Granted Authority Mapper, by default in Spring Security, roles are prefixed with **ROLE_**, we could change that in our Realm configuration but it could be confusing for other applications that do not know this convention, so here we assign a `SimpleAuthorityMapper` that will make sure no prefix is added.
- `keycloakConfigResolver`: By default, the Keycloak Spring Security Adapter will look up for a file named `keycloak.json` present on your classpath. But here we want to leverage the Spring Boot properties file support.

map the `Principal` name with our Keycloak username:
```
keycloak.principal-attribute=preferred_username
```

## References
- [使用 Keycloak 轻松保护 Spring Boot 应用程序](https://www.oschina.net/translate/easily-secure-your-spring-boot-applications-with-k)
- [Easily secure your Spring Boot applications with Keycloak](https://developers.redhat.com/blog/2017/05/25/easily-secure-your-spring-boot-applications-with-keycloak/)