package t5750.springboot2jwt.config;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;
import t5750.springboot2jwt.util.Globals;
import t5750.springboot2jwt.util.JwtUtil;

@WebFilter(urlPatterns = { "/resource/*", "/auth/logout" })
public class JwtFilter extends OncePerRequestFilter {
	@Value("${services.auth}")
	private String servicesAuth;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
			throws ServletException, IOException {
		String username = JwtUtil.parseToken(httpServletRequest,
				JwtUtil.JWT_TOKEN, JwtUtil.SIGNING_KEY);
		if (username == null) {
			httpServletResponse.sendRedirect(servicesAuth + "?redirect="
					+ httpServletRequest.getRequestURL());
		} else {
			httpServletRequest.setAttribute(Globals.USERNAME, username);
			filterChain.doFilter(httpServletRequest, httpServletResponse);
		}
	}
}
