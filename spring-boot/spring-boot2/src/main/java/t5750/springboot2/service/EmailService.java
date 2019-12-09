package t5750.springboot2.service;

public interface EmailService {
	void sendMail(String to, String subject, String body);

	void sendPreConfiguredMail(String message);

	void sendMailWithAttachment(String to, String subject, String body,
			String fileToAttach);

	void sendMailWithInlineResources(String to, String subject,
			String fileToAttach);
}
