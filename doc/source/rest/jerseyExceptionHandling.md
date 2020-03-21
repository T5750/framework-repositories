# Jersey Exception Handling

## Jersey ExceptionMapper – Create custom exceptions
To handle custom exception in JAX-RS based web services, you should create an exception class and then implement the [ExceptionMapper](https://docs.oracle.com/javaee/7/api/javax/ws/rs/ext/ExceptionMapper.html) interface.
```java
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
```

## How to throw exception from REST API
```java
public class DownloadService {
    @GET
    @Path("/{fileName}")
    public Response downloadFile(final @PathParam("fileName") String fileName)
            throws MissingFileException {
        final String fullFilePath = Globals.UPLOAD_PATH + fileName;
        File file = new File(fullFilePath);
        if (file.exists() == false) {
            throw new MissingFileException(
                    fileName + " does not existing on this server !!");
        }
        StreamingOutput fileStream = new StreamingOutput() {
            @Override
            public void write(java.io.OutputStream output) throws IOException {
                try {
                    java.nio.file.Path path = Paths.get(fullFilePath);
                    byte[] data = Files.readAllBytes(path);
                    output.write(data);
                    output.flush();
                } catch (IOException e) {
                    throw new IOException("Error while reading file :: '"
                            + fileName + "' !!");
                }
            }
        };
        return Response.ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
                .header("content-disposition",
                        "attachment; filename = '" + fileName)
                .build();
    }
}
```

## Uncaught Exception Handling
If you want to handle all uncaught exception before going to user screen, you will have to map the `Throwable` itself.
```java
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
```

## References
- [Jersey exception handling – Jersey ExceptionMapper Example](https://howtodoinjava.com/jersey/jaxrs-jersey-exceptionmapper/)