package t5750.pay.controller.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import t5750.pay.common.core.dwz.DWZ;
import t5750.pay.common.core.dwz.DwzAjax;
import t5750.pay.common.core.enums.PublicEnum;
import t5750.pay.common.core.page.PageBean;
import t5750.pay.common.core.page.PageParam;
import t5750.pay.service.user.api.RpPayProductService;
import t5750.pay.service.user.entity.RpPayProduct;

/**
 * @类功能说明： 支付产品管理 @版本：V1.0
 */
@Controller
@RequestMapping("/pay/product")
public class PayProductController {
	@Autowired
	private RpPayProductService rpPayProductService;

	/**
	 * 函数功能说明 ： 查询分页
	 * 
	 * @参数： @return @return String @throws
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String list(RpPayProduct rpPayProduct, PageParam pageParam,
			Model model) {
		PageBean pageBean = rpPayProductService.listPage(pageParam,
				rpPayProduct);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("rpPayProduct", rpPayProduct);
		return "pay/product/list";
	}

	/**
	 * 函数功能说明 ：跳转添加
	 * 
	 * @参数： @return @return String @throws
	 */
	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI() {
		return "pay/product/add";
	}

	/**
	 * 函数功能说明 ： 保存
	 * 
	 * @参数： @return @return String @throws
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(Model model, RpPayProduct rpPayProduct, DwzAjax dwz) {
		rpPayProductService.createPayProduct(rpPayProduct.getProductCode(),
				rpPayProduct.getProductName());
		dwz.setStatusCode(DWZ.SUCCESS);
		dwz.setMessage(DWZ.SUCCESS_MSG);
		model.addAttribute("dwz", dwz);
		return DWZ.AJAX_DONE;
	}

	/**
	 * 函数功能说明 ： 删除
	 * 
	 * @参数： @return @return String @throws
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String delete(Model model, DwzAjax dwz,
			@RequestParam("productCode") String productCode) {
		rpPayProductService.deletePayProduct(productCode);
		dwz.setStatusCode(DWZ.SUCCESS);
		dwz.setMessage(DWZ.SUCCESS_MSG);
		model.addAttribute("dwz", dwz);
		return DWZ.AJAX_DONE;
	}

	/**
	 * 函数功能说明 ： 查找带回
	 * 
	 * @参数： @return @return String @throws
	 */
	@RequestMapping(value = "/lookupList", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String lookupList(RpPayProduct rpPayProduct, PageParam pageParam,
			Model model) {
		// 查询已生效数据
		rpPayProduct.setAuditStatus(PublicEnum.YES.name());
		PageBean pageBean = rpPayProductService.listPage(pageParam,
				rpPayProduct);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("rpPayProduct", rpPayProduct);
		return "pay/product/lookupList";
	}

	/**
	 * 函数功能说明 ： 审核
	 * 
	 * @参数： @return @return String @throws
	 */
	@RequestMapping(value = "/audit", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String audit(Model model, DwzAjax dwz,
			@RequestParam("productCode") String productCode,
			@RequestParam("auditStatus") String auditStatus) {
		rpPayProductService.audit(productCode, auditStatus);
		dwz.setStatusCode(DWZ.SUCCESS);
		dwz.setMessage(DWZ.SUCCESS_MSG);
		model.addAttribute("dwz", dwz);
		return DWZ.AJAX_DONE;
	}
}
