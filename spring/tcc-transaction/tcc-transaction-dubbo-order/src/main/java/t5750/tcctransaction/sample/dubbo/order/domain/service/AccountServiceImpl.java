package t5750.tcctransaction.sample.dubbo.order.domain.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import t5750.tcctransaction.sample.dubbo.capital.api.CapitalAccountService;
import t5750.tcctransaction.sample.dubbo.redpacket.api.RedPacketAccountService;

/**
 */
@Service("accountService")
public class AccountServiceImpl {
	@Autowired
	RedPacketAccountService redPacketAccountService;
	@Autowired
	CapitalAccountService capitalAccountService;

	public BigDecimal getRedPacketAccountByUserId(long userId) {
		return redPacketAccountService.getRedPacketAccountByUserId(userId);
	}

	public BigDecimal getCapitalAccountByUserId(long userId) {
		return capitalAccountService.getCapitalAccountByUserId(userId);
	}
}