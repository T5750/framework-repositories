package t5750.pay.service.message.dao.impl;

import org.springframework.stereotype.Repository;

import t5750.pay.common.core.dao.impl.BaseDaoImpl;
import t5750.pay.service.message.dao.RpTransactionMessageDao;
import t5750.pay.service.message.entity.RpTransactionMessage;

/**
 * <b>功能说明: </b>
 */
@Repository("rpTransactionMessageDao")
public class RpTransactionMessageDaoImpl extends
		BaseDaoImpl<RpTransactionMessage> implements RpTransactionMessageDao {
}
