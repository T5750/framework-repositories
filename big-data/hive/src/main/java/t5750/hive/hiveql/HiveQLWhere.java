package t5750.hive.hiveql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import t5750.hive.util.HiveUtil;

public class HiveQLWhere {
	public static void main(String[] args) throws SQLException {
		Connection con = HiveUtil.getConnection();
		// create statement
		Statement stmt = con.createStatement();
		// execute statement
		ResultSet res = stmt.executeQuery("SELECT * FROM "
				+ HiveUtil.TABLE_EMPLOYEE + " WHERE salary>30000");
		System.out.println("Result:");
		System.out.println(" ID \t Name \t Salary \t Designation \t Dept ");
		while (res.next()) {
			System.out.println(res.getInt(1) + " " + res.getString(2) + " "
					+ res.getDouble(3) + " " + res.getString(4) + " "
					+ res.getString(5));
		}
		con.close();
	}
}