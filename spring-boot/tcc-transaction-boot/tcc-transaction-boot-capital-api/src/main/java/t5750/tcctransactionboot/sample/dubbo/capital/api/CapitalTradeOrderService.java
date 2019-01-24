package t5750.tcctransactionboot.sample.dubbo.capital.api;

import org.mengyun.tcctransaction.api.Compensable;
import t5750.tcctransactionboot.sample.dubbo.capital.api.dto.CapitalTradeOrderDto;

/**
 */
public interface CapitalTradeOrderService {

    @Compensable
    public String record(CapitalTradeOrderDto tradeOrderDto);

}
