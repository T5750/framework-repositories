package t5750.hive.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import t5750.hive.util.HiveUtil;

public class HiveCreateDb {
	public static void main(String[] args) throws SQLException {
		Connection con = HiveUtil.getConnection();
		Statement stmt = con.createStatement();
		stmt.execute("CREATE DATABASE " + HiveUtil.DATABASE_NAME);
		System.out.println("Database " + HiveUtil.DATABASE_NAME
				+ " created successfully.");
		con.close();
	}
}