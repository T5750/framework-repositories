package t5750.hbase.admin;

import java.io.IOException;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;

import t5750.hbase.util.HBaseUtil;

public class CreateTable {
	public static void main(String[] args) throws IOException {
		// Instantiating table descriptor class
		HTableDescriptor tableDescriptor = new HTableDescriptor(
				TableName.valueOf(HBaseUtil.TABLE_NAME));
		// Adding column families to table descriptor
		tableDescriptor.addFamily(new HColumnDescriptor("personal"));
		tableDescriptor.addFamily(new HColumnDescriptor("professional"));
		// Execute the table through admin
		HBaseUtil.getAdmin().createTable(tableDescriptor);
		System.out.println(" Table created ");
	}
}