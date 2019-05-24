package t5750.hbase.admin;

import java.io.IOException;

import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;

import t5750.hbase.util.HBaseUtil;

public class ListTables {
	public static void main(String args[]) throws MasterNotRunningException,
			IOException {
		// Getting all the list of tables using HBaseAdmin object
		HTableDescriptor[] tableDescriptor = HBaseUtil.getAdmin().listTables();
		// printing all the table names.
		for (int i = 0; i < tableDescriptor.length; i++) {
			System.out.println(tableDescriptor[i].getNameAsString());
		}
	}
}