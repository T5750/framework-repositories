# Spring Boot 2 Mail

## Maven
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

## SMTP configuration
### Gmail
```
debug=true
spring.mail.host=smtp.gmail.com
spring.mail.port=25
spring.mail.username=admin@gmail.com
spring.mail.password=xxxxxx
# Other properties
spring.mail.properties.mail.debug=true
spring.mail.properties.mail.transport.protocol=smtp
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.connectiontimeout=5000
spring.mail.properties.mail.smtp.timeout=5000
spring.mail.properties.mail.smtp.writetimeout=5000
# TLS , port 587
spring.mail.properties.mail.smtp.starttls.enable=true
# SSL, post 465
#spring.mail.properties.mail.smtp.socketFactory.port = 465
#spring.mail.properties.mail.smtp.socketFactory.class = javax.net.ssl.SSLSocketFactory
```
>To be able to use two factor authentication as well as keep the “allow less secure apps” option off, it is highly recommended to use an app password instead of the Gmail password.
>Refer: https://support.google.com/accounts/answer/185833

### Outlook
```
spring.mail.host=smtp-mail.outlook.com
spring.mail.port=587
spring.mail.username=outlookuserid@outlook.com
spring.mail.password=xxxxxx
spring.mail.properties.mail.protocol=smtp
spring.mail.properties.mail.tls=true
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.ssl.trust=smtp-mail.outlook.com
```

### AWS SES
Find relevant configuration from your AWS settings page. [[Read More](https://docs.aws.amazon.com/ses/latest/DeveloperGuide/sending-email.html)]
```
spring.mail.host=email-smtp.us-east-1.amazonaws.com
spring.mail.port=465
spring.mail.username=xxxxxxxxxxx
spring.mail.password=xxxxxxxxxxx
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
spring.mail.properties.mail.smtp.ssl.enavle=true
spring.mail.properties.mail.protocol=smtps
spring.mail.properties.mail.smtps.auth=true
```

## Pre-configured email templates
```java
@Configuration
public class EmailConfig {
    @Bean
    public SimpleMailMessage emailTemplate() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("user@gmail.com");
        message.setFrom("admin@gmail.com");
        message.setSubject("Important email");
        message.setText("FATAL - Application crash. Save your job !!");
        return message;
    }
}
```

## Send simple email
```java
@Service("emailService")
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SimpleMailMessage preConfiguredMessage;

    /**
     * This method will send compose and send the message
     * */
    public void sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    /**
     * This method will send a pre-configured message
     * */
    public void sendPreConfiguredMail(String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage(preConfiguredMessage);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }
}
```

## Send email with attachment
```
@Autowired
private JavaMailSender mailSender;

public void sendMailWithAttachment(String to, String subject, String body, String fileToAttach) {
    MimeMessagePreparator preparator = new MimeMessagePreparator() {
        public void prepare(MimeMessage mimeMessage) throws Exception {
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setFrom(new InternetAddress("admin@gmail.com"));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(body);
            FileSystemResource file = new FileSystemResource(new File(fileToAttach));
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.addAttachment("logo.jpg", file);
        }
    };
    try {
        mailSender.send(preparator);
    }
    catch (MailException ex) {
        // simply log it and go on...
        System.err.println(ex.getMessage());
    }
}
```

## Send email with inline images
Rich text include media content in between text content. To do so in emails, we have to use `MimeMessageHelper`‘s `addInline()` method.
```
@Autowired
private JavaMailSender mailSender;

public void sendMailWithInlineResources(String to, String subject, String fileToAttach) {
    MimeMessagePreparator preparator = new MimeMessagePreparator() {
        public void prepare(MimeMessage mimeMessage) throws Exception {
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setFrom(new InternetAddress("admin@gmail.com"));
            mimeMessage.setSubject(subject);
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setText("<html><body><img src='cid:identifier1234'></body></html>", true);
            FileSystemResource res = new FileSystemResource(new File(fileToAttach));
            helper.addInline("identifier1234", res);
        }
    };
    try {
        mailSender.send(preparator);
    }
    catch (MailException ex) {
        // simply log it and go on...
        System.err.println(ex.getMessage());
    }
}
```

## References
- [Spring boot – Send email with attachment](https://howtodoinjava.com/spring-boot2/send-email-with-attachment/)