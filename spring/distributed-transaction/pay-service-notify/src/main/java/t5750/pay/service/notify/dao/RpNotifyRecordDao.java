package t5750.pay.service.notify.dao;

import t5750.pay.common.core.dao.BaseDao;
import t5750.pay.service.notify.entity.RpNotifyRecord;

/**
 * @功能说明:
 * @版本:V1.0
 */
public interface RpNotifyRecordDao extends BaseDao<RpNotifyRecord> {
	RpNotifyRecord getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(
			String merchantNo, String merchantOrderNo, String notifyType);

	int deleteByPrimaryKey(String id);

	int insertSelective(RpNotifyRecord record);

	RpNotifyRecord selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(RpNotifyRecord record);

	int updateByPrimaryKey(RpNotifyRecord record);
}