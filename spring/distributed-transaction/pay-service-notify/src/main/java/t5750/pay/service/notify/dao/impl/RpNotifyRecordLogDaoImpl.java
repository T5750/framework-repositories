package t5750.pay.service.notify.dao.impl;

import org.springframework.stereotype.Repository;

import t5750.pay.common.core.dao.impl.BaseDaoImpl;
import t5750.pay.service.notify.dao.RpNotifyRecordLogDao;
import t5750.pay.service.notify.entity.RpNotifyRecordLog;

/**
 * @功能说明:
 * @版本:V1.0
 */
@Repository("rpNotifyRecordLogDao")
public class RpNotifyRecordLogDaoImpl extends BaseDaoImpl<RpNotifyRecordLog>
		implements RpNotifyRecordLogDao {
	@Override
	public int deleteByPrimaryKey(String id) {
		return 0;
	}

	@Override
	public RpNotifyRecordLog selectByPrimaryKey(String id) {
		return null;
	}

	@Override
	public int updateByPrimaryKey(RpNotifyRecordLog record) {
		return 0;
	}
}
