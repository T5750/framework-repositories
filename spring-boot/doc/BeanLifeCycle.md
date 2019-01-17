# Spring Boot

## Spring BeanLifeCycle
![BeanLifeCycle-min](https://www.wailian.work/images/2018/11/05/BeanLifeCycle-min.png)

Spring Bean的生命周期：
- Bean容器找到配置文件中Spring Bean的定义。
- Bean容器利用Java Reflection API创建一个Bean的实例。
- 如果涉及到一些属性值，利用`set`方法设置一些属性值。
- 如果Bean实现了`BeanNameAware`接口，调用`setBeanName()`方法，传入Bean的名字。
- 如果Bean实现了`BeanClassLoaderAware`接口，调用`setBeanClassLoader()`方法，传入`ClassLoader`对象的实例。
- 如果Bean实现了`BeanFactoryAware`接口，调用`setBeanFactory()`方法，传入`BeanFactory`对象的实例。
- 与上面的类似，如果实现了其他`*Aware`接口，就调用相应的方法。
- 如果有和加载这个Bean的Spring容器相关的`BeanPostProcessor`对象，执行`postProcessBeforeInitialization()`方法
- 如果Bean实现了`InitializingBean`接口，执行`afterPropertiesSet()`方法。
- 如果Bean在配置文件中的定义包含`init-method`属性，执行指定的方法。
- 如果有和加载这个Bean的Spring容器相关的`BeanPostProcessor`对象，执行`postProcessAfterInitialization()`方法
- 当要销毁Bean的时候，如果Bean实现了`DisposableBean`接口，执行`destroy()`方法。
- 当要销毁Bean的时候，如果Bean在配置文件中的定义包含`destroy-method`属性，执行指定的方法。

## Results
- `BeanLifeCycleServiceTest`

```
Spring容器初始化
=====================================
调用BeanLifeCycleService无参构造函数
BeanLifeCycleService中利用set方法设置属性值
调用setBeanName:: Bean Name defined in context=beanLifeCycleService
调用setBeanClassLoader,ClassLoader Name = sun.misc.Launcher$AppClassLoader
调用setBeanFactory,setBeanFactory:: BeanLifeCycle bean singleton=true
调用setEnvironment
调用setResourceLoader:: Resource File Name=application.properties
调用setApplicationEventPublisher
调用setApplicationContext:: Bean Definition Names=[org.springframework.context.annotation.internalConfigurationAnnotationProcessor, org.springframework.context.annotation.internalAutowiredAnnotationProcessor, org.springframework.context.annotation.internalRequiredAnnotationProcessor, org.springframework.context.annotation.internalCommonAnnotationProcessor, org.springframework.context.event.internalEventListenerProcessor, org.springframework.context.event.internalEventListenerFactory, org.springframework.boot.test.web.client.TestRestTemplate, org.springframework.boot.test.mock.mockito.MockitoPostProcessor$SpyPostProcessor, org.springframework.boot.test.mock.mockito.MockitoPostProcessor, bootApplication, org.springframework.boot.autoconfigure.internalCachingMetadataReaderFactory, beanLifeCycleService, customerBeanPostProcessor, org.springframework.boot.autoconfigure.AutoConfigurationPackages, org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration, org.springframework.boot.autoconfigure.condition.BeanTypeRegistry, propertySourcesPlaceholderConfigurer, org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration$Jackson2ObjectMapperBuilderCustomizerConfiguration, standardJacksonObjectMapperBuilderCustomizer, spring.jackson-org.springframework.boot.autoconfigure.jackson.JacksonProperties, org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor, org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor.store, org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration$JacksonObjectMapperBuilderConfiguration, jacksonObjectMapperBuilder, org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration$JacksonObjectMapperConfiguration, jacksonObjectMapper, org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration, jsonComponentModule, org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration$TomcatWebSocketConfiguration, websocketContainerCustomizer, org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration, org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration$EmbeddedTomcat, tomcatEmbeddedServletContainerFactory, org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration, embeddedServletContainerCustomizerBeanPostProcessor, errorPageRegistrarBeanPostProcessor, org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration$DispatcherServletConfiguration, dispatcherServlet, spring.mvc-org.springframework.boot.autoconfigure.web.WebMvcProperties, org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration$DispatcherServletRegistrationConfiguration, dispatcherServletRegistration, org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration, org.springframework.boot.autoconfigure.validation.DefaultValidatorConfiguration, defaultValidator, org.springframework.boot.autoconfigure.validation.Jsr303ValidatorAdapterConfiguration, org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration, methodValidationPostProcessor, org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration$WhitelabelErrorViewConfiguration, error, beanNameViewResolver, org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration$DefaultErrorViewResolverConfiguration, conventionErrorViewResolver, org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration, errorAttributes, basicErrorController, errorPageCustomizer, preserveErrorControllerTargetClassPostProcessor, spring.resources-org.springframework.boot.autoconfigure.web.ResourceProperties, org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration$EnableWebMvcConfiguration, requestMappingHandlerAdapter, requestMappingHandlerMapping, mvcContentNegotiationManager, mvcPathMatcher, mvcUrlPathHelper, viewControllerHandlerMapping, beanNameHandlerMapping, resourceHandlerMapping, mvcResourceUrlProvider, defaultServletHandlerMapping, mvcConversionService, mvcUriComponentsContributor, httpRequestHandlerAdapter, simpleControllerHandlerAdapter, handlerExceptionResolver, mvcViewResolver, org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration$WebMvcAutoConfigurationAdapter$FaviconConfiguration, faviconHandlerMapping, faviconRequestHandler, org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration$WebMvcAutoConfigurationAdapter, defaultViewResolver, viewResolver, welcomePageHandlerMapping, requestContextFilter, org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration, hiddenHttpMethodFilter, httpPutFormContentFilter, org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration, org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration$StringHttpMessageConverterConfiguration, stringHttpMessageConverter, spring.http.encoding-org.springframework.boot.autoconfigure.web.HttpEncodingProperties, org.springframework.boot.autoconfigure.web.JacksonHttpMessageConvertersConfiguration$MappingJackson2HttpMessageConverterConfiguration, mappingJackson2HttpMessageConverter, org.springframework.boot.autoconfigure.web.JacksonHttpMessageConvertersConfiguration, org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration, messageConverters, org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration, spring.info-org.springframework.boot.autoconfigure.info.ProjectInfoProperties, org.springframework.boot.autoconfigure.web.HttpEncodingAutoConfiguration, characterEncodingFilter, localeCharsetMappingsCustomizer, org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration, multipartConfigElement, multipartResolver, spring.http.multipart-org.springframework.boot.autoconfigure.web.MultipartProperties, org.springframework.boot.autoconfigure.web.ServerPropertiesAutoConfiguration, serverProperties, duplicateServerPropertiesDetector, org.springframework.boot.autoconfigure.web.WebClientAutoConfiguration$RestTemplateConfiguration, restTemplateBuilder, org.springframework.boot.autoconfigure.web.WebClientAutoConfiguration]
执行BeanPostProcessor的postProcessBeforeInitialization方法,beanName=beanLifeCycleService
调用PostConstruct注解标注的方法
执行InitializingBean接口的afterPropertiesSet方法
执行配置的init-method
执行BeanPostProcessor的postProcessAfterInitialization方法,beanName=beanLifeCycleService
Spring容器初始化完毕
=====================================
从容器中获取Bean
BeanLifeCycle Name=beanLifeCycle
=====================================
Spring容器关闭
调用preDestroy注解标注的方法
执行DisposableBean接口的destroy方法
执行配置的destroy-method
```

## References
- [Spring Bean的生命周期](https://yemengying.com/2016/07/14/spring-bean-life-cycle/)