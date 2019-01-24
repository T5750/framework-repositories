package t5750.tcctransactionboot.sample.dubbo.redpacket.service;

import java.util.Calendar;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.mengyun.tcctransaction.api.Compensable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import t5750.tcctransactionboot.dubbo.context.DubboTransactionContextEditor;
import t5750.tcctransactionboot.sample.dubbo.redpacket.api.RedPacketTradeOrderService;
import t5750.tcctransactionboot.sample.dubbo.redpacket.api.dto.RedPacketTradeOrderDto;
import t5750.tcctransactionboot.sample.dubbo.redpacket.domain.entity.RedPacketAccount;
import t5750.tcctransactionboot.sample.dubbo.redpacket.domain.entity.TradeOrder;
import t5750.tcctransactionboot.sample.dubbo.redpacket.domain.repository.RedPacketAccountRepository;
import t5750.tcctransactionboot.sample.dubbo.redpacket.domain.repository.TradeOrderRepository;

import com.alibaba.dubbo.config.annotation.Service;

/**
 */
// @Service("redPacketTradeOrderService")
@Service(timeout = 60000, retries = 0)
public class RedPacketTradeOrderServiceImpl implements
		RedPacketTradeOrderService {
	@Autowired
	RedPacketAccountRepository redPacketAccountRepository;
	@Autowired
	TradeOrderRepository tradeOrderRepository;

	@Override
	@Compensable(confirmMethod = "confirmRecord", cancelMethod = "cancelRecord", transactionContextEditor = DubboTransactionContextEditor.class)
	@Transactional
	public String record(RedPacketTradeOrderDto tradeOrderDto) {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		System.out.println("red packet try record called. time seq:"
				+ DateFormatUtils.format(Calendar.getInstance(),
						"yyyy-MM-dd HH:mm:ss"));
		TradeOrder foundTradeOrder = tradeOrderRepository
				.findByMerchantOrderNo(tradeOrderDto.getMerchantOrderNo());
		// check if trade order has been recorded, if yes, return success
		// directly.
		if (foundTradeOrder == null) {
			TradeOrder tradeOrder = new TradeOrder(
					tradeOrderDto.getSelfUserId(),
					tradeOrderDto.getOppositeUserId(),
					tradeOrderDto.getMerchantOrderNo(),
					tradeOrderDto.getAmount());
			try {
				tradeOrderRepository.insert(tradeOrder);
				RedPacketAccount transferFromAccount = redPacketAccountRepository
						.findByUserId(tradeOrderDto.getSelfUserId());
				transferFromAccount.transferFrom(tradeOrderDto.getAmount());
				redPacketAccountRepository.save(transferFromAccount);
			} catch (DataIntegrityViolationException e) {
				// this exception may happen when insert trade order
				// concurrently, if happened, ignore this insert operation.
			}
		}
		return "success";
	}

	@Transactional
	public void confirmRecord(RedPacketTradeOrderDto tradeOrderDto) {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		System.out.println("red packet confirm record called. time seq:"
				+ DateFormatUtils.format(Calendar.getInstance(),
						"yyyy-MM-dd HH:mm:ss"));
		TradeOrder tradeOrder = tradeOrderRepository
				.findByMerchantOrderNo(tradeOrderDto.getMerchantOrderNo());
		// check if the trade order status is DRAFT, if yes, return directly,
		// ensure idempotency.
		if (tradeOrder != null && tradeOrder.getStatus().equals("DRAFT")) {
			tradeOrder.confirm();
			tradeOrderRepository.update(tradeOrder);
			RedPacketAccount transferToAccount = redPacketAccountRepository
					.findByUserId(tradeOrderDto.getOppositeUserId());
			transferToAccount.transferTo(tradeOrderDto.getAmount());
			redPacketAccountRepository.save(transferToAccount);
		}
	}

	@Transactional
	public void cancelRecord(RedPacketTradeOrderDto tradeOrderDto) {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		System.out.println("red packet cancel record called. time seq:"
				+ DateFormatUtils.format(Calendar.getInstance(),
						"yyyy-MM-dd HH:mm:ss"));
		TradeOrder tradeOrder = tradeOrderRepository
				.findByMerchantOrderNo(tradeOrderDto.getMerchantOrderNo());
		// check if the trade order status is DRAFT, if yes, return directly,
		// ensure idempotency.
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
