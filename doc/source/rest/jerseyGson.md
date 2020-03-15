# Jersey Gson

Jersey does not provide any inbuilt support for Gson, as it provide for MOXy and JSONP etc.

Gson can be used with Jersey as JSON serializer/deserializer by implementing interfaces `javax.ws.rs.ext.MessageBodyWriter` and `javax.ws.rs.ext.MessageBodyReader`, and register to configuration either using `@Provider` annotation OR using `ResourceConfig.register()` method.

## Create Gson Provider
```java
public class GsonMessageBodyHandler
		implements MessageBodyWriter<Object>, MessageBodyReader<Object> {
	private static final String UTF_8 = "UTF-8";
	private Gson gson;

	// Customize the gson behavior here
	private Gson getGson() {
		if (gson == null) {
			final GsonBuilder gsonBuilder = new GsonBuilder();
			gson = gsonBuilder.disableHtmlEscaping()
					.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
					.setPrettyPrinting().serializeNulls().create();
		}
		return gson;
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public Object readFrom(Class<Object> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders,
			InputStream entityStream) {
		InputStreamReader streamReader = null;
		try {
			streamReader = new InputStreamReader(entityStream, UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			Type jsonType;
			if (type.equals(genericType)) {
				jsonType = type;
			} else {
				jsonType = genericType;
			}
			return getGson().fromJson(streamReader, jsonType);
		} finally {
			try {
				streamReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public long getSize(Object object, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public void writeTo(Object object, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream)
			throws IOException, WebApplicationException {
		OutputStreamWriter writer = new OutputStreamWriter(entityStream, UTF_8);
		try {
			Type jsonType;
			if (type.equals(genericType)) {
				jsonType = type;
			} else {
				jsonType = genericType;
			}
			getGson().toJson(object, jsonType, writer);
		} finally {
			writer.close();
		}
	}
}
```

## Register Gson Provider
```java
public class JerseyApplication extends ResourceConfig {
	public JerseyApplication() {
		packages("t5750.rest.jersey.resources", "t5750.rest.jersey.service");
		register(GsonMessageBodyHandler.class);
		register(AuthenticationFilter.class);
	}
}
```

## Extending Gson Functionality
You can extend/customize Gson behavior anytime inside `GsonMessageBodyHandler.getGson()` method.
```
private Gson getGson() {
    if (gson == null) {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.disableHtmlEscaping()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .serializeNulls()
                .create();
    }
    return gson;
}
```

## References
- [Jersey + Google Gson Example](https://howtodoinjava.com/jersey/jax-rs-gson-example/)