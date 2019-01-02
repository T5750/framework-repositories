package t5750.pay.service.trade.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @功能说明:
 * @版本:V1.0
 */
public class ScanPayResultVo implements Serializable {
	private static final long serialVersionUID = 1324566713391515644L;
	/**
	 * 支付方式编码
	 */
	private String payWayCode;
	/** 金额 **/
	private BigDecimal orderAmount;
	/** 产品名称 **/
	private String productName;
	/**
	 * 二维码地址
	 */
	private String codeUrl;

	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}

	public String getPayWayCode() {
		return payWayCode;
	}

	public void setPayWayCode(String payWayCode) {
		this.payWayCode = payWayCode;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}
