package t5750.rest.jersey.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import t5750.rest.jersey.util.Globals;

@Path("/download")
public class DownloadService {
	@GET
	@Path("/jpg")
	public Response downloadJpgFile() {
		StreamingOutput fileStream = new StreamingOutput() {
			@Override
			public void write(java.io.OutputStream output)
					throws IOException, WebApplicationException {
				try {
					java.nio.file.Path path = Paths
							.get(Globals.UPLOAD_PATH + "avatar.jpg");
					byte[] data = Files.readAllBytes(path);
					output.write(data);
					output.flush();
				} catch (Exception e) {
					throw new WebApplicationException("File Not Found !!");
				}
			}
		};
		return Response.ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
				.header("content-disposition",
						"attachment; filename = myfile.jpg")
				.build();
	}
}