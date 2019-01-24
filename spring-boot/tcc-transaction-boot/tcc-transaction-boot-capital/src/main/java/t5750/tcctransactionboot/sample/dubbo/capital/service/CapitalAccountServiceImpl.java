package t5750.tcctransactionboot.sample.dubbo.capital.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import t5750.tcctransactionboot.sample.dubbo.capital.api.CapitalAccountService;
import t5750.tcctransactionboot.sample.dubbo.capital.domain.repository.CapitalAccountRepository;

import com.alibaba.dubbo.config.annotation.Service;

/**
 */
// @Service("capitalAccountService")
@Service(timeout = 60000, retries = 0)
public class CapitalAccountServiceImpl implements CapitalAccountService {
	@Autowired
	CapitalAccountRepository capitalAccountRepository;

	@Override
	public BigDecimal getCapitalAccountByUserId(long userId) {
		return capitalAccountRepository.findByUserId(userId).getBalanceAmount();
	}
}
