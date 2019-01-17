package t5750.pay.service.point.entity;

import java.io.Serializable;

import t5750.pay.common.core.entity.BaseEntity;
import t5750.pay.common.core.enums.TrxTypeEnum;
import t5750.pay.service.point.enums.PointAccountFundDirectionEnum;

/**
 * @功能说明: 账户历史信息
 */
public class RpPointAccountHistory extends BaseEntity implements Serializable {
	/** 积分数量 **/
	private Integer amount;
	/** 账户余额 **/
	private Integer balance;
	/** 资金变动方向 **/
	private String fundDirection;
	/** 请求号 **/
	private String requestNo;
	/** 银行流水号 **/
	private String bankTrxNo;
	/** 业务类型 **/
	private String trxType;
	/** 用户编号 **/
	private String userNo;
	private static final long serialVersionUID = 1L;

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public String getFundDirection() {
		return fundDirection;
	}

	public void setFundDirection(String fundDirection) {
		this.fundDirection = fundDirection;
	}

	public String getFundDirectionDesc() {
		return PointAccountFundDirectionEnum.getEnum(this.getFundDirection())
				.getLabel();
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo == null ? null : requestNo.trim();
	}

	public String getBankTrxNo() {
		return bankTrxNo;
	}

	public void setBankTrxNo(String bankTrxNo) {
		this.bankTrxNo = bankTrxNo == null ? null : bankTrxNo.trim();
	}

	public String getTrxType() {
		return trxType;
	}

	public void setTrxType(String trxType) {
		this.trxType = trxType == null ? null : trxType.trim();
	}

	public String getTrxTypeDesc() {
		return TrxTypeEnum.getEnum(this.getTrxType()).getDesc();
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo == null ? null : userNo.trim();
	}
}