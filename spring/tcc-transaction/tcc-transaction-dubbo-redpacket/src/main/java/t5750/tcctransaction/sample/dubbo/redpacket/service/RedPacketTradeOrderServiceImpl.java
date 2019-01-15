package t5750.tcctransaction.sample.dubbo.redpacket.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import t5750.tcctransaction.Compensable;
import t5750.tcctransaction.api.TransactionContext;
import t5750.tcctransaction.sample.dubbo.redpacket.api.RedPacketTradeOrderService;
import t5750.tcctransaction.sample.dubbo.redpacket.api.dto.RedPacketTradeOrderDto;
import t5750.tcctransaction.sample.dubbo.redpacket.domain.entity.RedPacketAccount;
import t5750.tcctransaction.sample.dubbo.redpacket.domain.entity.TradeOrder;
import t5750.tcctransaction.sample.dubbo.redpacket.domain.repository.RedPacketAccountRepository;
import t5750.tcctransaction.sample.dubbo.redpacket.domain.repository.TradeOrderRepository;

/**
 */
@Service("redPacketTradeOrderService")
public class RedPacketTradeOrderServiceImpl implements
		RedPacketTradeOrderService {
	private static final Log LOG = LogFactory
			.getLog(RedPacketTradeOrderServiceImpl.class);
	@Autowired
	RedPacketAccountRepository redPacketAccountRepository;
	@Autowired
	TradeOrderRepository tradeOrderRepository;

	@Override
	@Compensable(confirmMethod = "confirmRecord", cancelMethod = "cancelRecord")
	@Transactional
	public String record(TransactionContext transactionContext,
			RedPacketTradeOrderDto tradeOrderDto) {
		LOG.debug("==>red packet try record called");
		TradeOrder tradeOrder = new TradeOrder(tradeOrderDto.getSelfUserId(),
				tradeOrderDto.getOppositeUserId(),
				tradeOrderDto.getMerchantOrderNo(), tradeOrderDto.getAmount());
		tradeOrderRepository.insert(tradeOrder);
		RedPacketAccount transferFromAccount = redPacketAccountRepository
				.findByUserId(tradeOrderDto.getSelfUserId());
		transferFromAccount.transferFrom(tradeOrderDto.getAmount());
		redPacketAccountRepository.save(transferFromAccount);
		return "success";
	}

	@Transactional
	public void confirmRecord(TransactionContext transactionContext,
			RedPacketTradeOrderDto tradeOrderDto) {
		LOG.debug("==>red packet confirm record called");
		TradeOrder tradeOrder = tradeOrderRepository
				.findByMerchantOrderNo(tradeOrderDto.getMerchantOrderNo());
		tradeOrder.confirm();
		tradeOrderRepository.update(tradeOrder);
		RedPacketAccount transferToAccount = redPacketAccountRepository
				.findByUserId(tradeOrderDto.getOppositeUserId());
		transferToAccount.transferTo(tradeOrderDto.getAmount());
		redPacketAccountRepository.save(transferToAccount);
	}

	@Transactional
	public void cancelRecord(TransactionContext transactionContext,
			RedPacketTradeOrderDto tradeOrderDto) {
		LOG.debug("==>red packet cancel record called");
		TradeOrder tradeOrder = tradeOrderRepository
				.findByMerchantOrderNo(tradeOrderDto.getMerchantOrderNo());
		if (null != tradeOrder && "DRAFT".equals(tradeOrder.getStatus())) {
			tradeOrder.cancel();
			tradeOrderRepository.update(tradeOrder);
			RedPacketAccount capitalAccount = redPacketAccountRepository
					.findByUserId(tradeOrderDto.getSelfUserId());
			capitalAccount.cancelTransfer(tradeOrderDto.getAmount());
			redPacketAccountRepository.save(capitalAccount);
		}
	}
}
