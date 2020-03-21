package t5750.rest.jersey.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MissingFileException extends Exception
		implements ExceptionMapper<MissingFileException> {
	private static final long serialVersionUID = 1L;

	public MissingFileException() {
		super("No File found with given name !!");
	}

	public MissingFileException(String string) {
		super(string);
	}

	@Override
	public Response toResponse(MissingFileException exception) {
		return Response.status(404).entity(exception.getMessage())
				.type(MediaType.TEXT_PLAIN).build();
	}
}