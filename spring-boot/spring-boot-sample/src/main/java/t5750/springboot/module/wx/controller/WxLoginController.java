package t5750.springboot.module.wx.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import t5750.springboot.module.wx.model.WxSession;
import t5750.springboot.util.HttpClientUtil;
import t5750.springboot.util.JsonResult;
import t5750.springboot.util.JsonUtil;

@RestController
@RequestMapping("/wx")
public class WxLoginController {
	@Value("${wx.appid}")
	private String appid;
	@Value("${wx.secret}")
	private String secret;
	public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

	@PostMapping("/login")
	public JsonResult login(String code) {
		System.out.println("wxlogin - code: " + code);
		Map<String, String> param = new HashMap<>(4);
		param.put("appid", appid);
		param.put("secret", secret);
		param.put("js_code", code);
		param.put("grant_type", "authorization_code");
		String wxResult = HttpClientUtil.doGet(WX_LOGIN, param);
		System.out.println(wxResult);
		WxSession model = JsonUtil.jsonToPojo(wxResult, WxSession.class);
		// 存入session到redis
		// redis.set("user-redis-session:" + model.getOpenid(),
		// model.getSession_key(), 1000 * 60 * 30);
		return JsonResult.ok();
	}
}
