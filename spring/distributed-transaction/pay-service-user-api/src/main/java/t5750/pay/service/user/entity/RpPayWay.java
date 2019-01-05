package t5750.pay.service.user.entity;

import java.io.Serializable;

import t5750.pay.common.core.entity.BaseEntity;

/**
 * @类功能说明： 支付方式实体类 @版本：V1.0
 */
public class RpPayWay extends BaseEntity implements Serializable {
	private String payWayCode;
	private String payWayName;
	private String payTypeCode;
	private String payTypeName;
	private String payProductCode;
	private Integer sorts;
	private Double payRate;
	private static final long serialVersionUID = 1L;

	public String getPayWayCode() {
		return payWayCode;
	}

	public void setPayWayCode(String payWayCode) {
		this.payWayCode = payWayCode == null ? null : payWayCode.trim();
	}

	public String getPayWayName() {
		return payWayName;
	}

	public void setPayWayName(String payWayName) {
		this.payWayName = payWayName == null ? null : payWayName.trim();
	}

	public String getPayTypeCode() {
		return payTypeCode;
	}

	public void setPayTypeCode(String payTypeCode) {
		this.payTypeCode = payTypeCode == null ? null : payTypeCode.trim();
	}

	public String getPayTypeName() {
		return payTypeName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName == null ? null : payTypeName.trim();
	}

	public String getPayProductCode() {
		return payProductCode;
	}

	public void setPayProductCode(String payProductCode) {
		this.payProductCode = payProductCode == null ? null
				: payProductCode.trim();
	}

	public Integer getSorts() {
		return sorts;
	}

	public void setSorts(Integer sorts) {
		this.sorts = sorts;
	}

	public Double getPayRate() {
		return payRate;
	}

	public void setPayRate(Double payRate) {
		this.payRate = payRate;
	}
}