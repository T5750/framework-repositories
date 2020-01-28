package t5750.network.codejava.socket.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This program is a socket client application that connects to a time server to
 * get the current date time.
 *
 * @author www.codejava.net
 */
public class TimeClient {
	public static void main(String[] args) {
		String hostname = "time.nist.gov";
		int port = 13;
		try (Socket socket = new Socket(hostname, port)) {
			InputStream input = socket.getInputStream();
			InputStreamReader reader = new InputStreamReader(input);
			int character;
			StringBuilder data = new StringBuilder();
			while ((character = reader.read()) != -1) {
				data.append((char) character);
			}
			System.out.println(data);
		} catch (UnknownHostException ex) {
			System.out.println("Server not found: " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("I/O error: " + ex.getMessage());
		}
	}
}