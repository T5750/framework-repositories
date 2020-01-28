package t5750.socket.nio.tutorial;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * NIO 入门<br/>
 * https://www.ibm.com/developerworks/cn/education/java/j-nio/j-nio.html
 */
public class CopyFile {
	static public void main(String args[]) throws Exception {
		if (args.length < 2) {
			System.err.println("Usage: java CopyFile infile outfile");
			System.exit(1);
		}
		String infile = args[0];
		String outfile = args[1];
		FileInputStream fin = new FileInputStream(infile);
		FileOutputStream fout = new FileOutputStream(outfile);
		FileChannel fcin = fin.getChannel();
		FileChannel fcout = fout.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		while (true) {
			buffer.clear();
			int r = fcin.read(buffer);
			if (r == -1) {
				break;
			}
			buffer.flip();
			fcout.write(buffer);
		}
	}
}
