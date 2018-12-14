package t5750.storm.util;

import java.io.File;

public class StormUtil {
	public static final String WINDOWS_FILE_DIR = "D:\\code\\";

	public static void hasDirectory() {
		File file = new File(WINDOWS_FILE_DIR);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		} else {
		}
	}
}