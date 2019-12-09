package t5750.springboot2.service.impl;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import t5750.springboot2.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private SimpleMailMessage preConfiguredMessage;
	@Value("${spring.mail.username}")
	private String from;

	/**
	 * This method will send compose and send the message
	 */
	@Override
	public void sendMail(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);
	}

	/**
	 * This method will send a pre-configured message
	 */
	@Override
	public void sendPreConfiguredMail(String message) {
		SimpleMailMessage mailMessage = new SimpleMailMessage(
				preConfiguredMessage);
		mailMessage.setText(message);
		mailSender.send(mailMessage);
	}

	@Override
	public void sendMailWithAttachment(String to, String subject, String body,
			String fileToAttach) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				// mimeMessage.setRecipient(Message.RecipientType.TO,
				// new InternetAddress(to));
				// mimeMessage.setFrom(new InternetAddress(from));
				// mimeMessage.setSubject(subject);
				// mimeMessage.setText(body);
				// FileSystemResource file = new FileSystemResource(
				// new File(fileToAttach));
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
						true);
				helper.setFrom(from);
				helper.setTo(to);
				helper.setSubject(subject);
				helper.setText(body);
				Resource resource = new ClassPathResource(fileToAttach);
				File file = resource.getFile();
				helper.addAttachment(file.getName(), file);
			}
		};
		try {
			mailSender.send(preparator);
		} catch (MailException ex) {
			// simply log it and go on...
			System.err.println(ex.getMessage());
		}
	}

	@Override
	public void sendMailWithInlineResources(String to, String subject,
			String fileToAttach) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				// mimeMessage.setRecipient(Message.RecipientType.TO,
				// new InternetAddress(to));
				// mimeMessage.setFrom(new InternetAddress(from));
				// mimeMessage.setSubject(subject);
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
						true);
				helper.setFrom(from);
				helper.setTo(to);
				helper.setSubject(subject);
				Resource resource = new ClassPathResource(fileToAttach);
				String resourceName = resource.getFilename();
				helper.setText("<html><body><img src='cid:" + resourceName
						+ "'></body></html>", true);
				helper.addInline(resourceName, resource);
			}
		};
		try {
			mailSender.send(preparator);
		} catch (MailException ex) {
			// simply log it and go on...
			System.err.println(ex.getMessage());
		}
	}
}
