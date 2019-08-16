package t5750.springboot.mockito;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import t5750.springboot.model.Product;
import t5750.springboot.service.OrderService;
import t5750.springboot.service.ProductService;

@SpringBootTest
@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
public class MockitoDemoApplicationTests {
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;

	@Test
	public void whenUserIdIsProvided_thenRetrievedNameIsCorrect() {
		Mockito.when(productService.getProducts()).thenReturn(null);
		Collection<Product> products = orderService.getProducts();
		Assert.assertEquals(null, products);
	}
}