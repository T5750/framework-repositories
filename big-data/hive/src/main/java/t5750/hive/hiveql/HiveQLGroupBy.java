package t5750.hive.hiveql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import t5750.hive.util.HiveUtil;

public class HiveQLGroupBy {
	public static void main(String[] args) throws SQLException {
		Connection con = HiveUtil.getConnection();
		// create statement
		Statement stmt = con.createStatement();
		// execute statement
		ResultSet res = stmt.executeQuery("SELECT Dept,count(*) " + "FROM "
				+ HiveUtil.TABLE_EMPLOYEE + " GROUP BY DEPT");
		System.out.println(" Dept \t count(*)");
		while (res.next()) {
			System.out.println(res.getString(1) + " " + res.getInt(2));
		}
		con.close();
	}
}