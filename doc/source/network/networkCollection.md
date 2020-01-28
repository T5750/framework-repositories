# Network Collection

## Simple Tutorial

### Use HttpURLConnection to download file from an HTTP URL
- Create a URL object for a given URL. The URL can be either:
    - A direct link which contains the real file name at the end, for example:*http://jdbc.postgresql.org/download/postgresql-9.2-1002.jdbc4.jar*
    - An indirect link which does not contain the real file name, for example:*http://myserver.com/download?id=1234*
- Open connection on the URL object – which would return an `HttpURLConnection` object if the URL is an HTTP URL.
- Open the input stream of the opened connection.
- Create an output stream to save file to disk.
- Repeatedly read array of bytes from the input stream and write them to the output stream, until the input stream is empty.
- Close the input stream, the output stream and the connection.

### An HTTP utility class to send GET/POST request
- `sendGetRequest(requestURL)`: accepts only the URL string.
- `sendPostRequest(requestURL, params)`: accepts a URL string and a `HashMap` containing POST parameters.
- `readSingleLineRespone()`: returns a String.
- `readMultipleLinesRespone()`: returns an array of Strings.
- `disconnect()`

### Upload files by sending multipart request programmatically
- `MultipartUtility(String requestURL, String charset)`: creates a new instance of this class for a given request URL and charset.
- `void addFormField(String name, String value)`: adds a regular text field to the request.
- `void addHeaderField(String name, String value)`: adds an HTTP header field to the request.
- `void addFilePart(String fieldName, File uploadFile)`: attach a file to be uploaded to the request.
- `List<String> finish()`: this method must be invoked lastly to complete the request and receive response from server as a list of String.

### Java InetAddress Examples
- `getByName(String host)`: creates an InetAddress object based on the provided hostname.
- `getByAddress(byte[] addr)`: returns an InetAddress object from a byte array of the raw IP address.
- `getAllByName(String host)`: returns an array of InetAddress objects from the specified hostname, as a hostname can be associated with several IP addresses.
- `getLocalHost()`: returns the address of the localhost.
- `getHostAddress()`: returns the IP address in text.
- `getHostname()`: gets the hostname.

### Java Socket Client Examples (TCP/IP)
1. The client initiates connection to a server specified by hostname/IP address and port number.
2. Send data to the server using an `OutputStream`.
3. Read data from the server using an `InputStream`.
4. Close the connection.

### Java Socket Server Examples (TCP/IP)
1. Create a server socket and bind it to a specific port number
2. Listen for a connection from the client and accept it. This results in a client socket is created for the connection.
3. Read data from the client via an `InputStream` obtained from the client socket.
4. Send data to the client via the client socket’s `OutputStream`.
5. Close the connection with the client.

### Java UDP Client Server Program Example
- `send(DatagramPacket p)`: sends a datagram packet.
- `receive(DatagramPacket p)`: receives a datagram packet.
- `setSoTimeout(int timeout)`: sets timeout in milliseconds, limiting the waiting time when receiving data. If the timeout expires, a `SocketTimeoutException` is raised.
- `close()`: closes the socket.

## Notes

![TCP/IP 和 ISO/OSI](https://s1.wailian.download/2017/11/20/TCPIPISOOSI.png)

![TCP/IP分层模型](https://s1.wailian.download/2017/11/20/TCPIP.png)

![数据的封装与分用](https://s1.wailian.download/2017/11/20/3278cb981ff6146a.png)

![数据的封装与分用2](https://s1.wailian.download/2017/11/20/2.png)

![五类不同的互联网地址](https://s1.wailian.download/2017/11/20/8f52799a84fe4f15.png)

![IP地址](https://s1.wailian.download/2017/11/20/IP.png)

## References
- [Use HttpURLConnection to download file from an HTTP URL](http://www.codejava.net/java-se/networking/use-httpurlconnection-to-download-file-from-an-http-url)
- [An HTTP utility class to send GET/POST request](http://www.codejava.net/java-se/networking/an-http-utility-class-to-send-getpost-request)
- [Upload files by sending multipart request programmatically](http://www.codejava.net/java-se/networking/upload-files-by-sending-multipart-request-programmatically)
- [Java InetAddress Examples](http://www.codejava.net/java-se/networking/java-inetaddress-examples)
- [Java Socket Client Examples (TCP/IP)](http://www.codejava.net/java-se/networking/java-socket-client-examples-tcp-ip)
- [Java Socket Server Examples (TCP/IP)](http://www.codejava.net/java-se/networking/java-socket-server-examples-tcp-ip)
- [Java UDP Client Server Program Example](http://www.codejava.net/java-se/networking/java-udp-client-server-program-example)
- [JAVA的网络编程【转】](http://www.cnblogs.com/springcsc/archive/2009/12/03/1616413.html)
- [TCP/IP协议簇分层详解](http://blog.csdn.net/hankscpp/article/details/8611229)