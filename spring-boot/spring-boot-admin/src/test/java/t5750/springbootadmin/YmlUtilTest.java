package t5750.springbootadmin;

import t5750.springbootadmin.util.Globals;
import t5750.springbootadmin.util.YmlUtil;

public class YmlUtilTest {
	private static String username;
	static {
		username = YmlUtil.getProperty("security.user.name").toString();
	}

	public static void main(String[] args) {
		System.out.println(Globals.CONTEXT_PATH);
		System.out.println(username);
	}
}
