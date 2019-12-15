package t5750.springboot2security.controller;

import org.springframework.web.bind.annotation.*;

import t5750.springboot2security.customize.RestfulClient;
import t5750.springboot2security.model.Customer;

@RestController
@RequestMapping(path = "/customer")
public class CustomerController {
	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public String post(@RequestBody Customer cust) {
		System.out.println("/POST request, cust: " + cust.toString());
		return "/Post Successful!";
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public Customer get(@RequestParam("id") long id,
			@RequestParam("name") String name, @RequestParam("age") int age) {
		String info = String.format("/GET info: id=%d, name=%s, age=%d", id,
				name, age);
		System.out.println(info);
		return new Customer(id, name, age);
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public Customer test() {
		RestfulClient restfulClient = new RestfulClient("admin", "password");
		/**
		 * POST ENTITY
		 */
		Customer customer = restfulClient.postEntity();
		/**
		 * GET ENTITY
		 */
		restfulClient.getEntity();
		return customer;
	}
}