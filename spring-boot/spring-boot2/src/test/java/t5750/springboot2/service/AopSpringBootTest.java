package t5750.springboot2.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import t5750.springboot2.service.aop.DomainService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AopSpringBootTest {
	@Autowired
	private DomainService service;

	@Test
	public void testGetDomainObjectById() {
		service.getDomainObjectById(10L);
	}
}