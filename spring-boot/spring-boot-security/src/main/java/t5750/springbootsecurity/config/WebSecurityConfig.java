package t5750.springbootsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import t5750.springbootsecurity.module.ip.IpAuthenticationProcessingFilter;
import t5750.springbootsecurity.module.ip.IpAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private static String REALM = "MY_TEST_REALM";

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/user/**").hasRole("ADMIN")
				.antMatchers("/", "/ipLogin").permitAll().anyRequest()
				.authenticated().and().formLogin().loginPage("/login")
				.permitAll().and().logout().permitAll();
		http.httpBasic().realmName(REALM)
				.authenticationEntryPoint(getBasicAuthEntryPoint());
		http.csrf().disable();
		// 注册IpAuthenticationProcessingFilter 注意放置的顺序 这很关键
		http.addFilterBefore(
				ipAuthenticationProcessingFilter(authenticationManager()),
				UsernamePasswordAuthenticationFilter.class);
	}

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("pass")
				.roles("ADMIN");
		auth.inMemoryAuthentication().withUser("user").password("pass")
				.roles("USER");
		auth.authenticationProvider(ipAuthenticationProvider());
	}

	@Bean
	public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint() {
		return new CustomBasicAuthenticationEntryPoint();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}

	/**
	 * ip认证者配置
	 */
	@Bean
	IpAuthenticationProvider ipAuthenticationProvider() {
		return new IpAuthenticationProvider();
	}

	/**
	 * 配置封装ipAuthenticationToken的过滤器
	 */
	IpAuthenticationProcessingFilter ipAuthenticationProcessingFilter(
			AuthenticationManager authenticationManager) {
		IpAuthenticationProcessingFilter ipAuthenticationProcessingFilter = new IpAuthenticationProcessingFilter();
		// 为过滤器添加认证器
		ipAuthenticationProcessingFilter
				.setAuthenticationManager(authenticationManager);
		// 重写认证失败时的跳转页面
		ipAuthenticationProcessingFilter
				.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(
						"/ipLogin?error"));
		return ipAuthenticationProcessingFilter;
	}

	/**
	 * 配置登录端点
	 */
	@Bean
	LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint() {
		LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint = new LoginUrlAuthenticationEntryPoint(
				"/ipLogin");
		return loginUrlAuthenticationEntryPoint;
	}
}
