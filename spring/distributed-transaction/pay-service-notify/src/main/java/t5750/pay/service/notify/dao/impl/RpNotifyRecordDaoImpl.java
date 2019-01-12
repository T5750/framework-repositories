package t5750.pay.service.notify.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import t5750.pay.common.core.dao.impl.BaseDaoImpl;
import t5750.pay.service.notify.dao.RpNotifyRecordDao;
import t5750.pay.service.notify.entity.RpNotifyRecord;

/**
 * @功能说明:
 * @版本:V1.0
 */
@Repository("rpNotifyRecordDao")
public class RpNotifyRecordDaoImpl extends BaseDaoImpl<RpNotifyRecord>
		implements RpNotifyRecordDao {
	@Override
	public RpNotifyRecord getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(
			String merchantNo, String merchantOrderNo, String notifyType) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("merchantNo", merchantNo);
		paramMap.put("merchantOrderNo", merchantOrderNo);
		paramMap.put("notifyType", notifyType);
		return super.getBy(paramMap);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		return 0;
	}

	@Override
	public RpNotifyRecord selectByPrimaryKey(String id) {
		return null;
	}

	@Override
	public int updateByPrimaryKey(RpNotifyRecord record) {
		return 0;
	}
}
