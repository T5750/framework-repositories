package t5750.nio.echo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NioEchoUtil {
	public static final String THREAD_SERVER = "Server";
	public static final String THREAD_CLIENT = "Client";
	public static final SimpleDateFormat LONG_DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 得到时间戳格式字串
	 */
	public static String getTimeStampStr(Date date) {
		return LONG_DATE_FORMAT.format(date);
	}

	public static ByteBuffer write(SelectionKey key, ByteBuffer writeBuf) {
		try {
			SocketChannel sc = (SocketChannel) key.channel();
			sc.configureBlocking(false);
			byte[] resultBytes = new byte[1024];
			if (THREAD_SERVER.equals(Thread.currentThread().getName())) {
				// Thread.sleep(5000);
				resultBytes = getTimeStampStr(new Date()).getBytes();
			} else if (THREAD_CLIENT.equals(Thread.currentThread().getName())) {
				System.in.read(resultBytes);
			}
			writeBuf.put(resultBytes);
			writeBuf.flip();
			sc.write(writeBuf);
			writeBuf.clear();
			key.interestOps(SelectionKey.OP_READ);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return writeBuf;
	}

	public static ByteBuffer read(SelectionKey key, ByteBuffer readBuf) {
		try {
			// 1 清空缓冲区旧的数据
			readBuf.clear();
			// 2 获取之前注册的socket通道对象
			SocketChannel sc = (SocketChannel) key.channel();
			// 3 读取数据
			int count = sc.read(readBuf);
			// 4 如果没有数据
			if (count == -1) {
				key.channel().close();
				key.cancel();
				return readBuf;
			}
			// 5 有数据则进行读取 读取之前需要进行复位方法(把position 和limit进行复位)
			readBuf.flip();
			// 6 根据缓冲区的数据长度创建相应大小的byte数组，接收缓冲区的数据
			byte[] bytes = new byte[readBuf.remaining()];
			// 7 接收缓冲区数据
			readBuf.get(bytes);
			// 8 打印结果
			String body = new String(bytes).trim();
			System.out.println(Thread.currentThread().getName() + " receive: "
					+ body);
			// 9 可以写回给客户端数据
			key.interestOps(SelectionKey.OP_WRITE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return readBuf;
	}
}
