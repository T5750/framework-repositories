package t5750.springboot.filter;

import java.io.IOException;

import javax.servlet.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SimpleFilter implements Filter {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterchain) throws IOException, ServletException {
		logger.info("Remote Host:" + request.getRemoteHost());
		logger.info("Remote Address:" + request.getRemoteAddr());
		filterchain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterconfig) throws ServletException {
	}
}
