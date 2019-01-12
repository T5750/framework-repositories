package t5750.pay.app.notify.param;

import java.util.Map;

/**
 * @功能说明: 通知参数（通知规则），从XML中加载.
 * @版本:V1.0
 */
public class NotifyParam {
	/**
	 * 通知参数（通知规则Map）
	 */
	private Map<Integer, Integer> notifyParams;
	/**
	 * 通知后用于判断是否成功的返回值（成功标识）,由HttpResponse获取
	 */
	private String successValue;

	public Map<Integer, Integer> getNotifyParams() {
		return notifyParams;
	}

	public void setNotifyParams(Map<Integer, Integer> notifyParams) {
		this.notifyParams = notifyParams;
	}

	public String getSuccessValue() {
		return successValue;
	}

	public void setSuccessValue(String successValue) {
		this.successValue = successValue;
	}

	/**
	 * 最大通知次数限制.
	 * 
	 * @return
	 */
	public Integer getMaxNotifyTimes() {
		return notifyParams == null ? 0 : notifyParams.size();
	}
}
