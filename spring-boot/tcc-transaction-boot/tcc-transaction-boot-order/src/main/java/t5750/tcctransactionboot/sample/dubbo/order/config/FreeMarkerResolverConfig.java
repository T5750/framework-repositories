package t5750.tcctransactionboot.sample.dubbo.order.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.utility.HtmlEscape;
import freemarker.template.utility.XmlEscape;

/**
 * appcontext-web-servlet.xml
 */
@Configuration
public class FreeMarkerResolverConfig extends
		FreeMarkerAutoConfiguration.FreeMarkerWebConfiguration {
	@Override
	@Bean
	@ConditionalOnMissingBean(FreeMarkerConfig.class)
	public FreeMarkerConfigurer freeMarkerConfigurer() {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		applyProperties(configurer);
		Map<String, Object> variables = new HashMap<>(2);
		variables.put("xml_escape", new XmlEscape());
		variables.put("html_escape", new HtmlEscape());
		configurer.setFreemarkerVariables(variables);
		return configurer;
	}
}
