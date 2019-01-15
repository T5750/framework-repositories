package t5750.tcctransaction.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;
import javax.transaction.xa.Xid;

import org.apache.log4j.Logger;

import t5750.tcctransaction.Transaction;
import t5750.tcctransaction.serializer.JdkSerializationSerializer;
import t5750.tcctransaction.serializer.ObjectSerializer;
import t5750.tcctransaction.utils.CollectionUtils;
import t5750.tcctransaction.utils.StringUtils;

/**
 * JDBC事务库（在应用服务中实例化并注入数据源）.
 */
public class JdbcTransactionRepository extends CachableTransactionRepository {
	static final Logger LOG = Logger.getLogger(JdbcTransactionRepository.class
			.getSimpleName());
	private String domain;
	private String tbSuffix;
	private DataSource dataSource;
	private ObjectSerializer serializer = new JdkSerializationSerializer();

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getTbSuffix() {
		return tbSuffix;
	}

	public void setTbSuffix(String tbSuffix) {
		this.tbSuffix = tbSuffix;
	}

	public void setSerializer(ObjectSerializer serializer) {
		this.serializer = serializer;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * 创建事务日志记录
	 */
	protected int doCreate(Transaction transaction) {
		LOG.debug("==>JdbcTransactionRepository.doCreate(Transaction transaction)");
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = this.getConnection();
			StringBuilder builder = new StringBuilder();
			builder.append("INSERT INTO "
					+ getTableName()
					+ "(GLOBAL_TX_ID,BRANCH_QUALIFIER,TRANSACTION_TYPE,CONTENT,STATUS,RETRIED_COUNT,CREATE_TIME,LAST_UPDATE_TIME,VERSION");
			builder.append(StringUtils.isNotEmpty(domain) ? ",DOMAIN ) VALUES (?,?,?,?,?,?,?,?,?,?)"
					: ") VALUES (?,?,?,?,?,?,?,?,?)");
			stmt = connection.prepareStatement(builder.toString());
			stmt.setBytes(1, transaction.getXid().getGlobalTransactionId()); // GLOBAL_TX_ID（全局事务ID）
			stmt.setBytes(2, transaction.getXid().getBranchQualifier()); // BRANCH_QUALIFIER（分支限定符）
			stmt.setInt(3, transaction.getTransactionType().getId()); // TRANSACTION_TYPE（事务类型，主事务:1，分支事务:2）
			stmt.setBytes(4, serializer.serialize(transaction)); // CONTENT（事务内容）
			stmt.setInt(5, transaction.getStatus().getId()); // STATUS（事务状态，TRYING(1)，CONFIRMING(2)，CANCELLING(3)）
			stmt.setInt(6, transaction.getRetriedCount()); // RETRIED_COUNT（事务恢复重试次数）
			stmt.setTimestamp(7, new Timestamp(transaction.getCreateTime()
					.getTime())); // CREATE_TIME(创建时间)
			stmt.setTimestamp(8, new Timestamp(transaction.getLastUpdateTime()
					.getTime())); // LAST_UPDATE_TIME(最后更新时间)
			stmt.setLong(9, transaction.getVersion()); // VERSION（版本号）
			if (StringUtils.isNotEmpty(domain)) {
				stmt.setString(10, domain);
			}
			return stmt.executeUpdate();
		} catch (SQLException e) {
			throw new TransactionIOException(e);
		} finally {
			closeStatement(stmt);
			this.releaseConnection(connection);
		}
	}

	protected int doUpdate(Transaction transaction) {
		LOG.debug("==>JdbcTransactionRepository.doUpdate(Transaction transaction)");
		Connection connection = null;
		PreparedStatement stmt = null;
		transaction.updateTime();
		transaction.updateVersion();
		try {
			connection = this.getConnection();
			StringBuilder builder = new StringBuilder();
			builder.append("UPDATE "
					+ getTableName()
					+ " SET "
					+ "CONTENT = ?,STATUS = ?,LAST_UPDATE_TIME = ?, RETRIED_COUNT = ?,VERSION = VERSION+1 WHERE GLOBAL_TX_ID = ? AND BRANCH_QUALIFIER = ? AND VERSION = ?");
			builder.append(StringUtils.isNotEmpty(domain) ? " AND DOMAIN = ?"
					: "");
			stmt = connection.prepareStatement(builder.toString());
			stmt.setBytes(1, serializer.serialize(transaction));
			stmt.setInt(2, transaction.getStatus().getId());
			stmt.setTimestamp(3, new Timestamp(transaction.getLastUpdateTime()
					.getTime()));
			stmt.setInt(4, transaction.getRetriedCount());
			stmt.setBytes(5, transaction.getXid().getGlobalTransactionId());
			stmt.setBytes(6, transaction.getXid().getBranchQualifier());
			stmt.setLong(7, transaction.getVersion() - 1);
			if (StringUtils.isNotEmpty(domain)) {
				stmt.setString(8, domain);
			}
			int result = stmt.executeUpdate();
			return result;
		} catch (Throwable e) {
			throw new TransactionIOException(e);
		} finally {
			closeStatement(stmt);
			this.releaseConnection(connection);
		}
	}

	protected int doDelete(Transaction transaction) {
		LOG.debug("==>JdbcTransactionRepository.doDelete(Transaction transaction)");
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = this.getConnection();
			StringBuilder builder = new StringBuilder();
			builder.append("DELETE FROM " + getTableName()
					+ " WHERE GLOBAL_TX_ID = ? AND BRANCH_QUALIFIER = ?");
			builder.append(StringUtils.isNotEmpty(domain) ? " AND DOMAIN = ?"
					: "");
			stmt = connection.prepareStatement(builder.toString());
			stmt.setBytes(1, transaction.getXid().getGlobalTransactionId());
			stmt.setBytes(2, transaction.getXid().getBranchQualifier());
			if (StringUtils.isNotEmpty(domain)) {
				stmt.setString(3, domain);
			}
			return stmt.executeUpdate();
		} catch (SQLException e) {
			throw new TransactionIOException(e);
		} finally {
			closeStatement(stmt);
			this.releaseConnection(connection);
		}
	}

	protected Transaction doFindOne(Xid xid) {
		LOG.debug("==>doFindOne xid:" + xid.getGlobalTransactionId());
		List<Transaction> transactions = doFind(Arrays.asList(xid));
		if (!CollectionUtils.isEmpty(transactions)) {
			return transactions.get(0);
		}
		return null;
	}

	@Override
	protected List<Transaction> doFindAllUnmodifiedSince(java.util.Date date) {
		List<Transaction> transactions = new ArrayList<Transaction>();
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = this.getConnection();
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT GLOBAL_TX_ID, BRANCH_QUALIFIER, CONTENT,STATUS,TRANSACTION_TYPE,CREATE_TIME,LAST_UPDATE_TIME,RETRIED_COUNT,VERSION");
			builder.append(StringUtils.isNotEmpty(domain) ? ",DOMAIN" : "");
			builder.append("  FROM " + getTableName()
					+ " WHERE LAST_UPDATE_TIME < ? AND TRANSACTION_TYPE = 1"); // TRANSACTION_TYPE=1（主事务:1）
			builder.append(StringUtils.isNotEmpty(domain) ? " AND DOMAIN = ?"
					: "");
			stmt = connection.prepareStatement(builder.toString());
			stmt.setTimestamp(1, new Timestamp(date.getTime()));
			if (StringUtils.isNotEmpty(domain)) {
				stmt.setString(2, domain);
			}
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				byte[] transactionBytes = resultSet.getBytes(3);
				Transaction transaction = (Transaction) serializer
						.deserialize(transactionBytes);
				transaction.setLastUpdateTime(resultSet.getDate(7));
				transaction.setVersion(resultSet.getLong(9));
				transaction.resetRetriedCount(resultSet.getInt(8));
				transactions.add(transaction);
			}
		} catch (Throwable e) {
			throw new TransactionIOException(e);
		} finally {
			closeStatement(stmt);
			this.releaseConnection(connection);
		}
		return transactions;
	}

	protected List<Transaction> doFind(List<Xid> xids) {
		LOG.debug("==>doFind");
		List<Transaction> transactions = new ArrayList<Transaction>();
		if (CollectionUtils.isEmpty(xids)) {
			return transactions;
		}
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = this.getConnection();
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT GLOBAL_TX_ID, BRANCH_QUALIFIER, CONTENT,STATUS,TRANSACTION_TYPE,CREATE_TIME,LAST_UPDATE_TIME,RETRIED_COUNT,VERSION");
			builder.append(StringUtils.isNotEmpty(domain) ? ",DOMAIN" : "");
			builder.append("  FROM " + getTableName() + " WHERE");
			if (!CollectionUtils.isEmpty(xids)) {
				for (Xid xid : xids) {
					builder.append(" ( GLOBAL_TX_ID = ? AND BRANCH_QUALIFIER = ? ) OR");
				}
				builder.delete(builder.length() - 2, builder.length());
			}
			builder.append(StringUtils.isNotEmpty(domain) ? " AND DOMAIN = ?"
					: "");
			stmt = connection.prepareStatement(builder.toString());
			int i = 0;
			for (Xid xid : xids) {
				stmt.setBytes(++i, xid.getGlobalTransactionId());
				stmt.setBytes(++i, xid.getBranchQualifier());
			}
			if (StringUtils.isNotEmpty(domain)) {
				stmt.setString(++i, domain);
			}
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				byte[] transactionBytes = resultSet.getBytes(3);
				Transaction transaction = (Transaction) serializer
						.deserialize(transactionBytes);
				transaction.setLastUpdateTime(resultSet.getDate(7));
				transaction.setVersion(resultSet.getLong(9));
				transaction.resetRetriedCount(resultSet.getInt(8));
				transactions.add(transaction);
			}
		} catch (Throwable e) {
			throw new TransactionIOException(e);
		} finally {
			closeStatement(stmt);
			this.releaseConnection(connection);
		}
		return transactions;
	}

	protected Connection getConnection() {
		try {
			return this.dataSource.getConnection();
		} catch (SQLException e) {
			throw new TransactionIOException(e);
		}
	}

	protected void releaseConnection(Connection con) {
		try {
			if (con != null && !con.isClosed()) {
				con.close();
			}
		} catch (SQLException e) {
			throw new TransactionIOException(e);
		}
	}

	private void closeStatement(Statement stmt) {
		try {
			if (stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (Exception ex) {
			throw new TransactionIOException(ex);
		}
	}

	private String getTableName() {
		return StringUtils.isNotEmpty(tbSuffix) ? "TCC_TRANSACTION" + tbSuffix
				: "TCC_TRANSACTION";
	}
}