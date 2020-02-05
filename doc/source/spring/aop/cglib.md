# cglib

## Overview
[cglib](https://github.com/cglib/cglib) (Code Generation Library) is a byte instrumentation library used in many Java frameworks such as Hibernate or Spring. The bytecode instrumentation allows manipulating or creating classes after the compilation phase of a program.

cglib - Byte Code Generation Library is high level API to generate and transform Java byte code. It is used by AOP, testing, data access frameworks to generate dynamic proxy objects and intercept field access.

### Figure 1: CGLIB Library and ASM Bytecode Framework
![CGLIB_Library_and_ASM_Bytecode_Framework](https://s1.wailian.download/2020/02/05/CGLIB_Library_and_ASM_Bytecode_Framework-min.png)

## APIs
The CGLIB library(2.1.2) is organized as follows:
- `net.sf.cglib.core`: Low-level bytecode manipulation classes; Most of them are related to ASM.
- `net.sf.cglib.transform`: Classes for class file transformations at runtime or build time
- `net.sf.cglib.proxy`: Classes for proxy creation and method interceptions
- `net.sf.cglib.reflect`: Classes for a faster reflection and C#-style delegates
- `net.sf.cglib.util`: Collection sorting utilities
- `net.sf.cglib.beans`: JavaBean related utilities

As discussed in preceding section, the CGLIB library is a high-level layer on top of ASM. It is very useful for proxying classes that do not implement interfaces. Essentially, it dynamically generates a subclass to override the non-final methods of the proxied class and wires up hooks that call back to user-defined interceptors. It is faster than the JDK dynamic proxy approach.

### Figure 2: CGLIB library APIs commonly used for proxying classes
![CGLIB_Library_APIs_Commonly_Used_for_Porxying_Classes](https://s1.wailian.download/2020/02/05/CGLIB_Library_APIs_Commonly_Used_for_Porxying_Classes-min.png)

CGLIB library APIs commonly used for proxying concrete classes are illustrated in Figure 2. `The net.sf.cglib.proxy.Callback` interface is a marker interface. All callback interfaces used by the `net.sf.cglib.proxy.Enhancer` class extend this interface.

The `net.sf.cglib.proxy.MethodInterceptor` is the most general callback type. It is often used in proxy-based AOP implementations to intercept method invocations. This interface has a single method:
```
public Object intercept(Object object, java.lang.reflect.Method method,
      Object[] args, MethodProxy proxy) throws Throwable;
```
When `net.sf.cglib.proxy.MethodInterceptor` is the callback for all methods of a proxy, method invocations on the proxy are routed to this method before invoking the methods on the original object. It is illustrated in Figure 3. The first argument is the proxy object. The second and third arguments are the method being intercepted and the method arguments, respectively. The original method may either be invoked by normal reflection using the `java.lang.reflect.Method` object or by using the `net.sf.cglib.proxy.MethodProxy` object. `net.sf.cglib.proxy.MethodProxy` is usually preferred because it is faster. In this method, custom code can be injected before or after invoking the original methods.

### Figure 3: CGLIB MethodInterceptor
![CGLIB_Method_Interceptor](https://s1.wailian.download/2020/02/05/CGLIB_Method_Interceptor-min.png)

`net.sf.cglib.proxy.MethodInterceptor` meets any interception needs, but it may be overkill for some situations. For simplicity and performance, additional specialized callback types are offered out of the box. For examples:
- `net.sf.cglib.proxy.FixedValue`: It is useful to force a particular method to return a fixed value for performance reasons.
- `net.sf.cglib.proxy.NoOp`: It delegates method invocations directly to the default implementations in the super class.
- `net.sf.cglib.proxy.LazyLoader`: It is useful when the real object needs to be lazily loaded. Once the real object is loaded, it is used for every future method call to the proxy instance.
- `net.sf.cglib.proxy.Dispatcher`: It has the same signatures as `net.sf.cglib.proxy.LazyLoader`, but the `loadObject` method is always called when a proxy method is invoked.
- `net.sf.cglib.proxy.ProxyRefDispatcher`: It is the same as `Dispatcher`, but it allows the proxy object to be passed in as an argument of the `loadObject` method.

## Implementing Proxy Using cglib

### Returning the Same Value
We want to create a simple proxy class that will intercept a call to a `sayHello()` method. The `Enhancer` class allows us to create a proxy by dynamically extending a `PersonService` class by using a `setSuperclass()` method from the `Enhancer` class:
```
Enhancer enhancer = new Enhancer();
enhancer.setSuperclass(PersonService.class);
enhancer.setCallback((FixedValue) () -> "Hello Tom!");
PersonService proxy = (PersonService) enhancer.create();
String res = proxy.sayHello(null);
assertEquals("Hello Tom!", res);
```
The `FixedValue` is a callback interface that simply returns the value from the proxied method. Executing `sayHello()` method on a proxy returned a value specified in a proxy method.

## Returning Value Depending on a Method Signature
The first version of our proxy has some drawbacks because we are not able to decide which method a proxy should intercept, and which method should be invoked from a superclass. We can use a `MethodInterceptor` interface to intercept all calls to the proxy and decide if want to make a specific call or execute a method from a superclass:
```
Enhancer enhancer = new Enhancer();
enhancer.setSuperclass(PersonService.class);
enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
    if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
        return "Hello Tom!";
    } else {
        return proxy.invokeSuper(obj, args);
    }
});
PersonService proxy = (PersonService) enhancer.create();
assertEquals("Hello Tom!", proxy.sayHello(null));
int lengthOfName = proxy.lengthOfName("Mary");
assertEquals(4, lengthOfName);
```

## Bean Creator
Another useful construct from the cglib is a `BeanGenerator` class. It allows us to dynamically create beans and to add fields together with setter and getter methods. It can be used by code generation tools to generate simple POJO objects:
```
BeanGenerator beanGenerator = new BeanGenerator();
beanGenerator.addProperty("name", String.class);
Object myBean = beanGenerator.create();
Method setter = myBean.getClass().getMethod("setName", String.class);
setter.invoke(myBean, "some string value set by a cglib");
Method getter = myBean.getClass().getMethod("getName");
assertEquals("some string value set by a cglib", getter.invoke(myBean));
```

## Creating Mixin
A `Mixin` is a construct that allows combining multiple objects into one. We can include a behavior of a couple of classes and expose that behavior as a single class or interface. The cglib Mixins allow the combination of several objects into a single object. However, in order to do so all objects that are included within a `Mixin` must be backed by interfaces.

To compose implementations of `Interface1` and `Interface2` we need to create an interface that extends both of them:
```
public interface MixinInterface extends Interface1, Interface2 { }
```

By using a `create()` method from the `Mixin` class we can include behaviors of `Class1` and `Class2` into a `MixinInterface`:
```
Mixin mixin = Mixin.create(
  new Class[]{ Interface1.class, Interface2.class, MixinInterface.class },
  new Object[]{ new Class1(), new Class2() }
);
MixinInterface mixinDelegate = (MixinInterface) mixin;
assertEquals("first behaviour", mixinDelegate.first());
assertEquals("second behaviour", mixinDelegate.second());
```
Calling methods on the `mixinDelegate` will invoke implementations from `Class1` and `Class2`.

## Avoiding StackOverflowError
Common mistake is to cause recursion in `MethodInterceptor` implementation:
```
Object intercept(Object proxy, Method method, MethodProxy fastMethod, Object args[]) throws Throwable {
    //ERROR 
    System.out.println(proxy.toString());
    //ERROR 
    return fastMethod.invoke(proxy,args);     
}
```
`invokeSuper` method must be used to invoke super class method. It will throw `AbstractMethodError` if super method is abstract. See trace sample how to solve recursion problem caused by `this` parameter in `args[]` array.

## Optimizing Proxies
Filter unused methods with `CallbackFilter` and use light `Callback` version if possible. It can help to avoid hash lookup on method object if you use per method interceptors too.

## Summary
CGLIB is a powerful, high performance code generation library. It is complementary to the JDK dynamic proxy in that it provides proxying classes that do not implement interfaces. Under the covers, it uses ASM bytecode manipulation framework. Essentially, CGLIB dynamically generates a subclass to override the non-final methods of the proxied class. It is faster than the JDK dynamic proxy approach, which uses Java reflection. CGLIB cannot proxy a final class or a class with any final methods. For general cases, you use the JDK dynamic proxy approach to create proxies. When the interfaces are not available or the performance is an issue, CGLIB is a good alternative.

## Results
- `CglibTest`

## References
- [Introduction to cglib](https://www.baeldung.com/cglib)
- [How To](https://github.com/cglib/cglib/wiki/How-To)
- [CREATE PROXIES DYNAMICALLY USING CGLIB LIBRARY](https://objectcomputing.com/resources/publications/sett/november-2005-create-proxies-dynamically-using-cglib-library)