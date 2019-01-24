package t5750.tcctransactionboot.sample.dubbo.order.config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.mengyun.tcctransaction.spring.recover.DefaultRecoverConfig;
import org.mengyun.tcctransaction.spring.repository.SpringJdbcTransactionRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * appcontext-service-tcc.xml
 */
@Configuration
@ImportResource(locations = { "classpath:tcc-transaction.xml" })
public class TccConfiguration {
	@Value("${c3p0.jdbcUrl}")
	private String jdbcUrl;
	@Value("${tcc.jdbc.url}")
	private String tccJdbcUrl;
	@Value("${c3p0.user}")
	private String jdbcUser;
	@Value("${c3p0.password}")
	private String jdbcPassword;
	@Value("${c3p0.driverClass}")
	private String jdbcDriver;
	@Value("${c3p0.initialPoolSize}")
	private int initialPoolSize;
	@Value("${c3p0.minPoolSize}")
	private int minPoolSize;
	@Value("${c3p0.maxPoolSize}")
	private int maxPoolSize;
	@Value("${c3p0.acquireIncrement}")
	private int acquireIncrement;
	@Value("${c3p0.maxIdleTime}")
	private int maxIdleTime;
	@Value("${c3p0.checkoutTimeout}")
	private int checkoutTimeout;
	@Value("${tcc.domain}")
	private String tccDomain;
	@Value("${tcc.tbSuffix}")
	private String tccTbSuffix;

	private ComboPooledDataSource createDataSource(String jdbcUrl)
			throws PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setDriverClass(jdbcDriver);
		dataSource.setUser(jdbcUser);
		dataSource.setPassword(jdbcPassword);
		dataSource.setInitialPoolSize(initialPoolSize);
		dataSource.setMinPoolSize(minPoolSize);
		dataSource.setMaxPoolSize(maxPoolSize);
		dataSource.setAcquireIncrement(acquireIncrement);
		dataSource.setMaxIdleTime(maxIdleTime);
		dataSource.setCheckoutTimeout(checkoutTimeout);
		return dataSource;
	}

	// , destroyMethod = "close"
	@Bean(name = "orderDataSource")
	@Qualifier(value = "orderDataSource")
	@Primary
	// @ConfigurationProperties(prefix = "c3p0")
	public DataSource orderDataSource() throws PropertyVetoException {
		ComboPooledDataSource dataSource = createDataSource(jdbcUrl);
		return dataSource;
	}

	@Bean(name = "tccDataSource")
	@Qualifier(value = "tccDataSource")
	public DataSource tccDataSource() throws PropertyVetoException {
		ComboPooledDataSource dataSource = createDataSource(tccJdbcUrl);
		return dataSource;
	}

	@Bean(name = "transactionManager")
	public DataSourceTransactionManager transactionManager()
			throws PropertyVetoException {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(
				orderDataSource());
		return transactionManager;
	}

	@Bean(name = "transactionRepository")
	public SpringJdbcTransactionRepository springJdbcTransactionRepository()
			throws PropertyVetoException {
		SpringJdbcTransactionRepository repository = new SpringJdbcTransactionRepository();
		repository.setDataSource(tccDataSource());
		repository.setDomain(tccDomain);
		repository.setTbSuffix(tccTbSuffix);
		return repository;
	}

	@Bean
	public DefaultRecoverConfig recoverConfig() {
		DefaultRecoverConfig recoverConfig = new DefaultRecoverConfig();
		recoverConfig.setMaxRetryCount(30);
		recoverConfig.setRecoverDuration(60);
		recoverConfig.setCronExpression("0/30 * * * * ?");
		// Set<Class<? extends Exception>> delayCancelExceptions=new
		// HashSet<Class<? extends Exception>>();
		// delayCancelExceptions.add(new TimeoutException());
		// recoverConfig.setDelayCancelExceptions(delayCancelExceptions);
		return recoverConfig;
	}
}