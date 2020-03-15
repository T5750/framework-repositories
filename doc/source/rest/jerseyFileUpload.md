# Jersey File Upload

## Jersey maven multipart dependency
```xml
<dependency>
    <groupId>org.glassfish.jersey.media</groupId>
    <artifactId>jersey-media-multipart</artifactId>
    <version>2.19</version>
</dependency>
```

## Add Jersey MultiPartFeature in web.xml
Further, you are required to add `MultiPartFeature` in Jersey configuration to let it know that you will use multipart requests.
```xml
<servlet>
    <servlet-name>jersey-serlvet</servlet-name>
    <servlet-class>org.glassfish.jersey.servlet.ServletContainer</servlet-class>
    <init-param>
        <param-name>jersey.config.server.provider.classnames</param-name>
        <param-value>org.glassfish.jersey.media.multipart.MultiPartFeature</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
</servlet>
```

## Write Jersey File Upload REST API
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

## Test file upload using HTML Form
```html
<form action="rest/upload/jpg" method="post" enctype="multipart/form-data">
	<p>Select a file : <input type="file" name="file" size="45" accept=".jpg"/></p>
	<input type="submit" value="Upload JPG" />
</form>
```

## Test file upload using jersey client
```java
public class JerseyFileUploadTest {
	@Test
	public void uploadFile() throws IOException {
		final Client client = ClientBuilder.newBuilder()
				.register(MultiPartFeature.class).build();
		final FileDataBodyPart filePart = new FileDataBodyPart("file",
				new File("C:/avatar.jpg"));
		FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
		final FormDataMultiPart multipart = (FormDataMultiPart) formDataMultiPart
				.field("foo", "bar").bodyPart(filePart);
		final WebTarget target = client
				.target(JerseyUtil.REST_URL + "/upload/jpg");
		final Response response = target.request()
				.post(Entity.entity(multipart, multipart.getMediaType()));
		// Use response object to verify upload success
		formDataMultiPart.close();
		multipart.close();
	}
}
```

## References
- [Jersey file upload example – jersey 2 MultiPartFeature](https://howtodoinjava.com/jersey/jersey-file-upload-example/)