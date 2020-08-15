package t5750.springboot2jwt.util;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
	public static final String JWT_TOKEN = "JWT_TOKEN";
	public static final String SIGNING_KEY = "SIGNING_KEY";
	public static final long EXPIRE_TIME = 8 * 60 * 60;

	public static String generateToken(String signingKey, String subject) {
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		JwtBuilder builder = Jwts.builder().setSubject(subject).setIssuedAt(now)
				.signWith(SignatureAlgorithm.HS256, signingKey);
		String token = builder.compact();
		return token;
	}

	public static String getSubject(HttpServletRequest httpServletRequest,
			String jwtTokenCookieName, String signingKey) {
		String token = CookieUtil.getValue(httpServletRequest,
				jwtTokenCookieName);
		if (token == null) {
			return null;
		}
		return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token)
				.getBody().getSubject();
	}

	public static String parseToken(HttpServletRequest httpServletRequest,
			String jwtTokenCookieName, String signingKey) {
		String token = CookieUtil.getValue(httpServletRequest,
				jwtTokenCookieName);
		if (token == null) {
			return null;
		}
		String subject = Jwts.parser().setSigningKey(signingKey)
				.parseClaimsJws(token).getBody().getSubject();
		return subject;
	}
}
