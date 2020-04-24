package t5750.springboot2rest.config;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

@EnableRedisHttpSession
public class HttpSessionConfig implements BeanClassLoaderAware {
	private ClassLoader loader;

	@Bean
	public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
		return new GenericJackson2JsonRedisSerializer(objectMapper());
	}

	@Bean
	public JedisConnectionFactory connectionFactory() {
		return new JedisConnectionFactory();
	}

	/**
	 * Customized {@link ObjectMapper} to add mix-in for class that doesn't have
	 * default constructors
	 *
	 * @return the {@link ObjectMapper} to use
	 */
	private ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModules(SecurityJackson2Modules.getModules(this.loader));
		return mapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.BeanClassLoaderAware#setBeanClassLoader
	 * (java.lang .ClassLoader)
	 */
	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.loader = classLoader;
	}

	@Bean
	public HttpSessionIdResolver httpSessionIdResolver() {
		return HeaderHttpSessionIdResolver.xAuthToken();
	}
}