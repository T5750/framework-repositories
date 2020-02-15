# Custom HTTP Header with the HttpClient

## Set Header on Request – 4.3 and Above
```
HttpClient client = HttpClients.custom().build();
HttpUriRequest request = RequestBuilder.get()
  .setUri(SAMPLE_URL)
  .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
  .build();
client.execute(request);
```

## Set Header on Request – Before 4.3
```
HttpClient client = new DefaultHttpClient();
HttpGet request = new HttpGet(SAMPLE_URL);
request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
client.execute(request);
```

## Set Default Header on the Client
```
Header header = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
List<Header> headers = Lists.newArrayList(header);
HttpClient client = HttpClients.custom().setDefaultHeaders(headers).build();
HttpUriRequest request = RequestBuilder.get().setUri(SAMPLE_URL).build();
client.execute(request);
```

## Results
- `HttpClientCustomHttpHeaderTest`

## References
- [Custom HTTP Header with the HttpClient](https://www.baeldung.com/httpclient-custom-http-header)