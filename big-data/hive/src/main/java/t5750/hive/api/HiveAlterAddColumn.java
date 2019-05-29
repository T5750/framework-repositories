package t5750.hive.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import t5750.hive.util.HiveUtil;

public class HiveAlterAddColumn {
	public static void main(String[] args) throws SQLException {
		Connection con = HiveUtil.getConnection();
		// create statement
		Statement stmt = con.createStatement();
		// execute statement
		stmt.execute("ALTER TABLE " + HiveUtil.TABLE_EMP_API + " ADD COLUMNS "
				+ " (dept STRING COMMENT 'Department name')");
		System.out.println("Add column successful.");
		con.close();
	}
}