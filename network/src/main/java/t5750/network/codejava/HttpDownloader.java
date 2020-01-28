package t5750.network.codejava;

import java.io.IOException;

public class HttpDownloader {
	public static void main(String[] args) {
		// String fileURL =
		// "http://jdbc.postgresql.org/download/postgresql-9.2-1002.jdbc4.jar";
		String fileURL = "https://codeload.github.com/T5750/java-repositories/zip/master";
		String saveDir = "E:/";
		try {
			HttpDownloadUtility.downloadFile(fileURL, saveDir);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
