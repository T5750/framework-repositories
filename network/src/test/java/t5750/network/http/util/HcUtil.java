package t5750.network.http.util;

public class HcUtil {
	public static final String HTTPCLIENT_URL = "http://www.example.com";
	public static final String HOSTNAME = "www.baeldung.com";
	public static final int PORT = 80;
	public static final String BAELDUNG_URL = "http://" + HOSTNAME;
	public static final String BAELDUNG_SSL_URL = "https://" + HOSTNAME;
	/**
	 * spring-boot/spring-boot-security/BootSecurityApplication
	 */
	public static final String SECURITY_HOSTNAME = "localhost";
	public static final int SECURITY_PORT = 8072;
	public static final String SECURITY_URL = "http://" + SECURITY_HOSTNAME;
	public static final String SECURITY_PRODUCTS_URL = SECURITY_URL + ":"
			+ SECURITY_PORT + "/view-products";
	public static final String SECURITY_USERNAME = "user";
	public static final String SECURITY_PASSWORD = "pass";
	public static final int TIMEOUT = 5 * 1000; // seconds
}