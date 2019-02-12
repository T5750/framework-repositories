package t5750.nio.tutorial;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import t5750.util.SocketUtil;

public class UseFileLocks {
	static private final int start = 10;
	static private final int end = 20;

	static public void main(String args[]) throws Exception {
		// Get file channel
		RandomAccessFile raf = new RandomAccessFile(SocketUtil.NIO_PATH
				+ "readandshow.txt", "rw");
		FileChannel fc = raf.getChannel();
		// Get lock
		System.out.println("trying to get lock");
		FileLock lock = fc.lock(start, end, false);
		System.out.println("got lock!");
		// Pause
		System.out.println("pausing");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException ie) {
		}
		// Release lock
		System.out.println("going to release lock");
		lock.release();
		System.out.println("released lock");
		raf.close();
	}
}
