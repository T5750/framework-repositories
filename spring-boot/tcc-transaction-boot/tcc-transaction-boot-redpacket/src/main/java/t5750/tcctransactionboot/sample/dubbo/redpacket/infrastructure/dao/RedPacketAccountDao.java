package t5750.tcctransactionboot.sample.dubbo.redpacket.infrastructure.dao;

import t5750.tcctransactionboot.sample.dubbo.redpacket.domain.entity.RedPacketAccount;

/**
 */
public interface RedPacketAccountDao {
	RedPacketAccount findByUserId(long userId);

	int update(RedPacketAccount redPacketAccount);
}
