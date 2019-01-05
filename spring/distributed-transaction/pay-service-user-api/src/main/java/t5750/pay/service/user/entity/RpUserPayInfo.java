package t5750.pay.service.user.entity;

import java.io.Serializable;

import t5750.pay.common.core.entity.BaseEntity;

/**
 * @类功能说明：用户第三方支付信息实体类 @版本：V1.0
 */
public class RpUserPayInfo extends BaseEntity implements Serializable {
	/**
	 * 对应关系 微信：appid 支付宝：partner
	 */
	private String appId;
	private String appSectet;
	/**
	 * 对应关系 微信：merchantid 支付宝：seller_id
	 */
	private String merchantId;
	private String appType;
	private String userNo;
	private String userName;
	/**
	 * 对应关系 微信：partnerkey 支付宝：key
	 */
	private String partnerKey;
	private String payWayCode;
	private String payWayName;

	public String getPayWayCode() {
		return payWayCode;
	}

	public void setPayWayCode(String payWayCode) {
		this.payWayCode = payWayCode;
	}

	public String getPayWayName() {
		return payWayName;
	}

	public void setPayWayName(String payWayName) {
		this.payWayName = payWayName;
	}

	public String getPartnerKey() {
		return partnerKey;
	}

	public void setPartnerKey(String partnerKey) {
		this.partnerKey = partnerKey;
	}

	private static final long serialVersionUID = 1L;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId == null ? null : appId.trim();
	}

	public String getAppSectet() {
		return appSectet;
	}

	public void setAppSectet(String appSectet) {
		this.appSectet = appSectet == null ? null : appSectet.trim();
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId == null ? null : merchantId.trim();
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType == null ? null : appType.trim();
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo == null ? null : userNo.trim();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}
}