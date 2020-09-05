package t5750.springbootadmin.util;

import java.util.Properties;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class YmlUtil {
	private static final String PROPERTY_NAME = "application.yml";

	public static Object getProperty(Object key) {
		Resource resource = new ClassPathResource(PROPERTY_NAME);
		Properties properties = null;
		try {
			YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
			yamlFactory.setResources(resource);
			properties = yamlFactory.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return properties.get(key);
	}
}
