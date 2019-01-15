package t5750.tcctransaction.utils;

import java.nio.ByteBuffer;

/**
 */
public class ByteUtils {
	public static byte[] longToBytes(long l) {
		return ByteBuffer.allocate(8).putLong(l).array();
	}

	public static long bytesToLong(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getLong();
	}
}
