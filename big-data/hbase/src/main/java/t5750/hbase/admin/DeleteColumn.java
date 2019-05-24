package t5750.hbase.admin;

import java.io.IOException;

import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;

import t5750.hbase.util.HBaseUtil;

public class DeleteColumn {
	public static void main(String args[]) throws MasterNotRunningException,
			IOException {
		// Deleting a column family
		HBaseUtil.getAdmin().deleteColumn(
				TableName.valueOf(HBaseUtil.TABLE_NAME),
				"contactDetails".getBytes());
		System.out.println("column deleted");
	}
}