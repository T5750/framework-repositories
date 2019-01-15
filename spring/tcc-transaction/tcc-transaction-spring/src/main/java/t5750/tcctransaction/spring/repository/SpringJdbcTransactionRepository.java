package t5750.tcctransaction.spring.repository;

import java.sql.Connection;

import org.springframework.jdbc.datasource.DataSourceUtils;

import t5750.tcctransaction.repository.JdbcTransactionRepository;

/**
 * SpringJdbc事务库
 */
public class SpringJdbcTransactionRepository extends JdbcTransactionRepository {
	protected Connection getConnection() {
		return DataSourceUtils.getConnection(this.getDataSource());
	}

	protected void releaseConnection(Connection con) {
		DataSourceUtils.releaseConnection(con, this.getDataSource());
	}
}
