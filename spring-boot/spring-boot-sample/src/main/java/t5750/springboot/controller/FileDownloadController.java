package t5750.springboot.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import t5750.springboot.util.Globals;

@RestController
public class FileDownloadController {
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public ResponseEntity<Object> downloadFile() throws IOException {
		String filename = Globals.UPLOAD_PATH + "impala.png";
		File file = new File(filename);
		InputStreamResource resource = new InputStreamResource(
				new FileInputStream(file));
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition",
				String.format("attachment; filename=\"%s\"", file.getName()));
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		ResponseEntity<Object> responseEntity = ResponseEntity.ok()
				.headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/txt"))
				.body(resource);
		return responseEntity;
	}
}