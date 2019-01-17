package t5750.pay.service.account.api;

import java.math.BigDecimal;

import t5750.pay.service.account.entity.RpAccount;
import t5750.pay.service.account.exceptions.AccountBizException;
import t5750.tcctransaction.api.TransactionContext;

/**
 * @类功能说明： 账户操作service接口 @版本：V1.0
 */
public interface RpAccountTransactionService {
	/** 加款:有银行流水 **/
	RpAccount creditToAccount(String userNo, BigDecimal amount,
			String requestNo, String bankTrxNo, String trxType, String remark)
			throws AccountBizException;

	/** 加款:有银行流水 **/
	void creditToAccountTcc(TransactionContext transactionContext,
			String userNo, BigDecimal amount, String requestNo,
			String bankTrxNo, String trxType, String remark)
			throws AccountBizException;

	/** 减款 :有银行流水 **/
	RpAccount debitToAccount(String userNo, BigDecimal amount,
			String requestNo, String bankTrxNo, String trxType, String remark)
			throws AccountBizException;

	/** 加款 **/
	RpAccount creditToAccount(String userNo, BigDecimal amount,
			String requestNo, String trxType, String remark)
			throws AccountBizException;

	/** 减款 **/
	RpAccount debitToAccount(String userNo, BigDecimal amount,
			String requestNo, String trxType, String remark)
			throws AccountBizException;

	/** 冻结 **/
	RpAccount freezeAmount(String userNo, BigDecimal freezeAmount)
			throws AccountBizException;

	/** 结算成功：解冻+减款 **/
	RpAccount unFreezeAmount(String userNo, BigDecimal amount,
			String requestNo, String trxType, String remark)
			throws AccountBizException;

	/** 结算失败：解冻 **/
	RpAccount unFreezeSettAmount(String userNo, BigDecimal amount)
			throws AccountBizException;

	/** 更新账户历史中的结算状态，并且累加可结算金额 **/
	void settCollectSuccess(String accountNo, String collectDate, int riskDay,
			BigDecimal totalAmount) throws AccountBizException;
}