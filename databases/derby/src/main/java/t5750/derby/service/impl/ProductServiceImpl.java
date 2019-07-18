package t5750.derby.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import t5750.derby.command.ProductForm;
import t5750.derby.converter.ProductFormToProduct;
import t5750.derby.domain.Product;
import t5750.derby.repository.ProductRepository;
import t5750.derby.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	private ProductRepository productRepository;
	private ProductFormToProduct productFormToProduct;

	@Autowired
	public ProductServiceImpl(ProductRepository productRepository,
			ProductFormToProduct productFormToProduct) {
		this.productRepository = productRepository;
		this.productFormToProduct = productFormToProduct;
	}

	@Override
	public List<Product> listAll() {
		List<Product> products = new ArrayList<>();
		productRepository.findAll().forEach(products::add); // fun with Java 8
		return products;
	}

	@Override
	public Product getById(Long id) {
		return productRepository.findOne(id);
	}

	@Override
	public Product saveOrUpdate(Product product) {
		productRepository.save(product);
		return product;
	}

	@Override
	public void delete(Long id) {
		productRepository.delete(id);
	}

	@Override
	public Product saveOrUpdateProductForm(ProductForm productForm) {
		Product savedProduct = saveOrUpdate(productFormToProduct
				.convert(productForm));
		System.out.println("Saved Product Id: " + savedProduct.getId());
		return savedProduct;
	}
}
