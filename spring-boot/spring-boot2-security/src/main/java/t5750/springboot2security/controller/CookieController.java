package t5750.springboot2security.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/cookie")
public class CookieController {
	@Autowired
	private FindByIndexNameSessionRepository<? extends Session> sessionRepository;

	@RequestMapping("/browser")
	public String browser(@RequestParam("browser") String browser,
			HttpServletRequest request, HttpSession session) {
		// 取出session中的browser
		Object sessionBrowser = session.getAttribute("browser");
		if (sessionBrowser == null) {
			System.out.println("不存在session，设置browser=" + browser);
			session.setAttribute("browser", browser);
		} else {
			System.out
					.println("存在session，browser=" + sessionBrowser.toString());
		}
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				System.out
						.println(cookie.getName() + " : " + cookie.getValue());
			}
		}
		return "index";
	}

	@RequestMapping("/findByUsername")
	@ResponseBody
	public Map findByUsername(@RequestParam String username) {
		Map<String, ? extends Session> usersSessions = sessionRepository
				.findByIndexNameAndIndexValue(
						FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME,
						username);
		return usersSessions;
	}

	@RequestMapping("/hello")
	public String hello(HttpSession session) {
		Object sessionName = session.getAttribute("name");
		if (null == sessionName) {
			session.setAttribute("name", "T5750");
		}
		return "index";
	}
}