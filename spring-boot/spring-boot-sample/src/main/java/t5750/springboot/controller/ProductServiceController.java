package t5750.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import t5750.springboot.model.Product;
import t5750.springboot.service.ProductService;

@RestController
public class ProductServiceController {
	@Autowired
	ProductService productService;

	@RequestMapping(value = "/products")
	public ResponseEntity<Object> getProduct() {
		return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
	}

	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public ResponseEntity<Object> createProduct(@RequestBody Product product) {
		productService.createProduct(product);
		return new ResponseEntity<>("Product is created successfully",
				HttpStatus.CREATED);
	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateProduct(@PathVariable("id") String id,
			@RequestBody Product product) {
		productService.updateProduct(id, product);
		return new ResponseEntity<>("Product is updated successsfully",
				HttpStatus.OK);
	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable("id") String id) {
		productService.deleteProduct(id);
		return new ResponseEntity<>("Product is deleted successsfully",
				HttpStatus.OK);
	}

	/**
	 * CrossOrigin or CorsConfig
	 */
	@RequestMapping(value = "/products-cors")
	// @CrossOrigin(origins = Globals.SPRING_BOOT_SECURITY_ORIGIN)
	public ResponseEntity<Object> getProductCors() {
		return getProduct();
	}
}