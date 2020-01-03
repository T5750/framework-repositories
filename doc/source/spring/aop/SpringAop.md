# Spring AOP

## 装饰器与代理
>在代理模式中，我们创建具有现有对象的对象，以便向外界提供功能接口

- 装饰器模式：点了咖啡，发现太苦了，不是自己想要的，然后用装饰器加了点糖
	```
	Coffee coffee = new BitterCoffee();
	coffee = new SugarDecorator(coffee);
	```
- 代理模式：直接就点的加糖咖啡
	```
	Coffee coffee = new CoffeeWithSugar();
	```

## AOP
AOP：Aspect Oriented Programming，面向切面编程，是面向对象编程的补充

### JDK动态代理
根据`InvocationHandler`中的`invoke`方法动态生成一个类，该类实现`SmsService`接口，代理对象，就是用这个类实例化的

![SmsJdkProxy-min](https://s1.wailian.download/2020/01/03/SmsJdkProxy-min.png)

JDK的动态代理，是生成一个实现相应接口的代理类
```
@Autowired
private SmsService smsService;

@Autowired
private SmsServiceImpl smsService;

Proxy.newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h);
```
所以，JDK动态代理针对直接注入类类型的，就代理不了

### cglib动态代理
- 根据当前的类，动态生成一个子类，在子类中织入切面逻辑
- 使用子类对象代理父类对象
- 织入成功的，都是子类能把父类覆盖的方法

## Results
- `DecoratorModeTest`, `ProxyModeTest`
- `SmsServiceTest`,`SmsJdkProxyTest`

## References
- [装饰器、代理模式与Spring AOP](https://segmentfault.com/a/1190000019148468)