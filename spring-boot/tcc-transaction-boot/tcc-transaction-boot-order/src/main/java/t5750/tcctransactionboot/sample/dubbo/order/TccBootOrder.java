package t5750.tcctransactionboot.sample.dubbo.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 */
@SpringBootApplication
@MapperScan("t5750.tcctransactionboot.sample.dubbo.order.infrastructure.dao")
public class TccBootOrder {
	public static void main(String[] args) {
		SpringApplication.run(TccBootOrder.class, args);
	}
}
