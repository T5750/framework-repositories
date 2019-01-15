package t5750.tcctransaction.sample.dubbo.redpacket.api;

import t5750.tcctransaction.api.TransactionContext;
import t5750.tcctransaction.sample.dubbo.redpacket.api.dto.RedPacketTradeOrderDto;

/**
 */
public interface RedPacketTradeOrderService {
	public String record(TransactionContext transactionContext,
			RedPacketTradeOrderDto tradeOrderDto);
}
