package t5750.pay.service.trade.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import t5750.pay.common.core.enums.PublicEnum;

/**
 * @功能说明:
 * @版本:V1.0
 */
public class OrderPayResultVo implements Serializable {
	private static final long serialVersionUID = 2023891587770894541L;
	/** 状态 **/
	private String status = PublicEnum.NO.name();
	/** 金额 **/
	private BigDecimal orderPrice;
	/** 商户页面通知结果地址 **/
	private String returnUrl;
	/** 产品名称 **/
	private String productName;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", status=").append(status);
		sb.append(", orderPrice=").append(orderPrice);
		sb.append(", returnUrl=").append(returnUrl);
		sb.append(", productName=").append(productName);
		sb.append("]");
		return sb.toString();
	}
}
