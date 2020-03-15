package t5750.rest.jersey.service;

import java.io.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import t5750.rest.jersey.util.Globals;

@Path("/upload")
public class UploadService {
	@POST
	@Path("/jpg")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	public Response uploadJpgFile(
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData)
			throws Exception {
		try {
			int read = 0;
			byte[] bytes = new byte[1024];
			OutputStream out = new FileOutputStream(
					new File(Globals.UPLOAD_PATH + fileMetaData.getFileName()));
			while ((read = fileInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			throw new WebApplicationException(
					"Error while uploading file. Please try again !!");
		}
		return Response
				.ok(fileMetaData.getFileName() + " uploaded successfully !!")
				.build();
	}
}