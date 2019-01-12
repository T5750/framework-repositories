package t5750.pay.app.notify.core;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import t5750.pay.app.notify.PayAppNotify;
import t5750.pay.app.notify.param.NotifyParam;
import t5750.pay.common.core.utils.DateUtils;
import t5750.pay.service.notify.entity.RpNotifyRecord;

/**
 * @功能说明:
 * @版本:V1.0
 */
@Service("notifyQueue")
public class NotifyQueue implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Log LOG = LogFactory.getLog(NotifyQueue.class);
	@Autowired
	private NotifyParam notifyParam;

	/**
	 * 将传过来的对象进行通知次数判断，决定是否放在任务队列中.<br/>
	 * 
	 * @param notifyRecord
	 * @throws Exception
	 */
	public void addToNotifyTaskDelayQueue(RpNotifyRecord notifyRecord) {
		if (notifyRecord == null) {
			return;
		}
		LOG.info("===>addToNotifyTaskDelayQueue notify id:"
				+ notifyRecord.getId());
		Integer notifyTimes = notifyRecord.getNotifyTimes(); // 通知次数
		Integer maxNotifyTimes = notifyRecord.getLimitNotifyTimes(); // 最大通知次数
		if (notifyRecord.getNotifyTimes().intValue() == 0) {
			notifyRecord.setLastNotifyTime(new Date()); // 第一次发送(取当前时间)
		} else {
			notifyRecord.setLastNotifyTime(notifyRecord.getEditTime()); // 非第一次发送（取上一次修改时间，也是上一次发送时间）
		}
		if (notifyTimes < maxNotifyTimes) {
			// 未超过最大通知次数，继续下一次通知
			LOG.info("===>notify id:" + notifyRecord.getId()
					+ ", 上次通知时间lastNotifyTime:"
					+ DateUtils.formatDate(notifyRecord.getLastNotifyTime(),
							"yyyy-MM-dd HH:mm:ss SSS"));
			PayAppNotify.tasks
					.put(new NotifyTask(notifyRecord, this, notifyParam));
		}
	}
}
