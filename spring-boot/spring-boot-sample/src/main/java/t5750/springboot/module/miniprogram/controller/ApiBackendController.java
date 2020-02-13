package t5750.springboot.module.miniprogram.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import t5750.springboot.module.miniprogram.service.ApiBackendService;
import t5750.springboot.module.miniprogram.util.MiniUtil;
import t5750.springboot.module.miniprogram.util.Result;
import t5750.springboot.util.DateUtils;
import t5750.springboot.util.HttpClientUtil;

@RestController
@RequestMapping("/mini")
public class ApiBackendController {
	@Value("${wx.mini.appid}")
	private String appid;
	@Value("${wx.mini.secret}")
	private String secret;
	@Autowired
	ApiBackendService apiBackendService;

	@PostMapping("/login")
	public String login(String code) {
		System.out.println("code: " + code);
		Map<String, String> param = new HashMap<>(4);
		param.put("appid", appid);
		param.put("secret", secret);
		param.put("js_code", code);
		param.put("grant_type", MiniUtil.AUTHORIZATION_CODE);
		String wxResult = HttpClientUtil.doGet(MiniUtil.MINI_LOGIN, param);
		if (!StringUtils.isEmpty(wxResult)) {
			System.out.println(wxResult);
			param.clear();
			Gson gson = new GsonBuilder().create();
			param = gson.fromJson(wxResult, Map.class);
			String openid = param.get(MiniUtil.OPENID);
			if (!StringUtils.isEmpty(openid)) {
				System.out.println("openid: " + openid);
			}
		}
		return Result.ok();
	}

	/**
	 * 接口调用凭据
	 */
	@GetMapping("/token")
	public String getAccessToken(HttpServletRequest request) {
		Map<String, String> param = new HashMap<>(4);
		param.put("grant_type", MiniUtil.CLIENT_CREDENTIAL);
		param.put("appid", appid);
		param.put("secret", secret);
		String wxResult = HttpClientUtil.doGet(MiniUtil.MINI_ACCESS_TOKEN,
				param);
		if (!StringUtils.isEmpty(wxResult)) {
			System.out.println(wxResult);
			param.clear();
			Gson gson = new GsonBuilder().create();
			param = gson.fromJson(wxResult, Map.class);
			String access_token = param.get(MiniUtil.ACCESS_TOKEN);
			if (!StringUtils.isEmpty(access_token)) {
				HttpSession session = request.getSession();
				session.setAttribute(MiniUtil.ACCESS_TOKEN, access_token);
			}
		}
		return wxResult;
	}

	/**
	 * 获取用户访问小程序月留存
	 */
	@GetMapping("/getmonthlyretain")
	public String getMonthlyRetain(HttpServletRequest request) {
		return apiBackendService.datacube(request, MiniUtil.GET_MONTHLY_RETAIN);
	}

	/**
	 * 获取用户访问小程序数据概况
	 */
	@GetMapping("/getdailysummary")
	public String getDailySummary(HttpServletRequest request) {
		return apiBackendService.datacube(request, MiniUtil.GET_DAILY_SUMMARY);
	}

	/**
	 * 获取用户访问小程序数据概况
	 */
	@GetMapping("/getmonthlyvisittrend")
	public String getMonthlyVisitTrend(HttpServletRequest request) {
		return apiBackendService.datacube(request,
				MiniUtil.GET_MONTHLY_VISIT_TREND);
	}

	/**
	 * 获取小程序新增或活跃用户的画像分布数据
	 */
	@GetMapping("/getuserportrait")
	public String getUserPortrait(HttpServletRequest request) {
		return apiBackendService.datacube(request, MiniUtil.GET_USER_PORTRAIT);
	}

	/**
	 * 获取用户小程序访问分布数据
	 */
	@GetMapping("/getvisitdistribution")
	public String getVisitDistribution(HttpServletRequest request) {
		return apiBackendService.datacube(request,
				MiniUtil.GET_VISIT_DISTRIBUTION);
	}

	/**
	 * 访问页面。目前只提供按 page_visit_pv 排序的 top200。
	 */
	@GetMapping("/getvisitpage")
	public String getVisitPage(HttpServletRequest request) {
		return apiBackendService.datacube(request, MiniUtil.GET_VISIT_PAGE);
	}

	/**
	 * 获取小程序二维码
	 */
	@GetMapping("/createqrcode")
	public String createQRCode(HttpServletRequest request) {
		String wxResult = "";
		HttpSession session = request.getSession();
		Object tokenObj = session.getAttribute(MiniUtil.ACCESS_TOKEN);
		if (null != tokenObj) {
			String access_token = tokenObj.toString();
			String path = request.getParameter("path");
			if (StringUtils.isEmpty(path)) {
				path = "page/index/index";
			}
			Map<String, String> param = new HashMap<>(2);
			param.put("path", path);
			Gson gson = new GsonBuilder().create();
			String json = gson.toJson(param);
			wxResult = HttpClientUtil
					.doPostJson(MiniUtil.CREATE_QR_CODE + access_token, json);
		}
		return wxResult;
	}

	/**
	 * 检查一段文本是否含有违法违规内容
	 */
	@GetMapping("/checkmsg")
	public String msgSecCheck(HttpServletRequest request) {
		String wxResult = "";
		HttpSession session = request.getSession();
		Object tokenObj = session.getAttribute(MiniUtil.ACCESS_TOKEN);
		if (null != tokenObj) {
			String access_token = tokenObj.toString();
			String content = request.getParameter("content");
			if (StringUtils.isEmpty(content)) {
				content = "特3456书yuuo莞6543李zxcz蒜7782法fgnv级完2347全dfji试3726测asad感3847知qwez到";
			}
			Map<String, String> param = new HashMap<>(2);
			param.put("content", content);
			Gson gson = new GsonBuilder().create();
			String json = gson.toJson(param);
			wxResult = HttpClientUtil
					.doPostJson(MiniUtil.MSG_SEC_CHECK + access_token, json);
			System.out.println(wxResult);
		}
		return wxResult;
	}

	/**
	 * 实时日志查询
	 */
	@GetMapping("/userlog")
	public String searchUserlog(HttpServletRequest request) {
		String wxResult = "";
		HttpSession session = request.getSession();
		Object tokenObj = session.getAttribute(MiniUtil.ACCESS_TOKEN);
		if (null != tokenObj) {
			String access_token = tokenObj.toString();
			String dateStr = request.getParameter("date");
			String begintime = request.getParameter("begintime");
			String endtime = request.getParameter("endtime");
			Date date;
			if (StringUtils.isEmpty(dateStr)) {
				date = new Date();
			} else {
				date = DateUtils.parseDate(dateStr,
						DateUtils.DATE_FORMAT_DATEONLY);
			}
			String shortDate = DateUtils.getReqDateyyyyMMdd(date);
			String short_date = DateUtils.getReqDate(date);
			if (StringUtils.isEmpty(begintime)) {
				begintime = String.valueOf(
						DateUtils.strToTimestamp(short_date + " 00:00:00")
								.getTime() / 1000);
			}
			if (StringUtils.isEmpty(endtime)) {
				endtime = String.valueOf(
						DateUtils.strToTimestamp(short_date + " 23:59:59")
								.getTime() / 1000);
			}
			Map<String, String> param = new HashMap<>(4);
			param.put("access_token", access_token);
			param.put("date", shortDate);
			param.put("begintime", endtime);
			param.put("endtime", endtime);
			wxResult = HttpClientUtil.doGet(MiniUtil.MINI_USER_LOG, param);
			System.out.println(wxResult);
		}
		return wxResult;
	}
}