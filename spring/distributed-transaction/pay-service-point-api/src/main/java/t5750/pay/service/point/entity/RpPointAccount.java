package t5750.pay.service.point.entity;

import java.io.Serializable;

import t5750.pay.common.core.entity.BaseEntity;

/**
 * @功能说明: 账户信息
 */
public class RpPointAccount extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1996976092574646493L;
	/** 用户编号 **/
	private String userNo;
	/** 账户余额 **/
	private Integer balance;

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo == null ? null : userNo.trim();
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}
}