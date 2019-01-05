package t5750.pay.service.user.api;

import java.util.List;

import t5750.pay.common.core.page.PageBean;
import t5750.pay.common.core.page.PageParam;
import t5750.pay.service.user.entity.RpPayProduct;
import t5750.pay.service.user.exceptions.PayBizException;

/**
 * @类功能说明： 支付产品service接口 @版本：V1.0
 */
public interface RpPayProductService {
	/**
	 * 保存
	 */
	void saveData(RpPayProduct rpPayProduct) throws PayBizException;

	/**
	 * 更新
	 */
	void updateData(RpPayProduct rpPayProduct) throws PayBizException;

	/**
	 * 根据id获取数据
	 * 
	 * @param id
	 * @return
	 */
	RpPayProduct getDataById(String id) throws PayBizException;

	/**
	 * 获取分页数据
	 * 
	 * @param pageParam
	 * @return
	 */
	PageBean listPage(PageParam pageParam, RpPayProduct rpPayProduct)
			throws PayBizException;

	/**
	 * 根据产品编号获取支付产品
	 * 
	 * @param productCode
	 * @return
	 */
	RpPayProduct getByProductCode(String productCode, String auditStatus)
			throws PayBizException;

	/**
	 * 创建支付产品
	 * 
	 * @param productCode
	 * @param productName
	 */
	void createPayProduct(String productCode, String productName)
			throws PayBizException;

	/**
	 * 删除支付产品
	 * 
	 * @param productCode
	 */
	void deletePayProduct(String productCode) throws PayBizException;

	/**
	 * 获取所有支付产品
	 */
	List<RpPayProduct> listAll() throws PayBizException;

	/**
	 * 审核
	 * 
	 * @param productCode
	 * @param auditStatus
	 */
	void audit(String productCode, String auditStatus) throws PayBizException;
}