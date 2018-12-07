package t5750.netty.upload;

import static io.netty.buffer.Unpooled.copiedBuffer;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import t5750.netty.util.HttpHeaderUtil;
import t5750.netty.util.ServerCookieDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.EndOfDataDecoderException;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.ErrorDataDecoderException;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import io.netty.util.CharsetUtil;

public class HttpUploadServerHandler extends
		SimpleChannelInboundHandler<HttpObject> {
	private static final Logger logger = Logger
			.getLogger(HttpUploadServerHandler.class.getName());
	private HttpRequest request;
	private boolean readingChunks;
	private final StringBuilder responseContent = new StringBuilder();
	private static final HttpDataFactory factory = new DefaultHttpDataFactory(
			DefaultHttpDataFactory.MINSIZE); // Disk if size exceed
	private HttpPostRequestDecoder decoder;
	static {
		DiskFileUpload.deleteOnExitTemporaryFile = true; // should delete file
		// on exit (in normal exit)
		// system temp directory
		DiskFileUpload.baseDirectory = "D:" + File.separatorChar + "code";
		DiskAttribute.deleteOnExitTemporaryFile = true; // should delete file on
		// exit (in normal exit)
		// system temp directory
		DiskAttribute.baseDirectory = "D:" + File.separatorChar + "code";
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		if (decoder != null) {
			decoder.cleanFiles();
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if (msg instanceof HttpRequest) {
			HttpRequest request = this.request = (HttpRequest) msg;
			URI uri = new URI(request.uri());
			if (!uri.getPath().startsWith("/form")) {
				// Write Menu
				writeMenu(ctx);
				return;
			}
			responseContent.setLength(0);
			responseContent.append("WELCOME TO THE WILD WILD WEB SERVER\r\n");
			responseContent.append("===================================\r\n");
			responseContent.append("VERSION: "
					+ request.protocolVersion().text() + "\r\n");
			responseContent
					.append("REQUEST_URI: " + request.uri() + "\r\n\r\n");
			responseContent.append("\r\n\r\n");
			// new getMethod
			for (Entry<String, String> entry : request.headers()) {
				responseContent.append("HEADER: " + entry.getKey() + '='
						+ entry.getValue() + "\r\n");
			}
			responseContent.append("\r\n\r\n");
			// new getMethod
			Set<Cookie> cookies;
			// String value = request.headers().getAndConvert(
			// HttpHeaderNames.COOKIE);
			String value = request.headers().get(HttpHeaderNames.COOKIE);
			if (value == null) {
				cookies = Collections.emptySet();
			} else {
				cookies = ServerCookieDecoder.decode(value);
			}
			for (Cookie cookie : cookies) {
				responseContent.append("COOKIE: " + cookie + "\r\n");
			}
			responseContent.append("\r\n\r\n");
			QueryStringDecoder decoderQuery = new QueryStringDecoder(
					request.uri());
			Map<String, List<String>> uriAttributes = decoderQuery.parameters();
			for (Entry<String, List<String>> attr : uriAttributes.entrySet()) {
				for (String attrVal : attr.getValue()) {
					responseContent.append("URI: " + attr.getKey() + '='
							+ attrVal + "\r\n");
				}
			}
			responseContent.append("\r\n\r\n");
			// if GET Method: should not try to create a HttpPostRequestDecoder
			if (request.method().equals(HttpMethod.GET)) {
				// GET Method: should not try to create a HttpPostRequestDecoder
				// So stop here
				responseContent.append("\r\n\r\nEND OF GET CONTENT\r\n");
				// Not now: LastHttpContent will be sent
				// writeResponse(ctx.channel());
				return;
			}
			try {
				decoder = new HttpPostRequestDecoder(factory, request);
			} catch (ErrorDataDecoderException e1) {
				e1.printStackTrace();
				responseContent.append(e1.getMessage());
				writeResponse(ctx.channel());
				ctx.channel().close();
				return;
			}
			readingChunks = HttpHeaderUtil.isTransferEncodingChunked(request);
			responseContent.append("Is Chunked: " + readingChunks + "\r\n");
			responseContent.append("IsMultipart: " + decoder.isMultipart()
					+ "\r\n");
			if (readingChunks) {
				// Chunk version
				responseContent.append("Chunks: ");
				readingChunks = true;
			}
		}
		// check if the decoder was constructed before
		// if not it handles the form get
		if (decoder != null) {
			if (msg instanceof HttpContent) {
				// New chunk is received
				HttpContent chunk = (HttpContent) msg;
				try {
					decoder.offer(chunk);
				} catch (ErrorDataDecoderException e1) {
					e1.printStackTrace();
					responseContent.append(e1.getMessage());
					writeResponse(ctx.channel());
					ctx.channel().close();
					return;
				}
				responseContent.append('o');
				// example of reading chunk by chunk (minimize memory usage due
				// to
				// Factory)
				readHttpDataChunkByChunk();
				// example of reading only if at the end
				if (chunk instanceof LastHttpContent) {
					writeResponse(ctx.channel());
					readingChunks = false;
					// https://github.com/netty/netty/pull/7696
					// reset();
				}
			}
		} else {
			writeResponse(ctx.channel());
		}
	}

	private void reset() {
		request = null;
		// destroy the decoder to release all resources
		decoder.destroy();
		decoder = null;
	}

	/**
	 * Example of reading request by chunk and getting values from chunk to
	 * chunk
	 */
	private void readHttpDataChunkByChunk() throws Exception {
		try {
			while (decoder.hasNext()) {
				InterfaceHttpData data = decoder.next();
				if (data != null) {
					try {
						// new value
						writeHttpData(data);
					} finally {
						data.release();
					}
				}
			}
		} catch (EndOfDataDecoderException e1) {
			// end
			responseContent
					.append("\r\n\r\nEND OF CONTENT CHUNK BY CHUNK\r\n\r\n");
		}
	}

	private void writeHttpData(InterfaceHttpData data) throws Exception {
		if (data.getHttpDataType() == HttpDataType.Attribute) {
			Attribute attribute = (Attribute) data;
			String value;
			try {
				value = attribute.getValue();
			} catch (IOException e1) {
				// Error while reading data from File, only print name and error
				e1.printStackTrace();
				responseContent.append("\r\nBODY Attribute: "
						+ attribute.getHttpDataType().name() + ": "
						+ attribute.getName() + " Error while reading value: "
						+ e1.getMessage() + "\r\n");
				return;
			}
			if (value.length() > 100) {
				responseContent.append("\r\nBODY Attribute: "
						+ attribute.getHttpDataType().name() + ": "
						+ attribute.getName() + " data too long\r\n");
			} else {
				responseContent.append("\r\nBODY Attribute: "
						+ attribute.getHttpDataType().name() + ": " + attribute
						+ "\r\n");
			}
		} else {
			responseContent.append("\r\n -----------start-------------"
					+ "\r\n");
			responseContent.append("\r\nBODY FileUpload: "
					+ data.getHttpDataType().name() + ": " + data + "\r\n");
			responseContent.append("\r\n ------------end------------" + "\r\n");
			if (data.getHttpDataType() == HttpDataType.FileUpload) {
				FileUpload fileUpload = (FileUpload) data;
				if (fileUpload.isCompleted()) {
					System.out.println("file name : "
							+ fileUpload.getFilename());
					System.out.println("file length: " + fileUpload.length());
					System.out.println("file maxSize : "
							+ fileUpload.getMaxSize());
					System.out.println("file path :"
							+ fileUpload.getFile().getPath());
					System.out.println("file absolutepath :"
							+ fileUpload.getFile().getAbsolutePath());
					System.out.println("parent path :"
							+ fileUpload.getFile().getParentFile());
					if (fileUpload.length() < 1024 * 1024 * 10) {
						responseContent.append("\tContent of file\r\n");
						try {
							// responseContent.append(fileUpload.getString(fileUpload.getCharset()));
						} catch (Exception e1) {
							// do nothing for the example
							e1.printStackTrace();
						}
						responseContent.append("\r\n");
					} else {
						responseContent
								.append("\tFile too long to be printed out:"
										+ fileUpload.length() + "\r\n");
					}
					// fileUpload.isInMemory();// tells if the file is in Memory
					// or on File
					fileUpload
							.renameTo(new File(fileUpload.getFile().getPath()));
					// enable to move into another
					// File dest
					// decoder.removeFileUploadFromClean(fileUpload); //remove
					// the File of to delete file
				} else {
					responseContent
							.append("\tFile to be continued but should not!\r\n");
				}
			}
		}
	}

	private void writeResponse(Channel channel) {
		// Convert the response content to a ChannelBuffer.
		ByteBuf buf = copiedBuffer(responseContent.toString(),
				CharsetUtil.UTF_8);
		responseContent.setLength(0);
		// Decide whether to close the connection or not.
		boolean close = request.headers().contains(HttpHeaderNames.CONNECTION,
				HttpHeaderValues.CLOSE, true)
				|| request.protocolVersion().equals(HttpVersion.HTTP_1_0)
				&& !request.headers().contains(HttpHeaderNames.CONNECTION,
						HttpHeaderValues.KEEP_ALIVE, true);
		// Build the response object.
		FullHttpResponse response = new DefaultFullHttpResponse(
				HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
		response.headers().set(HttpHeaderNames.CONTENT_TYPE,
				"text/plain; charset=UTF-8");
		if (!close) {
			// There's no need to add 'Content-Length' header
			// if this is the last response.
			response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH,
					buf.readableBytes());
		}
		Set<Cookie> cookies;
		// String value =
		// request.headers().getAndConvert(HttpHeaderNames.COOKIE);
		String value = request.headers().get(HttpHeaderNames.COOKIE);
		if (value == null) {
			cookies = Collections.emptySet();
		} else {
			cookies = ServerCookieDecoder.decode(value);
		}
		if (!cookies.isEmpty()) {
			// Reset the cookies if necessary.
			for (Cookie cookie : cookies) {
				response.headers().add(HttpHeaderNames.SET_COOKIE,
						ServerCookieEncoder.encode(cookie));
			}
		}
		// Write the response.
		ChannelFuture future = channel.writeAndFlush(response);
		// Close the connection after the write operation is done if necessary.
		if (close) {
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}

	private void writeMenu(ChannelHandlerContext ctx) {
		// print several HTML forms
		// Convert the response content to a ChannelBuffer.
		responseContent.setLength(0);
		// create Pseudo Menu
		responseContent.append("<html>");
		responseContent.append("<head>");
		responseContent.append("<title>Netty Test Form</title>\r\n");
		responseContent.append("</head>\r\n");
		responseContent
				.append("<body bgcolor=white><style>td{font-size: 12pt;}</style>");
		responseContent.append("<table border=\"0\">");
		responseContent.append("<tr>");
		responseContent.append("<td>");
		responseContent.append("<h1>Netty Test Form</h1>");
		responseContent.append("Choose one FORM");
		responseContent.append("</td>");
		responseContent.append("</tr>");
		responseContent.append("</table>\r\n");
		// GET
		responseContent
				.append("<CENTER>GET FORM<HR WIDTH=\"75%\" NOSHADE color=\"blue\"></CENTER>");
		responseContent.append("<FORM ACTION=\"/formget\" METHOD=\"GET\">");
		responseContent
				.append("<input type=hidden name=getform value=\"GET\">");
		responseContent.append("<table border=\"0\">");
		responseContent
				.append("<tr><td>Fill with value: <br> <input type=text name=\"info\" size=10></td></tr>");
		responseContent
				.append("<tr><td>Fill with value: <br> <input type=text name=\"secondinfo\" size=20>");
		responseContent
				.append("<tr><td>Fill with value: <br> <textarea name=\"thirdinfo\" cols=40 rows=10></textarea>");
		responseContent.append("</td></tr>");
		responseContent
				.append("<tr><td><INPUT TYPE=\"submit\" NAME=\"Send\" VALUE=\"Send\"></INPUT></td>");
		responseContent
				.append("<td><INPUT TYPE=\"reset\" NAME=\"Clear\" VALUE=\"Clear\" ></INPUT></td></tr>");
		responseContent.append("</table></FORM>\r\n");
		responseContent
				.append("<CENTER><HR WIDTH=\"75%\" NOSHADE color=\"blue\"></CENTER>");
		// POST
		responseContent
				.append("<CENTER>POST FORM<HR WIDTH=\"75%\" NOSHADE color=\"blue\"></CENTER>");
		responseContent.append("<FORM ACTION=\"/formpost\" METHOD=\"POST\">");
		responseContent
				.append("<input type=hidden name=getform value=\"POST\">");
		responseContent.append("<table border=\"0\">");
		responseContent
				.append("<tr><td>Fill with value: <br> <input type=text name=\"info\" size=10></td></tr>");
		responseContent
				.append("<tr><td>Fill with value: <br> <input type=text name=\"secondinfo\" size=20>");
		responseContent
				.append("<tr><td>Fill with value: <br> <textarea name=\"thirdinfo\" cols=40 rows=10></textarea>");
		responseContent
				.append("<tr><td>Fill with file (only file name will be transmitted): <br> "
						+ "<input type=file name=\"myfile\">");
		responseContent.append("</td></tr>");
		responseContent
				.append("<tr><td><INPUT TYPE=\"submit\" NAME=\"Send\" VALUE=\"Send\"></INPUT></td>");
		responseContent
				.append("<td><INPUT TYPE=\"reset\" NAME=\"Clear\" VALUE=\"Clear\" ></INPUT></td></tr>");
		responseContent.append("</table></FORM>\r\n");
		responseContent
				.append("<CENTER><HR WIDTH=\"75%\" NOSHADE color=\"blue\"></CENTER>");
		// POST with enctype="multipart/form-data"
		responseContent
				.append("<CENTER>POST MULTIPART FORM<HR WIDTH=\"75%\" NOSHADE color=\"blue\"></CENTER>");
		responseContent
				.append("<FORM ACTION=\"/formpostmultipart\" ENCTYPE=\"multipart/form-data\" METHOD=\"POST\">");
		responseContent
				.append("<input type=hidden name=getform value=\"POST\">");
		responseContent.append("<table border=\"0\">");
		responseContent
				.append("<tr><td>Fill with value: <br> <input type=text name=\"info\" size=10></td></tr>");
		responseContent
				.append("<tr><td>Fill with value: <br> <input type=text name=\"secondinfo\" size=20>");
		responseContent
				.append("<tr><td>Fill with value: <br> <textarea name=\"thirdinfo\" cols=40 rows=10></textarea>");
		responseContent
				.append("<tr><td>Fill with file: <br> <input type=file name=\"myfile\">");
		responseContent.append("</td></tr>");
		responseContent
				.append("<tr><td><INPUT TYPE=\"submit\" NAME=\"Send\" VALUE=\"Send\"></INPUT></td>");
		responseContent
				.append("<td><INPUT TYPE=\"reset\" NAME=\"Clear\" VALUE=\"Clear\" ></INPUT></td></tr>");
		responseContent.append("</table></FORM>\r\n");
		responseContent
				.append("<CENTER><HR WIDTH=\"75%\" NOSHADE color=\"blue\"></CENTER>");
		responseContent.append("</body>");
		responseContent.append("</html>");
		ByteBuf buf = copiedBuffer(responseContent.toString(),
				CharsetUtil.UTF_8);
		// Build the response object.
		FullHttpResponse response = new DefaultFullHttpResponse(
				HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
		response.headers().set(HttpHeaderNames.CONTENT_TYPE,
				"text/html; charset=UTF-8");
		response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH,
				buf.readableBytes());
		// Write the response.
		ctx.channel().writeAndFlush(response);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		logger.log(Level.WARNING, responseContent.toString(), cause);
		ctx.channel().close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg)
			throws Exception {
	}
}