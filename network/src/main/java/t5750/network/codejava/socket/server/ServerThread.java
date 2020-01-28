package t5750.network.codejava.socket.server;

import java.io.*;
import java.net.Socket;

/**
 * This thread is responsible to handle client connection.
 *
 * @author www.codejava.net
 */
public class ServerThread extends Thread {
	private Socket socket;

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));
			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);
			String text;
			do {
				text = reader.readLine();
				String reverseText = new StringBuilder(text).reverse()
						.toString();
				writer.println("Server: " + reverseText);
			} while (!text.equals("bye"));
			socket.close();
		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}