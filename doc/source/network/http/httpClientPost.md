# Posting with HttpClient

## Basic POST
```
CloseableHttpClient client = HttpClients.createDefault();
HttpPost httpPost = new HttpPost(HTTPCLIENT_URL);
List<NameValuePair> params = new ArrayList<NameValuePair>();
params.add(new BasicNameValuePair("username", "John"));
params.add(new BasicNameValuePair("password", "pass"));
httpPost.setEntity(new UrlEncodedFormEntity(params));
```
Note how we used a `List` of `NameValuePair` to include parameters in the POST request.

## POST With Authorization
```
httpPost.setEntity(new StringEntity("test post"));
UsernamePasswordCredentials creds = new UsernamePasswordCredentials(
	"John", "pass");
httpPost.addHeader(
	new BasicScheme().authenticate(creds, httpPost, null));
```

## POST With JSON
```
String json = "{\"id\":1,\"name\":\"John\"}";
StringEntity entity = new StringEntity(json);
httpPost.setEntity(entity);
httpPost.setHeader("Accept", "application/json");
httpPost.setHeader("Content-type", "application/json");
```
Note how we're using the `StringEntity` to set the body of the request.

We're also setting the `ContentType` header to `application/json` to give the server the necessary information about the representation of the content we're sending.

## POST With the HttpClient Fluent API
```
HttpResponse response = Request.Post(HTTPCLIENT_URL)
	.bodyForm(Form.form().add("username", "John")
		.add("password", "pass").build())
	.execute().returnResponse();
```

## POST Multipart Request
```
MultipartEntityBuilder builder = MultipartEntityBuilder.create();
builder.addTextBody("username", "John");
builder.addTextBody("password", "pass");
builder.addBinaryBody("file", new File(FILE_PATHNAME),
	ContentType.APPLICATION_OCTET_STREAM, FILENAME);
HttpEntity multipart = builder.build();
httpPost.setEntity(multipart);
```

## Upload a File Using HttpClient
```
MultipartEntityBuilder builder = MultipartEntityBuilder.create();
builder.addBinaryBody("file", new File(FILE_PATHNAME),
	ContentType.APPLICATION_OCTET_STREAM, FILENAME);
HttpEntity multipart = builder.build();
httpPost.setEntity(multipart);
```

## Get File Upload Progress
```
MultipartEntityBuilder builder = MultipartEntityBuilder.create();
builder.addBinaryBody("file", new File(FILE_PATHNAME),
	ContentType.APPLICATION_OCTET_STREAM, FILENAME);
HttpEntity multipart = builder.build();
ProgressEntityWrapper.ProgressListener pListener = percentage -> assertFalse(
	Float.compare(percentage, 100) > 0);
httpPost.setEntity(new ProgressEntityWrapper(multipart, pListener));
```
Note that:
- When extending `FilterOutputStream` to `CountingOutputStream` – we are overriding the `write()` method to count the written (transferred) bytes
- When extending `HttpEntityWrapper` to `ProgressEntityWrapper` – we are overriding the `writeTo()` method to use our `CountingOutputStream`

## Results
- `HttpClientPostTest`

## References
- [Posting with HttpClient](https://www.baeldung.com/httpclient-post-http-request)