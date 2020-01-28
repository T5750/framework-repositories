package t5750.netty.util;

import io.netty.handler.codec.http.*;

/**
 * Copied from netty-all 5.0.0.Alpha2.
 */
public final class HttpHeaderUtil {
	/**
	 * Returns {@code true} if and only if the connection can remain open and
	 * thus 'kept alive'. This methods respects the value of the
	 * {@code "Connection"} header first and then the return value of
	 * {@link HttpVersion#isKeepAliveDefault()}.
	 */
	public static boolean isKeepAlive(HttpMessage message) {
		CharSequence connection = message.headers().get(
				HttpHeaderNames.CONNECTION);
		if (connection != null && HttpHeaderValues.CLOSE.equals(connection)) {
			return false;
		}
		if (message.protocolVersion().isKeepAliveDefault()) {
			return !HttpHeaderValues.CLOSE.equals(connection);
		} else {
			return HttpHeaderValues.KEEP_ALIVE.equals(connection);
		}
	}

	/**
	 * Sets the {@code "Content-Length"} header.
	 */
	public static void setContentLength(HttpMessage message, long length) {
		int lengthInt = 0;
		try {
			lengthInt = new Long(length).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		message.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, lengthInt);
	}

	/**
	 * Returns {@code true} if and only if the specified message contains the
	 * {@code "Expect: 100-continue"} header.
	 */
	public static boolean is100ContinueExpected(HttpMessage message) {
		// Expect: 100-continue is for requests only.
		if (!(message instanceof HttpRequest)) {
			return false;
		}
		// It works only on HTTP/1.1 or later.
		if (message.protocolVersion().compareTo(HttpVersion.HTTP_1_1) < 0) {
			return false;
		}
		// In most cases, there will be one or zero 'Expect' header.
		CharSequence value = message.headers().get(HttpHeaderNames.EXPECT);
		if (value == null) {
			return false;
		}
		if (HttpHeaderValues.CONTINUE.equals(value)) {
			return true;
		}
		// Multiple 'Expect' headers. Search through them.
		return message.headers().contains(HttpHeaderNames.EXPECT,
				HttpHeaderValues.CONTINUE, true);
	}

	/**
	 * Checks to see if the transfer encoding in a specified {@link HttpMessage}
	 * is chunked
	 *
	 * @param message
	 *            The message to check
	 * @return True if transfer encoding is chunked, otherwise false
	 */
	public static boolean isTransferEncodingChunked(HttpMessage message) {
		return message.headers().contains(HttpHeaderNames.TRANSFER_ENCODING,
				HttpHeaderValues.CHUNKED, true);
	}

	private HttpHeaderUtil() {
	}
}
