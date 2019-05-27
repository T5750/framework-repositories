package t5750.hbase.client;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import t5750.hbase.util.HBaseUtil;

public class UpdateData {
	public static void main(String[] args) throws IOException {
		// Instantiating Configuration class
		Configuration config = HBaseUtil.getConfig();
		// Instantiating HTable class
		HTable hTable = new HTable(config, HBaseUtil.TABLE_NAME);
		// Instantiating Put class
		// accepts a row name
		Put p = new Put(Bytes.toBytes("row1"));
		// Updating a cell value
		p.add(Bytes.toBytes("personal"), Bytes.toBytes("city"),
				Bytes.toBytes("Delih"));
		// Saving the put Instance to the HTable.
		hTable.put(p);
		System.out.println("data Updated");
		// closing HTable
		hTable.close();
	}
}