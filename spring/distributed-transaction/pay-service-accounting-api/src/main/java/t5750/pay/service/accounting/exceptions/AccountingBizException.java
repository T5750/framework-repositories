package t5750.pay.service.accounting.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import t5750.pay.common.core.exception.BizException;

/**
 * 会计服务业务异常类,异常代码8位数字组成,前4位固定1001打头,后4位自定义
 */
public class AccountingBizException extends BizException {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(AccountingBizException.class);
	public static final AccountingBizException ACCOUNT_NOT_EXIT = new AccountingBizException(
			10010001, "账户不存在");
	public static final AccountingBizException ACCOUNT_SUB_AMOUNT_OUTLIMIT = new AccountingBizException(
			10010002, "余额不足，减款超限");
	public static final AccountingBizException ACCOUNT_UN_FROZEN_AMOUNT_OUTLIMIT = new AccountingBizException(
			10010003, "解冻金额超限");
	public static final AccountingBizException ACCOUNT_FROZEN_AMOUNT_OUTLIMIT = new AccountingBizException(
			10010004, "冻结金额超限");
	public static final AccountingBizException ACCOUNT_TYPE_IS_NULL = new AccountingBizException(
			10010005, "账户类型不能为空");

	public AccountingBizException() {
	}

	public AccountingBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public AccountingBizException(int code, String msg) {
		super(code, msg);
	}

	/**
	 * 实例化异常
	 * 
	 * @param msgFormat
	 * @param args
	 * @return
	 */
	@Override
	public AccountingBizException newInstance(String msgFormat, Object... args) {
		return new AccountingBizException(this.code, msgFormat, args);
	}

	public AccountingBizException print() {
		logger.info("==>BizException, code:" + this.code + ", msg:" + this.msg);
		return this;
	}
}
