package t5750.network.sample.example.guess;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 猜数字客户端
 */
public class TCPClient {
	public static void main(String[] args) {
		Socket socket = null;
		OutputStream os = null;
		InputStream is = null;
		BufferedReader br = null;
		byte[] data = new byte[2];
		try {
			// 建立连接
			socket = new Socket("127.0.0.1", 10001);
			// 发送数据
			os = socket.getOutputStream();
			// 读取反馈数据
			is = socket.getInputStream();
			// 键盘输入流
			br = new BufferedReader(new InputStreamReader(System.in));
			// 多次输入
			while (true) {
				System.out.println("请输入数字：");
				// 接收输入
				String s = br.readLine();
				// 结束条件
				if (s.equals("quit")) {
					os.write("quit".getBytes());
					break;
				}
				// 校验输入是否合法
				boolean b = true;
				try {
					Integer.parseInt(s);
				} catch (Exception e) {
					b = false;
				}
				if (b) { // 输入合法
							// 发送数据
					os.write(s.getBytes());
					// 接收反馈
					is.read(data);
					// 判断
					switch (data[0]) {
					case 0:
						System.out.println("相等！祝贺你！");
						break;
					case 1:
						System.out.println("大了！");
						break;
					case 2:
						System.out.println("小了！");
						break;
					default:
						System.out.println("其它错误！");
					}
					// 提示猜的次数
					System.out.println("你已经猜了" + data[1] + "次！");
					// 判断次数是否达到5次
					if (data[1] >= 5) {
						System.out.println("你挂了！");
						// 给服务器端线程关闭的机会
						os.write("quit".getBytes());
						// 结束客户端程序
						break;
					}
				} else { // 输入错误
					System.out.println("输入错误！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭连接
				br.close();
				is.close();
				os.close();
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}