package t5750.pay.service.notify.exceptions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import t5750.pay.common.core.exception.BizException;

/**
 * <b>功能说明: </b>
 */
public class NotifyBizException extends BizException {
	private static final long serialVersionUID = 3536909333010163563L;
	/** 保存的消息为空 **/
	public static final int NOTIFY_SYSTEM_EXCEPTION = 9001;
	private static final Log LOG = LogFactory.getLog(NotifyBizException.class);

	public NotifyBizException() {
	}

	public NotifyBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public NotifyBizException(int code, String msg) {
		super(code, msg);
	}

	public NotifyBizException print() {
		LOG.info("==>BizException, code:" + this.code + ", msg:" + this.msg);
		return this;
	}
}
