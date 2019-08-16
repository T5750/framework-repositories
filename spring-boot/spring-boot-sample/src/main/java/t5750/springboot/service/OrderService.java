package t5750.springboot.service;

import java.util.Collection;

import t5750.springboot.model.Product;

public interface OrderService {
	Collection<Product> getProducts();
}
