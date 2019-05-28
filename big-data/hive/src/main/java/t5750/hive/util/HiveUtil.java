package t5750.hive.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 */
public class HiveUtil {
	private static final String DRIVER_NAME = "org.apache.hive.jdbc.HiveDriver";
	public static final String DATABASE_NAME = "userdb_api";

	public static Connection getConnection() throws SQLException {
		Connection con = null;
		try {
			// Register driver and create driver instance
			Class.forName(DRIVER_NAME);
			// get connection
			con = DriverManager.getConnection(
					"jdbc:hive2://tc210:10000/default", "hadoop", "");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;
	}
}
