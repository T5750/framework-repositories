package t5750.derby;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DerbyApplication {
	public static void main(String[] args) {
		System.setProperty("derby.stream.error.file",
				System.getProperty("user.dir") + File.separator + "databases"
						+ File.separator + "derby" + File.separator
						+ "derby.log");
		SpringApplication.run(DerbyApplication.class, args);
	}
}
