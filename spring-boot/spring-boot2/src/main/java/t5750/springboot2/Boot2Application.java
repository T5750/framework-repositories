package t5750.springboot2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Boot2Application extends SpringBootServletInitializer {
	private static final Logger LOGGER = LogManager
			.getLogger(Boot2Application.class);

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Boot2Application.class,
				args);
		LOGGER.info("Info level log message");
		LOGGER.debug("Debug level log message");
		LOGGER.error("Error level log message");
	}
}