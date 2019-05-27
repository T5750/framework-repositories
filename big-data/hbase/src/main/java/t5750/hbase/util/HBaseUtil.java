package t5750.hbase.util;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

/**
 * 
 */
public class HBaseUtil {
	public static final String TABLE_NAME = "emp_api";

	public static Configuration getConfig() {
		// Instantiating configuration class
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		conf.set("hbase.zookeeper.quorum", "192.168.100.210");
		return conf;
	}

	public static Admin getAdmin() throws IOException {
		Connection connection = ConnectionFactory.createConnection(getConfig());
		// Instantiating HbaseAdmin class
		// HBaseAdmin admin = new HBaseAdmin(conf);
		Admin admin = connection.getAdmin();
		return admin;
	}
}
