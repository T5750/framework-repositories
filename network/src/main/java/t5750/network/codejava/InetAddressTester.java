package t5750.network.codejava;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressTester {
	public static void main(String[] args) {
		InetAddress address1 = null;
		try {
			address1 = InetAddress.getByName("www.codejava.net");
			System.out.println(address1.getHostAddress());
			InetAddress address2 = InetAddress.getByName("8.8.8.8");
			System.out.println(address2.getHostName());
			InetAddress[] google = InetAddress.getAllByName("google.com");
			for (InetAddress addr : google) {
				System.out.println(addr.getHostAddress());
			}
			InetAddress localhost = InetAddress.getLocalHost();
			System.out.println(localhost);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}