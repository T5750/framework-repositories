package t5750.network.sample.example.prime;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 以TCP方式实现的质数判断客户端程序
 */
public class TCPPrimeClient {
	static BufferedReader br;
	static Socket socket;
	static InputStream is;
	static OutputStream os;
	/** 服务器IP */
	final static String HOST = "127.0.0.1";
	/** 服务器端端口 */
	final static int PORT = 10005;

	public static void main(String[] args) {
		init(); // 初始化
		while (true) {
			System.out.println("请输入数字：");
			String input = readInput(); // 读取输入
			if (isQuit(input)) { // 判读是否结束
				byte[] b = "quit".getBytes();
				send(b);
				break; // 结束程序
			}
			if (checkInput(input)) { // 校验合法
				// 发送数据
				send(input.getBytes());
				// 接收数据
				byte[] data = receive();
				// 解析反馈数据
				parse(data);
			} else {
				System.out.println("输入不合法，请重新输入！");
			}
		}
		close(); // 关闭流和连接
	}

	/**
	 * 初始化
	 */
	private static void init() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			socket = new Socket(HOST, PORT);
			is = socket.getInputStream();
			os = socket.getOutputStream();
		} catch (Exception e) {
		}
	}

	/**
	 * 读取客户端输入
	 */
	private static String readInput() {
		try {
			return br.readLine();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 判断是否输入quit
	 * 
	 * @param input
	 *            输入内容
	 * @return true代表结束，false代表不结束
	 */
	private static boolean isQuit(String input) {
		if (input == null) {
			return false;
		} else {
			if ("quit".equalsIgnoreCase(input)) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 校验输入
	 * 
	 * @param input
	 *            用户输入内容
	 * @return true代表输入符合要求，false代表不符合
	 */
	private static boolean checkInput(String input) {
		if (input == null) {
			return false;
		}
		try {
			int n = Integer.parseInt(input);
			if (n >= 2) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false; // 输入不是整数
		}
	}

	/**
	 * 向服务器端发送数据
	 * 
	 * @param data
	 *            数据内容
	 */
	private static void send(byte[] data) {
		try {
			os.write(data);
		} catch (Exception e) {
		}
	}

	/**
	 * 接收服务器端反馈
	 * 
	 * @return 反馈数据
	 */
	private static byte[] receive() {
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
	 * 解析协议数据
	 * 
	 * @param data
	 *            协议数据
	 */
	private static void parse(byte[] data) {
		if (data == null) {
			System.out.println("服务器端反馈数据不正确！");
			return;
		}
		byte value = data[0]; // 取第一个byte
		// 按照协议格式解析
		switch (value) {
		case 0:
			System.out.println("质数");
			break;
		case 1:
			System.out.println("不是质数");
			break;
		case 2:
			System.out.println("协议格式错误");
			break;
		}
	}

	/**
	 * 关闭流和连接
	 */
	private static void close() {
		try {
			br.close();
			is.close();
			os.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}