# Jersey Multi-File Upload

## Write Upload REST API
```java
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
```

## HTML/Ajax code
```html
<script src="js/index.js"></script>
<form action="jersey/upload/jpg" method="post" enctype="multipart/form-data">
	<div class="form-group">
		<label for="formMultiFile1">Select a file 1 : </label>
		<input type="file" name="file" size="45" accept=".jpg" class="form-control-file" id="formMultiFile1"/>
		<label for="formMultiFile2">Select a file 2 : </label>
		<input type="file" name="file" size="45" accept=".jpg" class="form-control-file" id="formMultiFile2"/>
	</div>
	<input id="uploadBtn" type="button" value="Upload JPG Files" class="btn btn-outline-primary"/>
</form>
```

## References
- [Jersey – Ajax Multi-File Upload Example](https://howtodoinjava.com/jersey/jax-rs-jersey-ajax-multi-file-upload-example/)