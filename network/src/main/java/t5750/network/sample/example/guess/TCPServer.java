package t5750.network.sample.example.guess;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP连接方式的服务器端 实现功能：接收客户端的数据，判断数字关系
 */
public class TCPServer {
	public static void main(String[] args) {
		try {
			// 监听端口
			ServerSocket ss = new ServerSocket(10001);
			System.out.println("服务器已启动：");
			// 逻辑处理
			while (true) {
				// 获得连接
				Socket s = ss.accept();
				// 启动线程处理
				new LogicThread(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}