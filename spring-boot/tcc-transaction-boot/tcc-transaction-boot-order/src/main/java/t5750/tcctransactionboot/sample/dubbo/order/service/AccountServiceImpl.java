package t5750.tcctransactionboot.sample.dubbo.order.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import t5750.tcctransactionboot.sample.dubbo.capital.api.CapitalAccountService;
import t5750.tcctransactionboot.sample.dubbo.redpacket.api.RedPacketAccountService;

import com.alibaba.dubbo.config.annotation.Reference;

/**
 */
@Service("accountService")
public class AccountServiceImpl {
	@Reference
	RedPacketAccountService redPacketAccountService;
	@Reference
	CapitalAccountService capitalAccountService;

	public BigDecimal getRedPacketAccountByUserId(long userId) {
		return redPacketAccountService.getRedPacketAccountByUserId(userId);
	}

	public BigDecimal getCapitalAccountByUserId(long userId) {
		return capitalAccountService.getCapitalAccountByUserId(userId);
	}
}
