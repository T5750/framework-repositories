package t5750.springboot.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring Boot – Get all loaded beans with Class Type Information
 */
@RestController
public class BeanController {
	@Autowired
	private ApplicationContext appContext;

	@RequestMapping(value = "/loadedBeans")
	public Map loadedBeans() {
		String[] beans = appContext.getBeanDefinitionNames();
		Map map = new HashMap(beans.length);
		String beanClass = "";
		Arrays.sort(beans);
		for (String bean : beans) {
			beanClass = appContext.getBean(bean).getClass().toString();
			System.out.println(bean + " of Type :: " + beanClass);
			map.put(bean, beanClass);
		}
		return map;
	}
}