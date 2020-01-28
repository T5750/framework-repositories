package t5750.network.codejava.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

/**
 * This program demonstrates how to implement a UDP client program.
 *
 *
 * @author www.codejava.net
 */
public class QuoteClient {
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Syntax: QuoteClient <hostname> <port>");
			return;
		}
		String hostname = args[0];
		int port = Integer.parseInt(args[1]);
		try {
			InetAddress address = InetAddress.getByName(hostname);
			DatagramSocket socket = new DatagramSocket();
			while (true) {
				DatagramPacket request = new DatagramPacket(new byte[1], 1,
						address, port);
				socket.send(request);
				byte[] buffer = new byte[512];
				DatagramPacket response = new DatagramPacket(buffer,
						buffer.length);
				socket.receive(response);
				String quote = new String(buffer, 0, response.getLength());
				System.out.println(quote);
				System.out.println();
				Thread.sleep(10000);
			}
		} catch (SocketTimeoutException ex) {
			System.out.println("Timeout error: " + ex.getMessage());
			ex.printStackTrace();
		} catch (IOException ex) {
			System.out.println("Client error: " + ex.getMessage());
			ex.printStackTrace();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
// java QuoteClient djxmmx.net 17
// java QuoteClient localhost 17