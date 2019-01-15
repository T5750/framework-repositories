package t5750.tcctransaction.sample.dubbo.redpacket.api;

import java.math.BigDecimal;

/**
 */
public interface RedPacketAccountService {
	BigDecimal getRedPacketAccountByUserId(long userId);
}