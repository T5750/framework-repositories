package t5750.springboot2jwt.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import t5750.springboot2jwt.util.*;

@Controller
@RequestMapping("/auth")
public class LoginController {
	private static final Map<String, String> CREDENTIALS = new HashMap<>(2);
	@Autowired
	private RedisUtil redisUtil;

	public LoginController() {
		CREDENTIALS.put(Globals.T5750, Globals.T5750);
	}

	@RequestMapping("/")
	public String home() {
		return "redirect:/login";
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(HttpServletResponse httpServletResponse,
			String username, String password, String redirect, Model model) {
		if (username == null || !CREDENTIALS.containsKey(username)
				|| !CREDENTIALS.get(username).equals(password)) {
			model.addAttribute("error", "Invalid username or password!");
			return "login";
		}
		String token = JwtUtil.generateToken(JwtUtil.SIGNING_KEY, username);
		redisUtil.sSetAndTime(CacheConstant.SYS_USERS_CACHE + ":" + username,
				JwtUtil.EXPIRE_TIME, username);
		CookieUtil.create(httpServletResponse, JwtUtil.JWT_TOKEN, token, false,
				-1, CookieUtil.DOMAIN);
		return "redirect:" + redirect;
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String username = (String) httpServletRequest
				.getAttribute(Globals.USERNAME);
		if (!StringUtils.isEmpty(username)) {
			redisUtil.setRemove(CacheConstant.SYS_USERS_CACHE + ":" + username,
					username);
		}
		CookieUtil.clear(httpServletResponse, JwtUtil.JWT_TOKEN);
		return "redirect:/";
	}
}
