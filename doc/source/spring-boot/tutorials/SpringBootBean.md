# Get all loaded beans

## Get all loaded beans with Class Type Information
### Using ApplicationContext to get all loaded beans
1. Use `ApplicationContext.getBeanDefinitionNames()` to find the name of all loaded beans
1. Use `ApplicationContext.getBean(beanName)` to get bean including its runtime type information.

### Results
- `BeanController`

## References
- [Spring Boot – Get all loaded beans](https://howtodoinjava.com/spring-boot/get-loaded-beans-class-type-information/)