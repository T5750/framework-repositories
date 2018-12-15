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

	public static void waitForSeconds(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			// e.printStackTrace();
		}
	}

	public static void waitForMillis(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			// e.printStackTrace();
		}
	}
}