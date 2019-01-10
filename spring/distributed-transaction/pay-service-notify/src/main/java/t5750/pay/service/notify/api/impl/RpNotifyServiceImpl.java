package t5750.pay.service.notify.api.impl;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import t5750.pay.common.core.page.PageBean;
import t5750.pay.common.core.page.PageParam;
import t5750.pay.service.notify.api.RpNotifyService;
import t5750.pay.service.notify.dao.RpNotifyRecordDao;
import t5750.pay.service.notify.dao.RpNotifyRecordLogDao;
import t5750.pay.service.notify.entity.RpNotifyRecord;
import t5750.pay.service.notify.entity.RpNotifyRecordLog;
import t5750.pay.service.notify.enums.NotifyStatusEnum;
import t5750.pay.service.notify.enums.NotifyTypeEnum;

import com.alibaba.fastjson.JSONObject;

/**
 * @功能说明:
 * @版本:V1.0
 */
@Service("rpNotifyService")
public class RpNotifyServiceImpl implements RpNotifyService {
	@Autowired
	private JmsTemplate notifyJmsTemplate;
	@Autowired
	private RpNotifyRecordDao rpNotifyRecordDao;
	@Autowired
	private RpNotifyRecordLogDao rpNotifyRecordLogDao;

	/**
	 * 发送消息通知
	 *
	 * @param notifyUrl
	 *            通知地址
	 * @param merchantOrderNo
	 *            商户订单号
	 * @param merchantNo
	 *            商户编号
	 */
	@Override
	public void notifySend(String notifyUrl, String merchantOrderNo,
			String merchantNo) {
		RpNotifyRecord record = new RpNotifyRecord();
		record.setNotifyTimes(0);
		record.setLimitNotifyTimes(5);
		record.setStatus(NotifyStatusEnum.CREATED.name());
		record.setUrl(notifyUrl);
		record.setMerchantOrderNo(merchantOrderNo);
		record.setMerchantNo(merchantNo);
		record.setNotifyType(NotifyTypeEnum.MERCHANT.name());
		Object toJSON = JSONObject.toJSON(record);
		final String str = toJSON.toString();
		notifyJmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(str);
			}
		});
	}

	/**
	 * 通过ID获取通知记录
	 *
	 * @param id
	 * @return
	 */
	@Override
	public RpNotifyRecord getNotifyRecordById(String id) {
		return rpNotifyRecordDao.getById(id);
	}

	/**
	 * 根据商户编号,商户订单号,通知类型获取通知记录
	 *
	 * @param merchantNo
	 *            商户编号
	 * @param merchantOrderNo
	 *            商户订单号
	 * @param notifyType
	 *            消息类型
	 * @return
	 */
	@Override
	public RpNotifyRecord getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(
			String merchantNo, String merchantOrderNo, String notifyType) {
		return rpNotifyRecordDao
				.getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(
						merchantNo, merchantOrderNo, notifyType);
	}

	@Override
	public PageBean<RpNotifyRecord> queryNotifyRecordListPage(
			PageParam pageParam, Map<String, Object> paramMap) {
		return rpNotifyRecordDao.listPage(pageParam, paramMap);
	}

	/**
	 * 创建消息通知
	 *
	 * @param rpNotifyRecord
	 */
	@Override
	public long createNotifyRecord(RpNotifyRecord rpNotifyRecord) {
		return rpNotifyRecordDao.insert(rpNotifyRecord);
	}

	/**
	 * 修改消息通知
	 *
	 * @param rpNotifyRecord
	 */
	@Override
	public void updateNotifyRecord(RpNotifyRecord rpNotifyRecord) {
		rpNotifyRecordDao.update(rpNotifyRecord);
	}

	/**
	 * 创建消息通知记录
	 *
	 * @param rpNotifyRecordLog
	 * @return
	 */
	@Override
	public long createNotifyRecordLog(RpNotifyRecordLog rpNotifyRecordLog) {
		return rpNotifyRecordLogDao.insert(rpNotifyRecordLog);
	}
}
