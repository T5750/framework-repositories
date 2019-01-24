package t5750.tcctransactionboot.sample.dubbo.capital;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 */
@SpringBootApplication
@MapperScan("t5750.tcctransactionboot.sample.dubbo.capital.infrastructure.dao")
public class TccBootCapital {
	public static void main(String[] args) {
		SpringApplication.run(TccBootCapital.class, args);
	}
}
