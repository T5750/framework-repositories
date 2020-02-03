# cglib

## Overview
[cglib](https://github.com/cglib/cglib) (Code Generation Library) is a byte instrumentation library used in many Java frameworks such as Hibernate or Spring. The bytecode instrumentation allows manipulating or creating classes after the compilation phase of a program.

## cglib
Byte Code Generation Library is high level API to generate and transform Java byte code. It is used by AOP, testing, data access frameworks to generate dynamic proxy objects and intercept field access.

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

## Results
- `CglibTest`

## References
- [Introduction to cglib](https://www.baeldung.com/cglib)
- [How To](https://github.com/cglib/cglib/wiki/How-To)