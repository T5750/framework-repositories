package t5750.nio.echo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import t5750.util.SocketUtil;

public class NioEchoServer implements Runnable {
	// 1 多路复用器（管理所有的通道）
	private Selector selector;
	// 2 建立缓冲区
	private ByteBuffer readBuf = ByteBuffer.allocate(1024);
	// 3
	private ByteBuffer writeBuf = ByteBuffer.allocate(1024);

	public NioEchoServer(int port) {
		try {
			// 1 打开路复用器
			this.selector = Selector.open();
			// 2 打开服务器通道
			ServerSocketChannel ssc = ServerSocketChannel.open();
			// 3 设置服务器通道为非阻塞模式
			ssc.configureBlocking(false);
			// 4 绑定地址
			ssc.bind(new InetSocketAddress(port));
			// 5 把服务器通道注册到多路复用器上，并且监听阻塞事件
			ssc.register(this.selector, SelectionKey.OP_ACCEPT);
			System.out.println("NioChatServer start, port :" + port);
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
						// 7 如果为阻塞状态
						if (key.isAcceptable()) {
							this.accept(key);
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

	private void accept(SelectionKey key) {
		try {
			// 1 获取服务通道
			ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
			// 2 执行阻塞方法（等待客户端的通道）
			SocketChannel sc = ssc.accept();
			// 3 设置阻塞模式
			sc.configureBlocking(false);
			// 4 注册到多路复用器上，并设置读取标识
//			sc.register(this.selector, SelectionKey.OP_READ
//					| SelectionKey.OP_WRITE);
			sc.register(this.selector, SelectionKey.OP_READ);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Thread(new NioEchoServer(SocketUtil.PORT_8765),
				NioEchoUtil.THREAD_SERVER).start();
	}
}
