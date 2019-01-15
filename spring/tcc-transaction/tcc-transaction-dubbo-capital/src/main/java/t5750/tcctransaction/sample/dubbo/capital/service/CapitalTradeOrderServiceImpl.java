package t5750.tcctransaction.sample.dubbo.capital.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import t5750.tcctransaction.Compensable;
import t5750.tcctransaction.api.TransactionContext;
import t5750.tcctransaction.sample.dubbo.capital.api.CapitalTradeOrderService;
import t5750.tcctransaction.sample.dubbo.capital.api.dto.CapitalTradeOrderDto;
import t5750.tcctransaction.sample.dubbo.capital.domain.entity.CapitalAccount;
import t5750.tcctransaction.sample.dubbo.capital.domain.entity.TradeOrder;
import t5750.tcctransaction.sample.dubbo.capital.domain.repository.CapitalAccountRepository;
import t5750.tcctransaction.sample.dubbo.capital.domain.repository.TradeOrderRepository;

/**
 */
@Service("capitalTradeOrderService")
public class CapitalTradeOrderServiceImpl implements CapitalTradeOrderService {
	private static final Log LOG = LogFactory
			.getLog(CapitalTradeOrderServiceImpl.class);
	@Autowired
	CapitalAccountRepository capitalAccountRepository;
	@Autowired
	TradeOrderRepository tradeOrderRepository;

	@Override
	@Compensable(confirmMethod = "confirmRecord", cancelMethod = "cancelRecord")
	@Transactional
	public String record(TransactionContext transactionContext,
			CapitalTradeOrderDto tradeOrderDto) {
		LOG.debug("==>capital try record called");
		TradeOrder tradeOrder = new TradeOrder(tradeOrderDto.getSelfUserId(),
				tradeOrderDto.getOppositeUserId(),
				tradeOrderDto.getMerchantOrderNo(), tradeOrderDto.getAmount());
		tradeOrderRepository.insert(tradeOrder);
		CapitalAccount transferFromAccount = capitalAccountRepository
				.findByUserId(tradeOrderDto.getSelfUserId());
		transferFromAccount.transferFrom(tradeOrderDto.getAmount()); // 先扣减付款方资金帐户资金
		capitalAccountRepository.save(transferFromAccount);
		return "success";
	}

	@Transactional
	public void confirmRecord(TransactionContext transactionContext,
			CapitalTradeOrderDto tradeOrderDto) {
		LOG.debug("==>capital confirm record called");
		TradeOrder tradeOrder = tradeOrderRepository
				.findByMerchantOrderNo(tradeOrderDto.getMerchantOrderNo());
		tradeOrder.confirm();
		tradeOrderRepository.update(tradeOrder);
		CapitalAccount transferToAccount = capitalAccountRepository
				.findByUserId(tradeOrderDto.getOppositeUserId());
		transferToAccount.transferTo(tradeOrderDto.getAmount()); // 增加收款方资金帐户资金
		capitalAccountRepository.save(transferToAccount);
	}

	@Transactional
	public void cancelRecord(TransactionContext transactionContext,
			CapitalTradeOrderDto tradeOrderDto) {
		LOG.debug("==>capital cancel record called");
		TradeOrder tradeOrder = tradeOrderRepository
				.findByMerchantOrderNo(tradeOrderDto.getMerchantOrderNo());
		if (null != tradeOrder && "DRAFT".equals(tradeOrder.getStatus())) {
			tradeOrder.cancel();
			tradeOrderRepository.update(tradeOrder);
			CapitalAccount capitalAccount = capitalAccountRepository
					.findByUserId(tradeOrderDto.getSelfUserId());
			capitalAccount.cancelTransfer(tradeOrderDto.getAmount()); // 对扣减付款方资金帐户资金进行反操作
			capitalAccountRepository.save(capitalAccount);
		}
	}
}