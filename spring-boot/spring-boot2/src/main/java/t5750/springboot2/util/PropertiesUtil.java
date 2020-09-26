package t5750.springboot2.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PropertiesUtil {
	private static final String PROPERTY_NAME = "application.properties";

	public static String getProperty(String key) throws IOException {
		return getProperty(PROPERTY_NAME, key);
	}

	public static String getProperty(String propertyName, String key)
			throws IOException {
		Properties appProps = PropertiesLoaderUtils
				.loadProperties(new ClassPathResource(propertyName));
		String value = appProps.getProperty(key);
		return value;
	}
}
