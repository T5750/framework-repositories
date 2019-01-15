package t5750.tcctransaction.sample.dubbo.capital.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import t5750.tcctransaction.sample.dubbo.capital.api.CapitalAccountService;
import t5750.tcctransaction.sample.dubbo.capital.domain.repository.CapitalAccountRepository;

/**
 */
@Service("capitalAccountService")
public class CapitalAccountServiceImpl implements CapitalAccountService {
	@Autowired
	CapitalAccountRepository capitalAccountRepository;

	@Override
	public BigDecimal getCapitalAccountByUserId(long userId) {
		return capitalAccountRepository.findByUserId(userId).getBalanceAmount();
	}
}