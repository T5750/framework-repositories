package t5750.network.sample.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * 可以并发处理数据包的服务器端 功能为：显示客户端发送的内容，并向客户端反馈字符串“OK”
 */
public class MulUDPServer {
	public static void main(String[] args) {
		DatagramSocket ds = null; // 连接对象
		DatagramPacket receiveDp; // 接收数据包对象
		final int PORT = 10012; // 端口
		byte[] b = new byte[1024];
		receiveDp = new DatagramPacket(b, b.length);
		try {
			// 建立连接，监听端口
			ds = new DatagramSocket(PORT);
			System.out.println("服务器端已启动：");
			while (true) {
				// 接收
				ds.receive(receiveDp);
				// 启动线程处理数据包
				new LogicThread(ds, receiveDp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭连接
				ds.close();
			} catch (Exception e) {
			}
		}
	}
}