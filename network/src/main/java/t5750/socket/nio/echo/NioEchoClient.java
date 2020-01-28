package t5750.socket.nio.echo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import t5750.socket.util.SocketUtil;

public class NioEchoClient implements Runnable {
	// 1 多路复用器（管理所有的通道）
	private Selector selector;
	// 2 建立缓冲区
	private ByteBuffer readBuf = ByteBuffer.allocate(1024);
	// 3
	private ByteBuffer writeBuf = ByteBuffer.allocate(1024);

	public NioEchoClient(String hostname, int port) {
		try {
			// 1 打开路复用器
			this.selector = Selector.open();
			// 2 打开通道
			SocketChannel sc = SocketChannel.open();
			// 3 设置服务器通道为非阻塞模式
			sc.configureBlocking(false);
			// 进行连接
			sc.connect(new InetSocketAddress(hostname, port));
			sc.register(this.selector, SelectionKey.OP_CONNECT
					| SelectionKey.OP_WRITE | SelectionKey.OP_READ);
			System.out.println("NioChatClient start, port :" + port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				// 1 必须要让多路复用器开始监听
				this.selector.select();
				// 2 返回多路复用器已经选择的结果集
				Iterator<SelectionKey> keys = this.selector.selectedKeys()
						.iterator();
				// 3 进行遍历
				while (keys.hasNext()) {
					// 4 获取一个选择的元素
					SelectionKey key = keys.next();
					// 5 直接从容器中移除就可以了
					keys.remove();
					// 6 如果是有效的
					if (key.isValid()) {
						if (key.isConnectable()) {
							SocketChannel sc = (SocketChannel) key.channel();
							sc.configureBlocking(false);
							sc.finishConnect();
						}
						// 8 如果为可读状态
						if (key.isReadable()) {
							readBuf = NioEchoUtil.read(key, readBuf);
						}
						// 9 写数据
						if (key.isWritable()) {
							writeBuf = NioEchoUtil.write(key, writeBuf);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Thread(
				new NioEchoClient(SocketUtil.INET_HOST, SocketUtil.PORT_8765),
				NioEchoUtil.THREAD_CLIENT).start();
	}
}
