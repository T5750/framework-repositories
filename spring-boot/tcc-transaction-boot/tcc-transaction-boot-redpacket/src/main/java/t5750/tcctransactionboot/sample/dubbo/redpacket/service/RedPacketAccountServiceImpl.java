package t5750.tcctransactionboot.sample.dubbo.redpacket.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import t5750.tcctransactionboot.sample.dubbo.redpacket.api.RedPacketAccountService;
import t5750.tcctransactionboot.sample.dubbo.redpacket.domain.repository.RedPacketAccountRepository;

import com.alibaba.dubbo.config.annotation.Service;

/**
 */
// @Service("redPacketAccountService")
@Service(timeout = 60000, retries = 0)
public class RedPacketAccountServiceImpl implements RedPacketAccountService {
	@Autowired
	RedPacketAccountRepository redPacketAccountRepository;

	@Override
	public BigDecimal getRedPacketAccountByUserId(long userId) {
		return redPacketAccountRepository.findByUserId(userId)
				.getBalanceAmount();
	}
}
