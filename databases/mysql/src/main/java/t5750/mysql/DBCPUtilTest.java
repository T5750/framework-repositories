package t5750.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import t5750.mysql.util.DBCPUtil;
import t5750.mysql.util.Globals;

public class DBCPUtilTest {
	public static void main(String[] args) throws Exception {
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		String sql = "Select * from " + Globals.TEST_TABLE;
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			conn = DBCPUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String name = rs.getString(Globals.TEST_TABLE_COLUMN_LABEL);
			}
			DBCPUtil.closeAll(rs, stmt, conn);
		}
		System.out.println("经过100次的循环调用，不使用连接池花费的时间:"
				+ (System.currentTimeMillis() - start) + "ms");
	}
}
