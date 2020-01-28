package t5750.netty.util;

import static t5750.netty.util.CookieEncoderUtil.stringBuilder;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import io.netty.handler.codec.http.Cookie;
import io.netty.handler.codec.http.DefaultCookie;

/**
 * Copied from netty-all 5.0.0.Alpha2.
 */
public class ServerCookieDecoder {
	/**
	 * Decodes the specified Set-Cookie HTTP header value into a {@link Cookie}.
	 *
	 * @return the decoded {@link Cookie}
	 */
	public static Set<Cookie> decode(String header) {
		if (header == null) {
			throw new NullPointerException("header");
		}
		final int headerLen = header.length();
		if (headerLen == 0) {
			return Collections.emptySet();
		}
		Set<Cookie> cookies = new TreeSet<Cookie>();
		int i = 0;
		boolean rfc2965Style = false;
		if (header.regionMatches(true, 0, "$Version", 0, 8)) {
			// RFC 2965 style cookie, move to after version value
			i = header.indexOf(';') + 1;
			rfc2965Style = true;
		}
		loop: for (;;) {
			// Skip spaces and separators.
			for (;;) {
				if (i == headerLen) {
					break loop;
				}
				char c = header.charAt(i);
				if (c == '\t' || c == '\n' || c == 0x0b || c == '\f'
						|| c == '\r' || c == ' ' || c == ',' || c == ';') {
					i++;
					continue;
				}
				break;
			}
			int newNameStart = i;
			int newNameEnd = i;
			String value;
			if (i == headerLen) {
				value = null;
			} else {
				keyValLoop: for (;;) {
					char curChar = header.charAt(i);
					if (curChar == ';') {
						// NAME; (no value till ';')
						newNameEnd = i;
						value = null;
						break keyValLoop;
					} else if (curChar == '=') {
						// NAME=VALUE
						newNameEnd = i;
						i++;
						if (i == headerLen) {
							// NAME= (empty value, i.e. nothing after '=')
							value = "";
							break keyValLoop;
						}
						int newValueStart = i;
						char c = header.charAt(i);
						if (c == '"') {
							// NAME="VALUE"
							StringBuilder newValueBuf = stringBuilder();
							final char q = c;
							boolean hadBackslash = false;
							i++;
							for (;;) {
								if (i == headerLen) {
									value = newValueBuf.toString();
									break keyValLoop;
								}
								if (hadBackslash) {
									hadBackslash = false;
									c = header.charAt(i++);
									if (c == '\\' || c == '"') {
										// Escape last backslash.
										newValueBuf.setCharAt(
												newValueBuf.length() - 1, c);
									} else {
										// Do not escape last backslash.
										newValueBuf.append(c);
									}
								} else {
									c = header.charAt(i++);
									if (c == q) {
										value = newValueBuf.toString();
										break keyValLoop;
									}
									newValueBuf.append(c);
									if (c == '\\') {
										hadBackslash = true;
									}
								}
							}
						} else {
							// NAME=VALUE;
							int semiPos = header.indexOf(';', i);
							if (semiPos > 0) {
								value = header
										.substring(newValueStart, semiPos);
								i = semiPos;
							} else {
								value = header.substring(newValueStart);
								i = headerLen;
							}
						}
						break keyValLoop;
					} else {
						i++;
					}
					if (i == headerLen) {
						// NAME (no value till the end of string)
						newNameEnd = headerLen;
						value = null;
						break;
					}
				}
			}
			if (!rfc2965Style
					|| (!header.regionMatches(newNameStart, "$Path", 0,
							"$Path".length())
							&& !header.regionMatches(newNameStart, "$Domain",
									0, "$Domain".length()) && !header
								.regionMatches(newNameStart, "$Port", 0,
										"$Port".length()))) {
				// skip obsolete RFC2965 fields
				String name = header.substring(newNameStart, newNameEnd);
				cookies.add(new DefaultCookie(name, value));
			}
		}
		return cookies;
	}

	private ServerCookieDecoder() {
		// unused
	}
}
