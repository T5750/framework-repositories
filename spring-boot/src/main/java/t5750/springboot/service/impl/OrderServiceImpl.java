package t5750.springboot.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import t5750.springboot.model.Product;
import t5750.springboot.service.OrderService;
import t5750.springboot.service.ProductService;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	ProductService productService;

	@Override
	public Collection<Product> getProducts() {
		return productService.getProducts();
	}
}