package t5750.springboot2.service.aop;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class DomainService {
	public Object getDomainObjectById(Long id) {
		try {
			Thread.sleep(new Random().nextInt(2000));
		} catch (InterruptedException e) {
			//do some logging
		}
		return id;
	}
}