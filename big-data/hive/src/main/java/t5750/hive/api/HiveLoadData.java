package t5750.hive.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import t5750.hive.util.HiveUtil;

public class HiveLoadData {
	public static void main(String[] args) throws SQLException {
		Connection con = HiveUtil.getConnection();
		// create statement
		Statement stmt = con.createStatement();
		// execute statement
		stmt.execute("LOAD DATA LOCAL INPATH '/home/hadoop/hive/sample.txt'"
				+ "OVERWRITE INTO TABLE " + HiveUtil.TABLE_EMPLOYEE_API);
		System.out.println("Load Data into " + HiveUtil.TABLE_EMPLOYEE_API
				+ " successful");
		con.close();
	}
}