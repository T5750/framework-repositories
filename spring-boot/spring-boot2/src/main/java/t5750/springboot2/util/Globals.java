package t5750.springboot2.util;

import javax.servlet.http.HttpServletRequest;

public class Globals {
	public static final String BOOT2PROVIDER_PATH = "http://localhost:18091/";

	public static String getBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		return basePath;
	}
}
