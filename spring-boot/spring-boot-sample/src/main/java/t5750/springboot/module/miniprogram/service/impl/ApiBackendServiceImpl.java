package t5750.springboot.module.miniprogram.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import t5750.springboot.module.miniprogram.service.ApiBackendService;
import t5750.springboot.module.miniprogram.util.MiniUtil;
import t5750.springboot.util.DateUtils;
import t5750.springboot.util.HttpClientUtil;

@Service
public class ApiBackendServiceImpl implements ApiBackendService {
	/**
	 * 数据分析
	 */
	@Override
	public String datacube(HttpServletRequest request, String url) {
		String wxResult = "";
		HttpSession session = request.getSession();
		Object tokenObj = session.getAttribute(MiniUtil.ACCESS_TOKEN);
		if (null != tokenObj) {
			String access_token = tokenObj.toString();
			String begintime = request.getParameter("begin_date");
			String endtime = request.getParameter("end_date");
			if (url.contains("monthly")) {
				if (StringUtils.isEmpty(begintime)) {
					begintime = DateUtils.getReqDateyyyyMMdd(
							DateUtils.getPreviousMonthFirstDay());
				}
				if (StringUtils.isEmpty(endtime)) {
					endtime = DateUtils.getReqDateyyyyMMdd(
							DateUtils.getPreviousMonthLastDay());
				}
			} else {
				if (StringUtils.isEmpty(begintime)) {
					begintime = DateUtils.getYesterdaySimple();
				}
				if (StringUtils.isEmpty(endtime)) {
					endtime = begintime;
				}
			}
			Map<String, String> param = new HashMap<>(2);
			param.put("begin_date", begintime);
			param.put("end_date", endtime);
			Gson gson = new GsonBuilder().create();
			String json = gson.toJson(param);
			wxResult = HttpClientUtil.doPostJson(url + access_token, json);
			System.out.println(wxResult);
		}
		return wxResult;
	}
}