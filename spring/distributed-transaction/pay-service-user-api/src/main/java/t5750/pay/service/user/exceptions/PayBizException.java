package t5750.pay.service.user.exceptions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import t5750.pay.common.core.exception.BizException;

/**
 * @功能说明: 支付业务异常类
 * @版本:V1.0
 */
public class PayBizException extends BizException {
	/** 支付方式已存在 **/
	public static final int PAY_TYPE_IS_EXIST = 101;
	/** 支付产品已存在 **/
	public static final int PAY_PRODUCT_IS_EXIST = 102;
	/** 支付产品已关联数据 **/
	public static final int PAY_PRODUCT_HAS_DATA = 103;
	/** 用户支付配置已存在 **/
	public static final int USER_PAY_CONFIG_IS_EXIST = 104;
	/** 用户支付配置不存在 **/
	public static final int USER_PAY_CONFIG_IS_NOT_EXIST = 105;
	/** 用户支付配置已生效 **/
	public static final int USER_PAY_CONFIG_IS_EFFECTIVE = 106;
	/** 支付产品已生效 **/
	public static final int PAY_PRODUCT_IS_EFFECTIVE = 107;
	/** 支付产品不存在 **/
	public static final int PAY_PRODUCT_IS_NOT_EXIST = 108;
	/** 支付方式不存在 **/
	public static final int PAY_TYPE_IS_NOT_EXIST = 108;
	private static final Log LOG = LogFactory.getLog(PayBizException.class);

	public PayBizException() {
	}

	public PayBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public PayBizException(int code, String msg) {
		super(code, msg);
	}

	public PayBizException print() {
		LOG.info("==>BizException, code:" + this.code + ", msg:" + this.msg);
		return this;
	}
}
