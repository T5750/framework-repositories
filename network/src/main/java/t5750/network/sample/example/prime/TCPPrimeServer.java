package t5750.network.sample.example.prime;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 以TCP方式实现的质数判别服务器端
 */
public class TCPPrimeServer {
	public static void main(String[] args) {
		final int PORT = 10005;
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(PORT);
			System.out.println("服务器端已启动：");
			while (true) {
				Socket s = ss.accept();
				new PrimeLogicThread(s);
			}
		} catch (Exception e) {
		} finally {
			try {
				ss.close();
			} catch (Exception e2) {
			}
		}
	}
}
