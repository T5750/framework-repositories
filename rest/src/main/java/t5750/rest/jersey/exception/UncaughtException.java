package t5750.rest.jersey.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UncaughtException extends Throwable
		implements ExceptionMapper<Throwable> {
	private static final long serialVersionUID = 1L;

	@Override
	public Response toResponse(Throwable exception) {
		return Response.status(500)
				.entity("Something bad happened. Please try again !!")
				.type(MediaType.TEXT_PLAIN).build();
	}
}