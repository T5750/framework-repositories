package t5750.pay.service.notify.dao;

import t5750.pay.common.core.dao.BaseDao;
import t5750.pay.service.notify.entity.RpNotifyRecordLog;

/**
 * @功能说明:
 * @版本:V1.0
 */
public interface RpNotifyRecordLogDao extends BaseDao<RpNotifyRecordLog> {
	int deleteByPrimaryKey(String id);

	RpNotifyRecordLog selectByPrimaryKey(String id);

	int updateByPrimaryKey(RpNotifyRecordLog record);
}