package t5750.springboot.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import t5750.springboot.exception.ProductNotfoundException;
import t5750.springboot.model.Product;
import t5750.springboot.util.Globals;

@RestController
public class ConsumeWebServiceController {
	@Autowired
	RestTemplate restTemplate;

	@RequestMapping(value = "/template/products")
	public String getProductList(HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return restTemplate.exchange(Globals.getBasePath(request) + "products",
				HttpMethod.GET, entity, String.class).getBody();
	}

	@RequestMapping(value = "/template/products", method = RequestMethod.POST)
	public String createProducts(@RequestBody Product product,
			HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Product> entity = new HttpEntity<Product>(product, headers);
		return restTemplate.exchange(Globals.getBasePath(request) + "products",
				HttpMethod.POST, entity, String.class).getBody();
	}

	@RequestMapping(value = "/template/products/{id}", method = RequestMethod.PUT)
	public String updateProduct(@PathVariable("id") String id,
			@RequestBody Product product, HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Product> entity = new HttpEntity<Product>(product, headers);
		String result = "";
		try {
			result = restTemplate.exchange(
					Globals.getBasePath(request) + "products/" + id,
					HttpMethod.PUT, entity, String.class).getBody();
		} catch (HttpClientErrorException e) {
			throw new ProductNotfoundException();
		}
		return result;
	}

	@RequestMapping(value = "/template/products/{id}", method = RequestMethod.DELETE)
	public String deleteProduct(@PathVariable("id") String id,
			HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Product> entity = new HttpEntity<Product>(headers);
		return restTemplate.exchange(
				Globals.getBasePath(request) + "products/" + id,
				HttpMethod.DELETE, entity, String.class).getBody();
	}
}