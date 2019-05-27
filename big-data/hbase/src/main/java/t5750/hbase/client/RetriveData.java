package t5750.hbase.client;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import t5750.hbase.util.HBaseUtil;

public class RetriveData {
	public static void main(String[] args) throws IOException, Exception {
		// Instantiating Configuration class
		Configuration config = HBaseUtil.getConfig();
		// Instantiating HTable class
		HTable table = new HTable(config, HBaseUtil.TABLE_NAME);
		// Instantiating Get class
		Get g = new Get(Bytes.toBytes("row1"));
		// Reading the data
		Result result = table.get(g);
		// Reading values from Result class object
		byte[] value = result.getValue(Bytes.toBytes("personal"),
				Bytes.toBytes("name"));
		byte[] value1 = result.getValue(Bytes.toBytes("personal"),
				Bytes.toBytes("city"));
		// Printing the values
		String name = Bytes.toString(value);
		String city = Bytes.toString(value1);
		System.out.println("name: " + name + " city: " + city);
	}
}