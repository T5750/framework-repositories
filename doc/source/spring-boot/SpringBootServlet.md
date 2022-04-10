# Spring Boot Servlet

## 注册方式
### servlet3.0注解+@ServletComponentScan
一系列以`@Web*`开头的注解：`@WebServlet`，`@WebFilter`，`@WebListener`
```
@WebServlet("/hello")
public class HelloWorldServlet extends HttpServlet{}
```
```
@WebFilter("/hello/*")
public class HelloWorldFilter implements Filter {}
```
```
@SpringBootApplication
@ServletComponentScan
public class SpringBootServletApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootServletApplication.class, args);
	}
}
```

### RegistrationBean
```
@Bean
public ServletRegistrationBean helloWorldServlet() {
	ServletRegistrationBean helloWorldServlet = new ServletRegistrationBean();
	myServlet.addUrlMappings("/hello");
	myServlet.setServlet(new HelloWorldServlet());
	return helloWorldServlet;
}

@Bean
public FilterRegistrationBean helloWorldFilter() {
	FilterRegistrationBean helloWorldFilter = new FilterRegistrationBean();
	myFilter.addUrlPatterns("/hello/*");
	myFilter.setFilter(new HelloWorldFilter());
	return helloWorldFilter;
}
```

![RegistrationBean-min-min](https://s0.wailian.download/2019/07/01/RegistrationBean-min-min.png)

![TomcatStarter-min-min](https://s0.wailian.download/2019/07/01/TomcatStarter-min-min.png)

#### EmbeddedWebApplicationContext加载流程总结
- `EmbeddedWebApplicationContext`的`onRefresh`方法触发配置了一个匿名的`ServletContextInitializer`
- 匿名的`ServletContextInitializer`的`onStartup`方法会去容器中搜索到所有的`RegisterBean`，并按照顺序加载到`ServletContext`中
- 匿名的`ServletContextInitializer`最终传递给`TomcatStarter`，由`TomcatStarter`的`onStartup`方法去触发`ServletContextInitializer`的`onStartup`方法，最终完成装配

### Custom
```
@Configuration
public class CustomServletContextInitializer implements ServletContextInitializer {
	private final static String JAR_HELLO_URL = "/hello";
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		System.out.println("创建 helloWorldServlet...");
		ServletRegistration.Dynamic servlet = servletContext.addServlet(HelloWorldServlet.class.getSimpleName(), HelloWorldServlet.class);
		servlet.addMapping(JAR_HELLO_URL);
		System.out.println("创建 helloWorldFilter...");
		FilterRegistration.Dynamic filter = servletContext.addFilter(HelloWorldFilter.class.getSimpleName(), HelloWorldFilter.class);
		EnumSet<DispatcherType> dispatcherTypes = EnumSet.allOf(DispatcherType.class);
		dispatcherTypes.add(DispatcherType.REQUEST);
		dispatcherTypes.add(DispatcherType.FORWARD);
		filter.addMappingForUrlPatterns(dispatcherTypes, true, JAR_HELLO_URL);
	}
}
```

## References
- [Spring揭秘--寻找遗失的web.xml](https://www.cnkirito.moe/servlet-explore/)