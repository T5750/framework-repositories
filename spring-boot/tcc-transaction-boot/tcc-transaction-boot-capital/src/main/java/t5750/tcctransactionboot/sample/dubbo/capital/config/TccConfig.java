package t5750.tcctransactionboot.sample.dubbo.capital.config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import t5750.tcctransactionboot.dubbo.config.AbstractTccConfig;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * appcontext-service-tcc.xml
 */
@Configuration
public class TccConfig extends AbstractTccConfig {
	@Value("${c3p0.jdbcUrl}")
	private String jdbcUrl;

	@Override
	@Bean(name = "capitalDataSource", destroyMethod = "close")
	@Qualifier(value = "capitalDataSource")
	@Primary
	@Lazy(value = false)
	// @ConfigurationProperties(prefix = "c3p0")
	public DataSource dataSource() throws PropertyVetoException {
		ComboPooledDataSource dataSource = createDataSource(jdbcUrl);
		return dataSource;
	}
}