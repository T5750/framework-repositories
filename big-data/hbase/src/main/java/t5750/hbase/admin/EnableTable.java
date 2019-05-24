package t5750.hbase.admin;

import java.io.IOException;

import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;

import t5750.hbase.util.HBaseUtil;

public class EnableTable {
	public static void main(String args[]) throws MasterNotRunningException,
			IOException {
		Admin admin = HBaseUtil.getAdmin();
		// Verifying whether the table is disabled
		Boolean bool = admin.isTableEnabled(TableName
				.valueOf(HBaseUtil.TABLE_NAME));
		System.out.println(bool);
		// Enabling the table using HBaseAdmin object
		if (!bool) {
			admin.enableTable(TableName.valueOf(HBaseUtil.TABLE_NAME));
			System.out.println("Table Enabled");
		}
	}
}