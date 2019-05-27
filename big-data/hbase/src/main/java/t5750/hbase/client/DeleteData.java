package t5750.hbase.client;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.util.Bytes;

import t5750.hbase.util.HBaseUtil;

public class DeleteData {
	public static void main(String[] args) throws IOException {
		// Instantiating Configuration class
		Configuration conf = HBaseUtil.getConfig();
		// Instantiating HTable class
		HTable table = new HTable(conf, HBaseUtil.TABLE_NAME);
		// Instantiating Delete class
		Delete delete = new Delete(Bytes.toBytes("row1"));
		delete.deleteColumn(Bytes.toBytes("personal"), Bytes.toBytes("name"));
		delete.deleteFamily(Bytes.toBytes("professional"));
		// deleting the data
		table.delete(delete);
		// closing the HTable object
		table.close();
		System.out.println("data deleted.....");
	}
}
