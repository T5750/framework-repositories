package t5750.hbase.admin;

import java.io.IOException;

import t5750.hbase.util.HBaseUtil;

public class ShutDownHbase {
	public static void main(String args[]) throws IOException {
		// Shutting down HBase
		System.out.println("Shutting down hbase");
		HBaseUtil.getAdmin().shutdown();
	}
}