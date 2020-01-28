package t5750.socket.nio.tutorial;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import t5750.socket.util.SocketUtil;

public class UseMappedFile {
	static private final int start = 0;
	static private final int size = 1024;

	static public void main(String args[]) throws Exception {
		RandomAccessFile raf = new RandomAccessFile(SocketUtil.NIO_PATH
				+ "readandshow.txt", "rw");
		FileChannel fc = raf.getChannel();
		MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, start,
				size);
		mbb.put(0, (byte) 97);
		mbb.put(1023, (byte) 122);
		raf.close();
	}
}
