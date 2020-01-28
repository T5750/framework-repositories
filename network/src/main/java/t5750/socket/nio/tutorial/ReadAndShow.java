package t5750.socket.nio.tutorial;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import t5750.socket.util.SocketUtil;

/**
 * 读取文件涉及三个步骤：(1) 从 FileInputStream 获取 Channel，(2) 创建 Buffer，(3) 将数据从 Channel 读到
 * Buffer 中
 */
public class ReadAndShow {
	static public void main(String args[]) throws Exception {
		FileInputStream fin = new FileInputStream(SocketUtil.NIO_PATH
				+ "readandshow.txt");
		FileChannel fc = fin.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		fc.read(buffer);
		buffer.flip();
		int i = 0;
		while (buffer.remaining() > 0) {
			byte b = buffer.get();
			System.out.println("Character " + i + ": " + ((char) b));
			i++;
		}
		fin.close();
	}
}
