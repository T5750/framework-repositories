package t5750.hive.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import t5750.hive.util.HiveUtil;

public class HiveCreateTable {
	public static void main(String[] args) throws SQLException {
		Connection con = HiveUtil.getConnection();
		// create statement
		Statement stmt = con.createStatement();
		// execute statement
		stmt.execute("CREATE TABLE IF NOT EXISTS " + HiveUtil.TABLE_EMPLOYEE_API
				+ " ( eid int, name String, "
				+ " salary String, destignation String)"
				+ " COMMENT 'Employee details'" + " ROW FORMAT DELIMITED"
				+ " FIELDS TERMINATED BY '\t'" + " LINES TERMINATED BY '\n'"
				+ " STORED AS TEXTFILE");
		System.out.println(" Table " + HiveUtil.TABLE_EMPLOYEE_API + " created.");
		con.close();
	}
}
