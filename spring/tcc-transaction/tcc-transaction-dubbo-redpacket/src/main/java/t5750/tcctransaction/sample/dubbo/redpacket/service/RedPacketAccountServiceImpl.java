package t5750.tcctransaction.sample.dubbo.redpacket.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import t5750.tcctransaction.sample.dubbo.redpacket.api.RedPacketAccountService;
import t5750.tcctransaction.sample.dubbo.redpacket.domain.repository.RedPacketAccountRepository;

/**
 */
@Service("redPacketAccountService")
public class RedPacketAccountServiceImpl implements RedPacketAccountService {
	@Autowired
	RedPacketAccountRepository redPacketAccountRepository;

	@Override
	public BigDecimal getRedPacketAccountByUserId(long userId) {
		return redPacketAccountRepository.findByUserId(userId)
				.getBalanceAmount();
	}
}