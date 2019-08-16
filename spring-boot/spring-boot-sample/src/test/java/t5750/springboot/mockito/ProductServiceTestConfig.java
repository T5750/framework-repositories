package t5750.springboot.mockito;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import t5750.springboot.service.ProductService;

@Profile("dev")
@Configuration
public class ProductServiceTestConfig {
	@Bean
	@Primary
	public ProductService productService() {
		return Mockito.mock(ProductService.class);
	}
}