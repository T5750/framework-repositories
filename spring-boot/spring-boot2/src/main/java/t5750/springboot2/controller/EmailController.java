package t5750.springboot2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import t5750.springboot2.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {
	@Autowired
	private EmailService emailService;
	@Value("${spring.mail.username}")
	private String from;

	@GetMapping("/send")
	public void sendM() {
		emailService.sendMail(from, "Hi", "Ho ho ho");
		emailService.sendPreConfiguredMail("Ho ho ho");
	}
}
