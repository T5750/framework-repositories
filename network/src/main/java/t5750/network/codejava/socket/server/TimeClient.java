package t5750.network.codejava.socket.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This program demonstrates a simple TCP/IP socket client.
 *
 * @author www.codejava.net
 */
public class TimeClient {
	public static void main(String[] args) {
		if (args.length < 2)
			return;
		String hostname = args[0];
		int port = Integer.parseInt(args[1]);
		try (Socket socket = new Socket(hostname, port)) {
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));
			String time = reader.readLine();
			System.out.println(time);
		} catch (UnknownHostException ex) {
			System.out.println("Server not found: " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("I/O error: " + ex.getMessage());
		}
	}
}
// java TimeClient localhost 6868