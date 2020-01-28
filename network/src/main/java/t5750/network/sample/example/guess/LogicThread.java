package t5750.network.sample.example.guess;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

/**
 * 逻辑处理线程
 */
public class LogicThread extends Thread {
	Socket s;
	static Random r = new Random();

	public LogicThread(Socket s) {
		this.s = s;
		start(); // 启动线程
	}

	public void run() {
		// 生成一个[0，50]的随机数
		int randomNumber = Math.abs(r.nextInt() % 51);
		// 用户猜的次数
		int guessNumber = 0;
		InputStream is = null;
		OutputStream os = null;
		byte[] data = new byte[2];
		try {
			// 获得输入流
			is = s.getInputStream();
			// 获得输出流
			os = s.getOutputStream();
			while (true) { // 多次处理
				// 读取客户端发送的数据
				byte[] b = new byte[1024];
				int n = is.read(b);
				String send = new String(b, 0, n);
				// 结束判别
				if (send.equals("quit")) {
					break;
				}
				// 解析、判断
				try {
					int num = Integer.parseInt(send);
					// 处理
					guessNumber++; // 猜的次数增加1
					data[1] = (byte) guessNumber;
					// 判断
					if (num > randomNumber) {
						data[0] = 1;
					} else if (num < randomNumber) {
						data[0] = 2;
					} else {
						data[0] = 0;
						// 如果猜对
						guessNumber = 0; // 清零
						randomNumber = Math.abs(r.nextInt() % 51);
					}
					// 反馈给客户端
					os.write(data);
				} catch (Exception e) { // 数据格式错误
					data[0] = 3;
					data[1] = (byte) guessNumber;
					os.write(data); // 发送错误标识
					break;
				}
				os.flush(); // 强制发送
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				os.close();
				s.close();
			} catch (Exception e) {
			}
		}
	}
}