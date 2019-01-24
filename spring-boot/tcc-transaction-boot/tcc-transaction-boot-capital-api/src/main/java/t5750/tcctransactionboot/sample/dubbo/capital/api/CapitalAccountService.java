package t5750.tcctransactionboot.sample.dubbo.capital.api;

import java.math.BigDecimal;

/**
 */
public interface CapitalAccountService {

    BigDecimal getCapitalAccountByUserId(long userId);
}
