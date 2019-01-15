package t5750.tcctransaction.sample.dubbo.redpacket.infrastructure.dao;

import t5750.tcctransaction.sample.dubbo.redpacket.domain.entity.RedPacketAccount;

/**
 */
public interface RedPacketAccountDao {
	RedPacketAccount findByUserId(long userId);

	void update(RedPacketAccount redPacketAccount);
}
