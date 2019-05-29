package t5750.hive.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import t5750.hive.util.HiveUtil;

public class HiveAlterRenameTo {
	public static void main(String[] args) throws SQLException {
		Connection con = HiveUtil.getConnection();
		// create statement
		Statement stmt = con.createStatement();
		// execute statement
		stmt.execute("ALTER TABLE " + HiveUtil.TABLE_EMPLOYEE_API
				+ " RENAME TO " + HiveUtil.TABLE_EMP_API);
		System.out.println("Table Renamed Successfully");
		con.close();
	}
}