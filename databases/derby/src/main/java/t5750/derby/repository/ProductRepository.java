package t5750.derby.repository;

import org.springframework.data.repository.CrudRepository;

import t5750.derby.domain.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
