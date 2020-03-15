package t5750.rest.jersey.client;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.junit.Test;

import t5750.rest.jersey.client.util.JerseyUtil;

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