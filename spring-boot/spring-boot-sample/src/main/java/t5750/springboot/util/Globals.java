package t5750.springboot.util;

import javax.servlet.http.HttpServletRequest;

public class Globals {
	// /var/tmp/
	public static final String UPLOAD_PATH = "D:\\code\\";
	public static final int SPRING_BOOT_SECURITY_PORT = 8072;
	public static final String SPRING_BOOT_SECURITY_ORIGIN = "http://localhost:"
			+ SPRING_BOOT_SECURITY_PORT;
	public static final String KAFKA_BOOTSTRAP_SERVERS = "192.168.100.163:9092";
	public static final String KAFKA_GROUP_ID = "group-id";
	public static final String KAFKA_TOPIC = "tutorialspoint";
	public static final String[] FILTER_EXCLUDE = { "/mini/", "favicon.ico" };

	public static String getBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		return basePath;
	}
}