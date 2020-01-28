package t5750.network.sample.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 逻辑处理线程
 */
public class LogicThread extends Thread {
	/** 连接对象 */
	DatagramSocket ds;
	/** 接收到的数据包 */
	DatagramPacket dp;

	public LogicThread(DatagramSocket ds, DatagramPacket dp) {
		this.ds = ds;
		this.dp = dp;
		start(); // 启动线程
	}

	public void run() {
		try {
			// 获得缓冲数组
			byte[] data = dp.getData();
			// 获得有效数据长度
			int len = dp.getLength();
			// 客户端IP
			InetAddress clientAddress = dp.getAddress();
			// 客户端端口
			int clientPort = dp.getPort();
			// 输出
			System.out.println("客户端IP：" + clientAddress.getHostAddress());
			System.out.println("客户端端口号：" + clientPort);
			System.out.println("客户端发送内容：" + new String(data, 0, len));
			// 反馈到客户端
			byte[] b = "OK".getBytes();
			DatagramPacket sendDp = new DatagramPacket(b, b.length,
					clientAddress, clientPort);
			// 发送
			ds.send(sendDp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}