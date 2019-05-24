package t5750.hbase.admin;

import java.io.IOException;

import org.apache.hadoop.hbase.TableName;

import t5750.hbase.util.HBaseUtil;

public class TableExists {
	public static void main(String args[]) throws IOException {
		// Verifying the existance of the table
		boolean bool = HBaseUtil.getAdmin().tableExists(
				TableName.valueOf(HBaseUtil.TABLE_NAME));
		System.out.println(bool);
	}
}
