package t5750.springboot.util;

import javax.servlet.http.HttpServletRequest;

public class Globals {
	// /var/tmp/
	public static final String UPLOAD_PATH = "D:\\code\\";

	public static String getBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		return basePath;
	}
}