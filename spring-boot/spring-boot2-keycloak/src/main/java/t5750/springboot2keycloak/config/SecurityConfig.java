package t5750.springboot2keycloak.config;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticatedActionsFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakSecurityContextRequestFilter;
import org.keycloak.adapters.springsecurity.management.HttpSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

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

	@Bean
	public KeycloakConfigResolver KeycloakConfigResolver() {
		return new KeycloakSpringBootConfigResolver();
	}

	@Bean
	public FilterRegistrationBean keycloakAuthenticationProcessingFilterRegistrationBean(
			KeycloakAuthenticationProcessingFilter filter) {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean(
				filter);
		registrationBean.setEnabled(false);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean keycloakPreAuthActionsFilterRegistrationBean(
			KeycloakPreAuthActionsFilter filter) {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean(
				filter);
		registrationBean.setEnabled(false);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean keycloakAuthenticatedActionsFilterBean(
			KeycloakAuthenticatedActionsFilter filter) {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean(
				filter);
		registrationBean.setEnabled(false);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean keycloakSecurityContextRequestFilterBean(
			KeycloakSecurityContextRequestFilter filter) {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean(
				filter);
		registrationBean.setEnabled(false);
		return registrationBean;
	}

	@Bean
	@Override
	@ConditionalOnMissingBean(HttpSessionManager.class)
	protected HttpSessionManager httpSessionManager() {
		return new HttpSessionManager();
	}
}