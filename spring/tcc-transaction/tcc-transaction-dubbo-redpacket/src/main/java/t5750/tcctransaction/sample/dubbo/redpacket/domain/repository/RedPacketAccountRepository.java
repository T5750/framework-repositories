package t5750.tcctransaction.sample.dubbo.redpacket.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import t5750.tcctransaction.sample.dubbo.redpacket.domain.entity.RedPacketAccount;
import t5750.tcctransaction.sample.dubbo.redpacket.infrastructure.dao.RedPacketAccountDao;

/**
 */
@Repository
public class RedPacketAccountRepository {
	@Autowired
	RedPacketAccountDao redPacketAccountDao;

	public RedPacketAccount findByUserId(long userId) {
		return redPacketAccountDao.findByUserId(userId);
	}

	public void save(RedPacketAccount redPacketAccount) {
		redPacketAccountDao.update(redPacketAccount);
	}
}
