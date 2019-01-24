package t5750.tcctransactionboot.sample.dubbo.redpacket;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 */
@SpringBootApplication
@MapperScan("t5750.tcctransactionboot.sample.dubbo.redpacket.infrastructure.dao")
public class TccBootRedpacket {
	public static void main(String[] args) {
		SpringApplication.run(TccBootRedpacket.class, args);
	}
}
