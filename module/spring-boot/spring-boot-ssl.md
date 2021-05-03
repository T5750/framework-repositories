# Spring Boot SSL [https]

## SSL Configuration for Impatients
### Spring boot HTTPS Config
```
server.port=8443
server.ssl.key-alias=selfsigned_localhost_springbootssl
server.ssl.key-password=changeit
server.ssl.key-store=classpath:spring-boot-ssl.jks
server.ssl.key-store-provider=SUN
server.ssl.key-store-type=JKS
```

### Redirect from HTTP to HTTPS
```
private Connector redirectConnector() {
	Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
	connector.setScheme("http");
	connector.setPort(8080);
	connector.setSecure(false);
	connector.setRedirectPort(8443);
	return connector;
}
```

## Terminology
- **SSL** – stands for Secure Sockets Layer. It is the industry standard protocol for keeping an internet connection secure by safeguarding all sensitive data that is being sent between two systems, preventing hackers from reading and modifying any information transferred.
- **TLS** – (Transport Layer Security) is an updated, more secure, version of SSL. It adds more features. Today, certificates provided by certificate authorities are based on TLS only. But regarding secured communication over network, the term SSL is still common as it is the old and just become popular among community.
- **HTTPS** – (Hyper Text Transfer Protocol Secure) appears in the URL when a website is secured by an SSL certificate. It is the secured version of HTTP protocol.
- **Truststore and Keystore** – Those are used to store SSL certificates in Java but there is little difference between them. `truststore` is used to store public certificates while `keystore` is used to store private certificates of client or server.

## Create your own self signed SSL certificate
To get SSL digital certificate for our application we have two options –
1. to create a self-signed certificate
1. to obtain SSL certificate from certification authority(CA) we call it CA certificate.

```
keytool -genkey -alias selfsigned_localhost_springbootssl -keyalg RSA -keysize 2048 -validity 700 -keypass changeit -storepass changeit -keystore spring-boot-ssl.jks
```

- `-genkey` – is the keytool command to generate the certificate, actually keytool is a multipurpose and robust tool which has several options
- `-alias selfsigned_localhost_springbootssl` – indicates the alias of the certificate, which is used by SSL/TLS layer
- `-keyalg RSA -keysize 2048 -validity 700` – are self descriptive parameters indicating the crypto algorithm, keysize and certificate validity.
- `-keypass changeit -storepass changeit` – are the passwords of our truststore and keystore
- `-keystore spring-boot-ssl.jks` – is the actual keystore where the certificate and public/private key will be stored. Here we are using [JKS](https://en.wikipedia.org/wiki/Keystore) fromat – Java Key Store, there are other formats as well for keystore.

### keytool-commands
```
keytool -list -keystore spring-boot-ssl.jks
keytool -list -rfc -keystore spring-boot-ssl.jks
```

## Create Spring-boot project and configure SSL
- `BootSslApplication`
- https://localhost:8443/secured

## References
- [Spring Boot SSL [https] Example](https://howtodoinjava.com/spring-boot/spring-boot-ssl-https-example/#create-ssl-cert)