package t5750.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	@RequestMapping(value = { "/index", "/" })
	public String index() {
		return "index";
	}
}