package t5750.mysql.util;

/**
 * 连接池工具类，返回唯一的一个数据库连接池对象,单例模式
 */
public class ConnectionPoolUtil {
	// 私有静态方法
	private ConnectionPoolUtil() {
	}

	private static ConnectionPool poolInstance = null;

	public static ConnectionPool GetPoolInstance() {
		if (poolInstance == null) {
			poolInstance = new ConnectionPool(Globals.JDBC_DRIVER,
					Globals.DB_URL, Globals.DB_USERNAME, Globals.DB_PASSWORD);
			try {
				poolInstance.createPool();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return poolInstance;
	}
}