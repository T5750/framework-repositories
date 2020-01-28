package t5750.network.codejava.socket.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This program demonstrates a simple TCP/IP socket server that echoes every
 * message from the client in reversed form. This server is single-threaded.
 *
 * @author www.codejava.net
 */
public class ReverseServer {
	public static void main(String[] args) {
		if (args.length < 1)
			return;
		int port = Integer.parseInt(args[0]);
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Server is listening on port " + port);
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("New client connected");
				new ServerThread(socket).start();
				InputStream input = socket.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(input));
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
			}
		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
// java ReverseServer 9090