package t5750.pay.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <b>功能说明:支付控制类 </b>
 */
@Controller
@RequestMapping(value = "/microPayNotify")
public class MicroPayNotifyController extends BaseController {
	private static final Logger LOG = LoggerFactory
			.getLogger(MicroPayNotifyController.class);

	@RequestMapping("/notify")
	public String scanPay(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		Map requestMap = getParamMap_NullStr(
				httpServletRequest.getParameterMap());
		LOG.info("银行返回结果：{}", requestMap);
		try {
			httpServletResponse.getWriter().print("success");
			// httpServletResponse.getWriter().print("error");
		} catch (IOException e) {
			LOG.error("回写失败：", e);
		}
		return null;
	}
}
