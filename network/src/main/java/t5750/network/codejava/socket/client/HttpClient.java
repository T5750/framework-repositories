package t5750.network.codejava.socket.client;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * This program demonstrates a client socket application that connects to a web
 * server and send a HTTP HEAD request.
 *
 * @author www.codejava.net
 */
public class HttpClient {
	public static void main(String[] args) {
		if (args.length < 1)
			return;
		URL url;
		try {
			url = new URL(args[0]);
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
			return;
		}
		String hostname = url.getHost();
		int port = 80;
		try (Socket socket = new Socket(hostname, port)) {
			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);
			writer.println("HEAD " + url.getPath() + " HTTP/1.1");
			writer.println("Host: " + hostname);
			writer.println("User-Agent: Simple Http Client");
			writer.println("Accept: text/html");
			writer.println("Accept-Language: en-US");
			writer.println("Connection: close");
			writer.println();
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (UnknownHostException ex) {
			System.out.println("Server not found: " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("I/O error: " + ex.getMessage());
		}
	}
}
// java HttpClient http://www.codejava.net/java-core