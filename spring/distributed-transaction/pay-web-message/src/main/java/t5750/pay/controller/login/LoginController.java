package t5750.pay.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import t5750.pay.common.core.dwz.DWZ;
import t5750.pay.common.core.dwz.DwzAjax;
import t5750.pay.common.core.utils.StringUtil;

/**
 * @类功能说明： 系统 登陆管理.
 * @版本：V1.0
 */
@Controller
public class LoginController {
	private static final Log LOG = LogFactory.getLog(LoginController.class);

	/**
	 * 函数功能说明 ： 进入后台登陆页面.
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "system/login";
	}

	/**
	 * 函数功能说明 ： 登陆后台管理系统. 修改者名字： 修改日期： 修改内容：
	 * 
	 * @参数： @param request
	 * @参数： @param model
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/login/index", method = RequestMethod.POST)
	public String index(String empNo, String empPwd, String code, Model model) {
		// 判断是否输入帐号.
		if (StringUtil.isEmpty(empNo)) {
			model.addAttribute("message", "请输入帐号！");
			return "system/login";
		}
		// 判断是否输入密码.
		if (StringUtil.isEmpty(empPwd)) {
			model.addAttribute("message", "请输入密码！");
			return "system/login";
		}
		// 判断是否输入验证码.
		// String randomCode = (String) request.getSession().getAttribute(
		// "randomCode");
		// if (StringUtil.isEmpty(code)) {
		// model.addAttribute("message", "请输入验证码！");
		// return "system/login";
		// } else if (!code.equals(randomCode)) {
		// model.addAttribute("message", "验证码不正确！");
		// return "system/login";
		// }
		model.addAttribute("userNo", "888888");
		model.addAttribute("userName", "Along");
		return "system/index";
	}

	/**
	 * 函数功能说明 ：进入退出系统确认页面. 修改者名字： 修改日期： 修改内容：
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/admin/confirm", method = RequestMethod.GET)
	public String confirm() {
		return "system/confirm";
	}

	/**
	 * 函数功能说明 ： 退出系统. 修改者名字： 修改日期： 修改内容：
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/admin/logout", method = RequestMethod.POST)
	public String logout(HttpServletRequest request, Model model) {
		// 不是以form的形式提交的数据,要new一个DwzAjax对象
		DwzAjax dwz = new DwzAjax();
		try {
			HttpSession session = request.getSession();
			session.removeAttribute("employee");
			LOG.info("***clean session success!***");
		} catch (Exception e) {
			LOG.error(e);
			dwz.setStatusCode(DWZ.ERROR);
			dwz.setMessage("退出系统时系统出现异常，请通知系统管理员！");
			model.addAttribute("dwz", dwz);
			return "admin.common.ajaxDone";
		}
		return "admin.login";
	}
}
