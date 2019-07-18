package t5750.derby.service;

import java.util.List;

import t5750.derby.command.ProductForm;
import t5750.derby.domain.Product;

public interface ProductService {
	List<Product> listAll();

	Product getById(Long id);

	Product saveOrUpdate(Product product);

	void delete(Long id);

	Product saveOrUpdateProductForm(ProductForm productForm);
}
