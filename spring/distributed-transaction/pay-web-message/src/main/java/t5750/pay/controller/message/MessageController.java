package t5750.pay.controller.message;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import t5750.pay.common.core.dwz.DWZ;
import t5750.pay.common.core.dwz.DwzAjax;
import t5750.pay.common.core.enums.NotifyDestinationNameEnum;
import t5750.pay.common.core.page.PageBean;
import t5750.pay.common.core.page.PageParam;
import t5750.pay.common.core.utils.StringUtil;
import t5750.pay.service.message.api.RpTransactionMessageService;
import t5750.pay.service.message.entity.RpTransactionMessage;
import t5750.pay.service.message.enums.MessageStatusEnum;

/**
 * message控制器
 */
@Controller
@RequestMapping("/message")
public class MessageController {
	private static final Log log = LogFactory.getLog(MessageController.class);
	@Autowired
	private RpTransactionMessageService rpTransactionMessageService;

	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, PageParam pageParam,
			RpTransactionMessage message, Model model) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("areadlyDead", message.getAreadlyDead());
		paramMap.put("messageId", message.getMessageId());
		paramMap.put("consumerQueue", message.getConsumerQueue());
		paramMap.put("status", message.getStatus());
		PageBean pageBean = rpTransactionMessageService.listPage(pageParam,
				paramMap);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("messageStatus", MessageStatusEnum.toList());
		model.addAttribute("queues", NotifyDestinationNameEnum.toList());
		return "message/list";
	}

	@RequestMapping(value = "/sendMessage")
	public String sendMessage(String messageId, Model model) {
		DwzAjax dwz = new DwzAjax();
		try {
			rpTransactionMessageService.reSendMessageByMessageId(messageId);
			dwz.setStatusCode(DWZ.SUCCESS);
			dwz.setNavTabId("xxlb");
			dwz.setMessage("操作成功");
			model.addAttribute("dwz", dwz);
			return DWZ.AJAX_DONE;
		} catch (Exception e) {
			dwz.setStatusCode(DWZ.ERROR);
			dwz.setMessage("退出系统时系统出现异常，请通知系统管理员！");
			model.addAttribute("dwz", dwz);
			return DWZ.AJAX_DONE;
		}
	}

	/**
	 * 一键触发发送某个消息队列全部已死亡的消息
	 * 
	 * @param queueName
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/sendAllMessage")
	public String sendAllMessage(String queueName, Model model) {
		DwzAjax dwz = new DwzAjax();
		try {
			if (StringUtil.isEmpty(queueName)) {
				dwz.setStatusCode(DWZ.ERROR);
				dwz.setMessage("请选择相应的队列名称");
				model.addAttribute("dwz", dwz);
				return DWZ.AJAX_DONE;
			}
			rpTransactionMessageService.reSendAllDeadMessageByQueueName(
					queueName, 2000);
			dwz.setStatusCode(DWZ.SUCCESS);
			dwz.setNavTabId("xxlb");
			dwz.setMessage("操作成功");
			model.addAttribute("dwz", dwz);
			return DWZ.AJAX_DONE;
		} catch (Exception e) {
			dwz.setStatusCode(DWZ.ERROR);
			dwz.setMessage("退出系统时系统出现异常，请通知系统管理员！");
			model.addAttribute("dwz", dwz);
			return DWZ.AJAX_DONE;
		}
	}
}
