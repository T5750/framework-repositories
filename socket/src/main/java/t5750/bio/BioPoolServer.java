package t5750.bio;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import t5750.util.SocketUtil;

public class BioPoolServer {
	public static void main(String[] args) {
		ServerSocket server = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			server = new ServerSocket(SocketUtil.PORT_8765);
			System.out.println("BioPoolServer start ...");
			Socket socket = null;
			HandlerExecutorPool executorPool = new HandlerExecutorPool(50, 1000);
			while (true) {
				socket = server.accept();
				executorPool.execute(new BioPoolServerHandler(socket));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if (server != null) {
				try {
					server.close();
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
			server = null;
		}
	}
}
