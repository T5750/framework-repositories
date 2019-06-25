package t5750.springbootssl.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecuredServerController {
	@RequestMapping("/secured")
	public String secured() {
		System.out.println("Inside secured()");
		return "Hello user !!! : " + new Date();
	}
}
