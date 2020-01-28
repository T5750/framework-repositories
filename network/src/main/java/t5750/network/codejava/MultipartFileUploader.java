package t5750.network.codejava;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This program demonstrates a usage of the MultipartUtility class.
 * 
 * @author www.codejava.net
 *
 */
public class MultipartFileUploader {
	public static void main(String[] args) {
		String charset = "UTF-8";
		File uploadFile1 = new File("e:/Test/PIC1.JPG");
		File uploadFile2 = new File("e:/Test/PIC2.JPG");
		String requestURL = "http://localhost:8080/FileUploadSpringMVC/uploadFile.do";
		try {
			MultipartUtility multipart = new MultipartUtility(requestURL,
					charset);
			multipart.addHeaderField("User-Agent", "CodeJava");
			multipart.addHeaderField("Test-Header", "Header-Value");
			multipart.addFormField("description", "Cool Pictures");
			multipart.addFormField("keywords", "Java,upload,Spring");
			multipart.addFilePart("fileUpload", uploadFile1);
			multipart.addFilePart("fileUpload", uploadFile2);
			List<String> response = multipart.finish();
			System.out.println("SERVER REPLIED:");
			for (String line : response) {
				System.out.println(line);
			}
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
}