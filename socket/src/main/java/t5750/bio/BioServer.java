package t5750.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import t5750.util.SocketUtil;

public class BioServer {
	public static void main(String[] args) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(SocketUtil.PORT_8765);
			System.out.println("BioServer start ...");
			// 进行阻塞
			Socket socket = server.accept();
			// 新建一个线程执行客户端的任务
			new Thread(new BioServerHandler(socket)).start();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			server = null;
		}
	}
}
