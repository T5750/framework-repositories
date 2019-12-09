package t5750.springboot2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
	private String to;

	@GetMapping("/send")
	public HttpStatus send() {
		emailService.sendMail(to, "Hi", "Ho ho ho");
		return HttpStatus.OK;
	}

	@GetMapping("/sendTemplate")
	public HttpStatus sendTemplate() {
		emailService.sendPreConfiguredMail("Ho ho ho");
		return HttpStatus.OK;
	}

	@GetMapping("/sendWithAttachment")
	public HttpStatus sendWithAttachment() {
		String fileToAttach = "static/img/T5750_32.png";
		emailService.sendMailWithAttachment(to, "Send Mail With Attachment",
				"Ho ho ho", fileToAttach);
		return HttpStatus.OK;
	}

	@GetMapping("/sendWithInlineResources")
	public HttpStatus sendWithInlineResources() {
		String fileToAttach = "static/img/T5750_32.png";
		emailService.sendMailWithInlineResources(to,
				"Send Mail With Inline Resources", fileToAttach);
		return HttpStatus.OK;
	}
}
