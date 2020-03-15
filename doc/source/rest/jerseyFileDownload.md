# Jersey File Download

## REST API to stream file with StreamingOutput
```java
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
```

## Jersey file download demo
- http://localhost:8080/rest/jersey/download/jpg
- The `filename` with which JPG file will be saved, will be what you set in `Response.header()` method.

## References
- [Jersey file download example – StreamingOutput](https://howtodoinjava.com/jersey/jersey-streamingoutput/)