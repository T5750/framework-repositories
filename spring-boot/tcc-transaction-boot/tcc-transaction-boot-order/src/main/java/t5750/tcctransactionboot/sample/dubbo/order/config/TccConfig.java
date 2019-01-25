package t5750.tcctransactionboot.sample.dubbo.order.config;

import java.beans.PropertyVetoException;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.mengyun.tcctransaction.spring.recover.DefaultRecoverConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import t5750.tcctransactionboot.dubbo.config.AbstractTccConfig;

import com.alibaba.dubbo.remoting.TimeoutException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * appcontext-service-tcc.xml
 */
@Configuration
public class TccConfig extends AbstractTccConfig {
	@Value("${c3p0.jdbcUrl}")
	private String jdbcUrl;

	@Override
	// , destroyMethod = "close"
	@Bean(name = "orderDataSource")
	@Qualifier(value = "orderDataSource")
	@Primary
	// @ConfigurationProperties(prefix = "c3p0")
	public DataSource dataSource() throws PropertyVetoException {
		ComboPooledDataSource dataSource = createDataSource(jdbcUrl);
		return dataSource;
	}

	@Override
	@Bean
	public DefaultRecoverConfig recoverConfig() {
		DefaultRecoverConfig recoverConfig = new DefaultRecoverConfig();
		recoverConfig.setMaxRetryCount(30);
		recoverConfig.setRecoverDuration(60);
		recoverConfig.setCronExpression("0/30 * * * * ?");
		Set<Class<? extends Exception>> delayCancelExceptions = new HashSet<Class<? extends Exception>>();
		delayCancelExceptions.add(TimeoutException.class);
		recoverConfig.setDelayCancelExceptions(delayCancelExceptions);
		return recoverConfig;
	}
}