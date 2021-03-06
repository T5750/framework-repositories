package t5750.springboot.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import t5750.springboot.util.Globals;

@Component
public class SimpleFilter implements Filter {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterchain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		final String requestURI = req.getRequestURI();
		if (!requestURI.startsWith(Globals.FILTER_EXCLUDE[0])
				&& requestURI.indexOf(Globals.FILTER_EXCLUDE[1]) == -1) {
			logger.info("Remote Host: " + request.getRemoteHost()
					+ ", Remote Address: " + request.getRemoteAddr());
			final String requestURL = req.getRequestURL().toString();
			final String contextPath = req.getContextPath();
			final String servletPath = req.getServletPath();
			final String pathInfo = req.getPathInfo();
			logger.info("requestURL: " + requestURL + ", requestURI: "
					+ requestURI + ", contextPath: " + contextPath
					+ ", servletPath: " + servletPath + ", pathInfo: "
					+ pathInfo);
		}
		filterchain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterconfig) throws ServletException {
	}
}
