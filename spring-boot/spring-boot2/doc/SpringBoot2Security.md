# Spring Boot 2 Security

## Configure WebSecurityConfigurerAdapter
```
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeRequests().anyRequest().authenticated()
				.and()
				.httpBasic();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("{noop}password").roles("USER");
	}
}
```

## References
- [Spring boot security rest basic authentication example](https://howtodoinjava.com/spring-boot2/security-rest-basic-auth-example/)