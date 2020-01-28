package t5750.network.sample.example.prime;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 实现质数判别逻辑的线程
 */
public class PrimeLogicThread extends Thread {
	Socket socket;
	InputStream is;
	OutputStream os;

	public PrimeLogicThread(Socket socket) {
		this.socket = socket;
		init();
		start();
	}

	/**
	 * 初始化
	 */
	private void init() {
		try {
			is = socket.getInputStream();
			os = socket.getOutputStream();
		} catch (Exception e) {
		}
	}

	public void run() {
		while (true) {
			// 接收客户端反馈
			byte[] data = receive();
			// 判断是否是退出
			if (isQuit(data)) {
				break; // 结束循环
			}
			// 逻辑处理
			byte[] b = logic(data);
			// 反馈数据
			send(b);
		}
		close();
	}

	/**
	 * 接收客户端数据
	 * 
	 * @return 客户端发送的数据
	 */
	private byte[] receive() {
		byte[] b = new byte[1024];
		try {
			int n = is.read(b);
			byte[] data = new byte[n];
			// 复制有效数据
			System.arraycopy(b, 0, data, 0, n);
			return data;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 向客户端发送数据
	 * 
	 * @param data
	 *            数据内容
	 */
	private void send(byte[] data) {
		try {
			os.write(data);
		} catch (Exception e) {
		}
	}

	/**
	 * 判断是否是quit
	 * 
	 * @return 是返回true，否则返回false
	 */
	private boolean isQuit(byte[] data) {
		if (data == null) {
			return false;
		} else {
			String s = new String(data);
			if (s.equalsIgnoreCase("quit")) {
				return true;
			} else {
				return false;
			}
		}
	}

	private byte[] logic(byte[] data) {
		// 反馈数组
		byte[] b = new byte[1];
		// 校验参数
		if (data == null) {
			b[0] = 2;
			return b;
		}
		try {
			// 转换为数字
			String s = new String(data);
			int n = Integer.parseInt(s);
			// 判断是否是质数
			if (n >= 2) {
				boolean flag = isPrime(n);
				if (flag) {
					b[0] = 0;
				} else {
					b[0] = 1;
				}
			} else {
				b[0] = 2; // 格式错误
				System.out.println(n);
			}
		} catch (Exception e) {
			e.printStackTrace();
			b[0] = 2;
		}
		return b;
	}

	/**
	 *
	 * @param n
	 * @return
	 */
	private boolean isPrime(int n) {
		boolean b = true;
		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (n % i == 0) {
				b = false;
				break;
			}
		}
		return b;
	}

	/**
	 * 关闭连接
	 */
	private void close() {
		try {
			is.close();
			os.close();
			socket.close();
		} catch (Exception e) {
		}
	}
}