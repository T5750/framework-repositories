package t5750.hbase.admin;

import java.io.IOException;

import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;

import t5750.hbase.util.HBaseUtil;

public class DisableTable {
	public static void main(String args[]) throws MasterNotRunningException,
			IOException {
		Admin admin = HBaseUtil.getAdmin();
		// Verifying weather the table is disabled
		Boolean bool = admin.isTableDisabled(TableName
				.valueOf(HBaseUtil.TABLE_NAME));
		System.out.println(bool);
		// Disabling the table using HBaseAdmin object
		if (!bool) {
			admin.disableTable(TableName.valueOf(HBaseUtil.TABLE_NAME));
			System.out.println("Table disabled");
		}
	}
}