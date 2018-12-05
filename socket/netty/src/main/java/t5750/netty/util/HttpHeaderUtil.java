package t5750.netty.util;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpVersion;

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
	public static void setContentLength(HttpMessage message, int length) {
		message.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, length);
	}

	private HttpHeaderUtil() {
	}
}
