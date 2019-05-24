package t5750.hbase.admin;

import java.io.IOException;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;

import t5750.hbase.util.HBaseUtil;

public class AddColumn {
	public static void main(String args[]) throws MasterNotRunningException,
			IOException {
		// Instantiating columnDescriptor class
		HColumnDescriptor columnDescriptor = new HColumnDescriptor(
				"contactDetails");
		// Adding column family
		HBaseUtil.getAdmin().addColumn(TableName.valueOf(HBaseUtil.TABLE_NAME),
				columnDescriptor);
		System.out.println("column added");
	}
}