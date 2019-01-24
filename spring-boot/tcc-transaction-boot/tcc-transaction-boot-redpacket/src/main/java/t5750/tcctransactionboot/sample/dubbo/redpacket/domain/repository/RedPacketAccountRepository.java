package t5750.tcctransactionboot.sample.dubbo.redpacket.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import t5750.tcctransactionboot.sample.dubbo.redpacket.domain.entity.RedPacketAccount;
import t5750.tcctransactionboot.sample.dubbo.redpacket.infrastructure.dao.RedPacketAccountDao;
import t5750.tcctransactionboot.sample.exception.InsufficientBalanceException;

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
		int effectCount = redPacketAccountDao.update(redPacketAccount);
		if (effectCount < 1) {
			throw new InsufficientBalanceException();
		}
	}
}
