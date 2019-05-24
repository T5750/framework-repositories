package t5750.hbase.admin;

import java.io.IOException;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;

import t5750.hbase.util.HBaseUtil;

public class DeleteTable {
	public static void main(String[] args) throws IOException {
		Admin admin = HBaseUtil.getAdmin();
		// disabling table named emp
		admin.disableTable(TableName.valueOf(HBaseUtil.TABLE_NAME));
		// Deleting emp
		admin.deleteTable(TableName.valueOf(HBaseUtil.TABLE_NAME));
		System.out.println("Table deleted");
	}
}