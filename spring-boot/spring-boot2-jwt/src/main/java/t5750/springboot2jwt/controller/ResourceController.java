package t5750.springboot2jwt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import t5750.springboot2jwt.util.CacheConstant;
import t5750.springboot2jwt.util.JwtUtil;
import t5750.springboot2jwt.util.RedisUtil;

@Controller
@RequestMapping("/resource")
public class ResourceController {
	@Autowired
	private RedisUtil redisUtil;

	@RequestMapping("/")
	public String home() {
		return "redirect:/protected-resource";
	}

	@RequestMapping("/protected-resource")
	public String protectedResource(HttpServletRequest request) {
		String username = JwtUtil.parseToken(request, JwtUtil.JWT_TOKEN,
				JwtUtil.SIGNING_KEY);
		if (!StringUtils.isEmpty(username)) {
			if (!redisUtil.sHasKey(
					CacheConstant.SYS_USERS_CACHE + ":" + username, username)) {
				username = "";
			}
		}
		return "protected-resource";
	}
}
