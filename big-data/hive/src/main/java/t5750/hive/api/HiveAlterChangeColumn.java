package t5750.hive.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import t5750.hive.util.HiveUtil;

public class HiveAlterChangeColumn {
	public static void main(String[] args) throws SQLException {
		Connection con = HiveUtil.getConnection();
		// create statement
		Statement stmt = con.createStatement();
		// execute statement
		stmt.execute("ALTER TABLE " + HiveUtil.TABLE_EMP_API
				+ " CHANGE name ename String");
		stmt.execute("ALTER TABLE " + HiveUtil.TABLE_EMP_API
				+ " CHANGE salary salary Double");
		System.out.println("Change column successful.");
		con.close();
	}
}