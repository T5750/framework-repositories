package t5750.securityoauth.codeqq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class OAuth2ServerConfig {
	private static final String QQ_RESOURCE_ID = "qq";

	@Configuration
	@EnableResourceServer()
	protected static class ResourceServerConfiguration extends
			ResourceServerConfigurerAdapter {
		@Override
		public void configure(ResourceServerSecurityConfigurer resources) {
			resources.resourceId(QQ_RESOURCE_ID).stateless(true);
			// 如果关闭 stateless，则 accessToken 使用时的session
			// id会被记录，后续请求不携带accessToken也可以正常响应
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			http
			// .sessionManagement()
			// .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
			// .and()
			.requestMatchers()
					// 保险起见，防止被主过滤器链路拦截
					.antMatchers("/qq/**").and().authorizeRequests()
					.anyRequest().authenticated().and().authorizeRequests()
					.antMatchers("/qq/info/**")
					.access("#oauth2.hasScope('get_user_info')")
					.antMatchers("/qq/fans/**")
					.access("#oauth2.hasScope('get_fanslist')");
		}
	}

	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends
			AuthorizationServerConfigurerAdapter {
		@Autowired
		@Qualifier("authenticationManagerBean")
		private AuthenticationManager authenticationManager;

		@Override
		public void configure(ClientDetailsServiceConfigurer clients)
				throws Exception {
			clients.inMemory()
					.withClient("aiqiyi")
					.resourceIds(QQ_RESOURCE_ID)
					.authorizedGrantTypes("authorization_code",
							"refresh_token", "implicit")
					.authorities("ROLE_CLIENT")
					.scopes("get_user_info", "get_fanslist")
					.secret("secret")
					.redirectUris("http://localhost:8062/aiqiyi/qq/redirect")
					.autoApprove(true)
					.autoApprove("get_user_info")
					.and()
					.withClient("youku")
					.resourceIds(QQ_RESOURCE_ID)
					.authorizedGrantTypes("authorization_code",
							"refresh_token", "implicit")
					.authorities("ROLE_CLIENT")
					.scopes("get_user_info", "get_fanslist").secret("secret")
					.redirectUris("http://localhost:8082/youku/qq/redirect");
		}

		@Bean
		public ApprovalStore approvalStore() {
			TokenApprovalStore store = new TokenApprovalStore();
			store.setTokenStore(tokenStore());
			return store;
		}

		@Autowired
		RedisConnectionFactory redisConnectionFactory;

		@Bean
		public TokenStore tokenStore() {
			return new InMemoryTokenStore();
			// 需要使用 redis 的话，放开这里
			// return new RedisTokenStore(redisConnectionFactory);
		}

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
			endpoints
					.tokenStore(tokenStore())
					.authenticationManager(authenticationManager)
					.allowedTokenEndpointRequestMethods(HttpMethod.GET,
							HttpMethod.POST);
		}

		@Override
		public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
			oauthServer.realm(QQ_RESOURCE_ID)
					.allowFormAuthenticationForClients();
		}
	}
}
