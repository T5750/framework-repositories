package t5750.tcctransactionboot.sample.dubbo.redpacket.api;

import org.mengyun.tcctransaction.api.Compensable;

import t5750.tcctransactionboot.sample.dubbo.redpacket.api.dto.RedPacketTradeOrderDto;

/**
 */
public interface RedPacketTradeOrderService {
	@Compensable
	public String record(RedPacketTradeOrderDto tradeOrderDto);
}
