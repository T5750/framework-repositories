package t5750.netty.util;

import io.netty.handler.codec.http.HttpConstants;
import io.netty.util.internal.InternalThreadLocalMap;

/**
 * Created by bm on 2018/12/6.
 */
final class CookieEncoderUtil {
	static StringBuilder stringBuilder() {
		return InternalThreadLocalMap.get().stringBuilder();
	}

	/**
	 * @param buf
	 *            a buffer where some cookies were maybe encoded
	 * @return the buffer String without the trailing separator, or null if no
	 *         cookie was appended.
	 */
	static String stripTrailingSeparatorOrNull(StringBuilder buf) {
		return buf.length() == 0 ? null : stripTrailingSeparator(buf);
	}

	static String stripTrailingSeparator(StringBuilder buf) {
		if (buf.length() > 0) {
			buf.setLength(buf.length() - 2);
		}
		return buf.toString();
	}

	static void addUnquoted(StringBuilder sb, String name, String val) {
		sb.append(name);
		sb.append((char) HttpConstants.EQUALS);
		sb.append(val);
		sb.append((char) HttpConstants.SEMICOLON);
		sb.append((char) HttpConstants.SP);
	}

	static void add(StringBuilder sb, String name, long val) {
		sb.append(name);
		sb.append((char) HttpConstants.EQUALS);
		sb.append(val);
		sb.append((char) HttpConstants.SEMICOLON);
		sb.append((char) HttpConstants.SP);
	}

	private CookieEncoderUtil() {
		// Unused
	}
}
