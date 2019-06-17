package t5750.springbootsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").permitAll().anyRequest()
				.authenticated().and().formLogin().loginPage("/login")
				.permitAll().and().logout().permitAll();
		http.httpBasic();
	}
	// @Autowired
	// public void configureGlobal(AuthenticationManagerBuilder auth)
	// throws Exception {
	// auth.inMemoryAuthentication().withUser("user").password("pass")
	// .roles("USER");
	// }
}
