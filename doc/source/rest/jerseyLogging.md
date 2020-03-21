# Jersey Custom Logging

## Create CustomLoggingFilter
```java
public class CustomLoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {
	@Override
	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("User: ").append(
				requestContext.getSecurityContext().getUserPrincipal() == null
						? "unknown"
						: requestContext.getSecurityContext()
								.getUserPrincipal());
		sb.append(" - Path: ").append(requestContext.getUriInfo().getPath());
		sb.append(" - Header: ").append(requestContext.getHeaders());
		sb.append(" - Entity: ").append(getEntityBody(requestContext));
		System.out.println("HTTP REQUEST : " + sb.toString());
	}

	private String getEntityBody(ContainerRequestContext requestContext) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream in = requestContext.getEntityStream();
		final StringBuilder b = new StringBuilder();
		try {
			ReaderWriter.writeTo(in, out);
			byte[] requestEntity = out.toByteArray();
			if (requestEntity.length == 0) {
				b.append("").append("\n");
			} else {
				b.append(new String(requestEntity)).append("\n");
			}
			requestContext
					.setEntityStream(new ByteArrayInputStream(requestEntity));
		} catch (IOException ex) {
			// Handle logging error
		}
		return b.toString();
	}

	@Override
	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("Header: ").append(responseContext.getHeaders());
		sb.append(" - Entity: ").append(responseContext.getEntity());
		System.out.println("HTTP RESPONSE : " + sb.toString());
	}
}
```

## Register CustomLoggingFilter
```java
public class JerseyApplication extends ResourceConfig {
	public JerseyApplication() {
		packages("t5750.rest.jersey.resources", "t5750.rest.jersey.service");
		register(GsonMessageBodyHandler.class);
		register(AuthenticationFilter.class);
		register(CustomLoggingFilter.class);
	}
}
```

## References
- [Jersey Logging Request and Response Entities using Filter](https://howtodoinjava.com/jersey/jersey-custom-logging-request-and-response-entities-using-filter/)