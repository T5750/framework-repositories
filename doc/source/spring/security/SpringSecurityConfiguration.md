# Spring Security Configuration

## @EnableWebSecurity
```
@Import({WebSecurityConfiguration.class, SpringWebMvcImportSelector.class})
@EnableGlobalAuthentication
@Configuration
public @interface EnableWebSecurity {
	boolean debug() default false;
}
```
```
@Configuration
public class WebSecurityConfiguration implements ImportAware, BeanClassLoaderAware {
	// DEFAULT_FILTER_NAME = "springSecurityFilterChain"
	@Bean(name = AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME)
	public Filter springSecurityFilterChain() throws Exception {}
}
```
```
@Import(AuthenticationConfiguration.class)
@Configuration
public @interface EnableGlobalAuthentication {
}
```
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
```
@Configuration
@Import(ObjectPostProcessorConfiguration.class)
public class AuthenticationConfiguration {
	@Bean
	public AuthenticationManagerBuilder authenticationManagerBuilder(
			ObjectPostProcessor<Object> objectPostProcessor) {
		return new AuthenticationManagerBuilder(objectPostProcessor);
	}
	
	public AuthenticationManager getAuthenticationManager() throws Exception {}
}
```

## WebSecurityConfigurerAdapter
```
public abstract class WebSecurityConfigurerAdapter implements WebSecurityConfigurer<WebSecurity> {
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		this.disableLocalConfigureAuthenticationBldr = true;
	}

	protected void configure(HttpSecurity http) throws Exception {
		logger.debug("Using default configure(HttpSecurity). If subclassed this will potentially override subclass configure(HttpSecurity).");
		http
			.authorizeRequests().anyRequest().authenticated().and()
			.formLogin().and()
			.httpBasic();
	}
	
	public void configure(WebSecurity web) throws Exception {
	}
}
```

### HttpSecurity
```
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/resources/**", "/signup", "/about").permitAll()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.usernameParameter("username")
			.passwordParameter("password")
			.failureForwardUrl("/login?error")
			.loginPage("/login")
			.permitAll()
			.and()
		.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/index")
			.permitAll()
			.and()
		.httpBasic()
			.disable();
	}
}
```

### WebSecurity
```
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}
}
```

### AuthenticationManagerBuilder
```
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("USER");
	}
}
```
```
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("USER");
	}
}
```

## References
- [Spring Security(三)--核心配置解读](https://www.cnkirito.moe/spring-security-3/)