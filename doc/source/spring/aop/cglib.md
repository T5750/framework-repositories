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

### Enhancer
An `Enhancer` allows the creation of Java proxies for non-interface types.The `Enhancer` dynamically creates a subclass of a given type but intercepts all method calls. Other than with the `Proxy` class, this works for both class and interface types.

### Figure 2: CGLIB library APIs commonly used for proxying classes
![CGLIB_Library_APIs_Commonly_Used_for_Porxying_Classes](https://s1.wailian.download/2020/02/05/CGLIB_Library_APIs_Commonly_Used_for_Porxying_Classes-min.png)

CGLIB library APIs commonly used for proxying concrete classes are illustrated in Figure 2. The `net.sf.cglib.proxy.Callback` interface is a marker interface. All callback interfaces used by the `net.sf.cglib.proxy.Enhancer` class extend this interface.

The `net.sf.cglib.proxy.MethodInterceptor` is the most general callback type. It is often used in proxy-based AOP implementations to intercept method invocations. This interface has a single method:
```
public Object intercept(Object object, java.lang.reflect.Method method,
      Object[] args, MethodProxy proxy) throws Throwable;
```
When `MethodInterceptor` is the callback for all methods of a proxy, method invocations on the proxy are routed to this method before invoking the methods on the original object. It is illustrated in Figure 3.
- The first argument is the proxy object.
- The second and third arguments are the method being intercepted and the method arguments, respectively.
- The original method may either be invoked by normal reflection using the `Method` object or by using the `net.sf.cglib.proxy.MethodProxy` object. `MethodProxy` is usually preferred because it is faster.
- In this method, custom code can be injected before or after invoking the original methods.

### Figure 3: CGLIB MethodInterceptor
![CGLIB_Method_Interceptor](https://s1.wailian.download/2020/02/05/CGLIB_Method_Interceptor-min.png)

`MethodInterceptor` meets any interception needs, but it may be overkill for some situations. For simplicity and performance, additional specialized callback types are offered out of the box. For examples:
- `net.sf.cglib.proxy.FixedValue`: It is useful to force a particular method to return a fixed value for performance reasons.
- `net.sf.cglib.proxy.NoOp`: It delegates method invocations directly to the default implementations in the super class.
- `net.sf.cglib.proxy.LazyLoader`: It is useful when the real object needs to be lazily loaded. Once the real object is loaded, it is used for every future method call to the proxy instance.
- `net.sf.cglib.proxy.Dispatcher`: It has the same signatures as `LazyLoader`, but the `loadObject` method is always called when a proxy method is invoked.
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

The anonymous subclass of `FixedValue` would become hardly referenced from the enhanced `SampleClass` such that neither the anonymous `FixedValue` instance or the class holding the `@Test` method would ever be garbage collected. This can introduce nasty memory leaks in your applications. Therefore, do not use non-`static` inner classes with cglib.

### Returning Value Depending on a Method Signature
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

## Immutable Bean
cglib's `ImmutableBean` allows you to create an immutability wrapper similar to for example `Collections#immutableSet`. All changes of the underlying bean will be prevented by an `IllegalStateException` (however, not by an `UnsupportedOperationException` as recommended by the Java API).
```
@Test(expected = IllegalStateException.class)
public void testImmutableBean() throws Exception {
    SampleBean bean = new SampleBean();
    bean.setValue(RESULT);
    SampleBean immutableBean = (SampleBean) ImmutableBean.create(bean);
    assertEquals(RESULT, immutableBean.getValue());
    bean.setValue(RESULT_AGAIN);
    assertEquals(RESULT_AGAIN, immutableBean.getValue());
    immutableBean.setValue(RESULT); // Causes exception.
}
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
On creation, the `BeanGenerator` creates the accessors
- `<type> get<name>()`
- `void set<name>(<type>)`

## Bean Copier
The `BeanCopier` is another bean utility that copies beans by their property values.
```
@Test
public void testBeanCopier() throws Exception {
    BeanCopier copier = BeanCopier.create(SampleBean.class,
            OtherSampleBean.class, false);
    SampleBean bean = new SampleBean();
    bean.setValue(RESULT);
    OtherSampleBean otherBean = new OtherSampleBean();
    copier.copy(bean, otherBean, null);
    assertEquals(RESULT, otherBean.getValue());
}
```
without being restrained to a specific type. The `BeanCopier#copy` mehtod takles an (eventually) optional `Converter` which allows to do some further manipulations on each bean property. If the `BeanCopier` is created with false as the third constructor argument, the `Converter` is ignored and can therefore be `null`.

## Bulk Bean
A `BulkBean` allows to use a specified set of a bean's accessors by arrays instead of method calls:
```
@Test
public void testBulkBean() throws Exception {
    BulkBean bulkBean = BulkBean.create(SampleBean.class,
            new String[] { "getValue" }, new String[] { "setValue" },
            new Class[] { String.class });
    SampleBean bean = new SampleBean();
    bean.setValue(RESULT);
    assertEquals(1, bulkBean.getPropertyValues(bean).length);
    assertEquals(RESULT, bulkBean.getPropertyValues(bean)[0]);
    bulkBean.setPropertyValues(bean, new Object[] { RESULT_AGAIN });
    assertEquals(RESULT_AGAIN, bean.getValue());
}
```
The `BulkBean` takes an array of getter names, an array of setter names and an array of property types as its constructor arguments. The resulting instrumented class can then extracted as an array by `BulkBean#getPropertyBalues(Object)`. Similarly, a bean's properties can be set by `BulkBean#setPropertyBalues(Object, Object[])`.

## Bean Map
This is the last bean utility within the cglib library. The `BeanMap` converts all properties of a bean to a `String`-to-`Object` Java `Map`:
```
@Test
public void testBeanGenerator() throws Exception {
    SampleBean bean = new SampleBean();
    BeanMap map = BeanMap.create(bean);
    bean.setValue("Hello cglib!");
    assertEquals("Hello cglib!", map.get("value"));
}
```
Additionally, the `BeanMap#newInstance(Object)` method allows to create maps for other beans by reusing the same `Class`.

## Key Factory
The `KeyFactory` factory allows the dynamic creation of keys that are composed of multiple values that can be used in for example `Map` implementations. For doing so, the `KeyFactory` requires some interface that defines the values that should be used in such a key. This interface must contain a single method by the name newInstance that returns an `Object`.
```
@Test
public void testKeyFactory() throws Exception {
    SampleKeyFactory keyFactory = (SampleKeyFactory) KeyFactory
            .create(Key.class);
    Object key = keyFactory.newInstance("foo", 42);
    Map<Object, String> map = new HashMap<Object, String>();
    map.put(key, RESULT);
    assertEquals(RESULT, map.get(keyFactory.newInstance("foo", 42)));
}
```
The `KeyFactory` will assure the correct implementation of the `Object#equals(Object)` and `Object#hashCode` methods such that the resulting key objects can be used in a `Map` or a `Set`. The `KeyFactory` is also used quite a lot internally in the cglib library.

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

## String Switcher
The `StringSwitcher` emulates a `String` to int Java `Map`:
```
@Test
public void testStringSwitcher() throws Exception {
    String[] strings = new String[] { "one", "two" };
    int[] values = new int[] { 10, 20 };
    StringSwitcher stringSwitcher = StringSwitcher.create(strings, values,
            true);
    assertEquals(10, stringSwitcher.intValue("one"));
    assertEquals(20, stringSwitcher.intValue("two"));
    assertEquals(-1, stringSwitcher.intValue("three"));
}
```
The `StringSwitcher` allows to emulate a `switch` command on Strings such as it is possible with the built-in Java `switch` statement since Java 7. If using the `StringSwitcher` in Java 6 or less really adds a benefit to your code remains however doubtful and I would personally not recommend its use.

## Interface Maker
The `InterfaceMaker` does what its name suggests: It dynamically creates a new interface.
```
@Test
public void testInterfaceMaker() throws Exception {
    Signature signature = new Signature("foo", Type.DOUBLE_TYPE,
            new Type[] { Type.INT_TYPE });
    InterfaceMaker interfaceMaker = new InterfaceMaker();
    interfaceMaker.add(signature, new Type[0]);
    Class iface = interfaceMaker.create();
    assertEquals(1, iface.getMethods().length);
    assertEquals("foo", iface.getMethods()[0].getName());
    assertEquals(double.class, iface.getMethods()[0].getReturnType());
}
```
Other than any other class of cglib's public API, the interface maker relies on ASM types. The creation of an interface in a running application will hardly make sense since an interface only represents a type which can be used by a compiler to check types. It can however make sense when you are generating code that is to be used in later development.

## Method Delegate
A `MethodDelegate` allows to emulate a [C#-like](http://msdn.microsoft.com/de-de/library/900fyy8e%28v=vs.90%29.aspx) delegate to a specific method by binding a method call to some interface. For example, the following code would bind the `SampleBean#getValue` method to a delegate:
```
@Test
public void testMethodDelegate() throws Exception {
    SampleBean bean = new SampleBean();
    bean.setValue(RESULT);
    BeanDelegate delegate = (BeanDelegate) MethodDelegate.create(bean,
            "getValue", BeanDelegate.class);
    assertEquals(RESULT, delegate.getValueFromDelegate());
}
```
There are however some things to note:
- The factory method `MethodDelegate#create` takes exactly one method name as its second argument. This is the method the `MethodDelegate` will proxy for you.
- There must be a method **without** arguments defined for the object which is given to the factory method as its first argument. Thus, the `MethodDelegate` is not as strong as it could be.
- The third argument must be an interface with exactly one argument. The `MethodDelegate` implements this interface and can be cast to it. When the method is invoked, it will call the proxied method on the object that is the first argument.

Furthermore, consider these drawbacks:
- cglib creates a new class for each proxy. Eventually, this will litter  up your permanent generation heap space
- You cannot proxy methods that take arguments.
- If your interface takes arguments, the method delegation will simply not  work without an exception thrown (the return value will always be `null`). If your interface requires another return type (even if that is more general), you will get a `IllegalArgumentException`.

## Multicast Delegate
The `MulticastDelegate` works a little different than the `MethodDelegate` even though it aims at similar functionality.

Based on this interface-backed bean we can create a `MulticastDelegate` that dispatches all calls to `setValue(String)` to several classes that implement the `DelegationProvider` interface:
```
@Test
public void testMulticastDelegate() throws Exception {
    MulticastDelegate multicastDelegate = MulticastDelegate
            .create(DelegatationProvider.class);
    SimpleMulticastBean first = new SimpleMulticastBean();
    SimpleMulticastBean second = new SimpleMulticastBean();
    multicastDelegate = multicastDelegate.add(first);
    multicastDelegate = multicastDelegate.add(second);
    DelegatationProvider provider = (DelegatationProvider) multicastDelegate;
    provider.setValue("Hello world!");
    assertEquals("Hello world!", first.getValue());
    assertEquals("Hello world!", second.getValue());
}
```
Again, there are some drawbacks:
- The objects need to implement a single-method interface. This sucks for  third-party libraries and is awkward when you use CGlib to do some magic where this magic gets exposed to the normal code.  Also, you could implement your own delegate easily (without byte code  though but I doubt that you win so much over manual delegation).
- When your delegates return a value, you will receive only that of the  last delegate you added. All other return values are lost (but retrieved  at some point by the multicast delegate).

## Constructor Delegate
A `ConstructorDelegate` allows to create a byte-instrumented [factory method](http://en.wikipedia.org/wiki/Factory_method_pattern). For that, that we first require an interface with a single method `newInstance` which returns an `Object` and takes any amount of parameters to be used for a constructor call of the specified class. For example, in order to create a `ConstructorDelegate` for the `SampleBean`, we require the following to call `SampleBean`'s default (no-argument) constructor:
```
@Test
public void testConstructorDelegate() throws Exception {
    SampleBeanConstructorDelegate constructorDelegate = (SampleBeanConstructorDelegate) ConstructorDelegate
            .create(SampleBean.class, SampleBeanConstructorDelegate.class);
    SampleBean bean = (SampleBean) constructorDelegate.newInstance();
    assertTrue(SampleBean.class.isAssignableFrom(bean.getClass()));
}
```

## Parallel Sorter
The `ParallelSorter` claims to be a faster alternative to the Java standard library's array sorters when sorting arrays of arrays:
```
@Test
public void testParallelSorter() throws Exception {
    Integer[][] value = { { 4, 3, 9, 0 }, { 2, 1, 6, 0 } };
    ParallelSorter.create(value).mergeSort(0);
    for (Integer[] row : value) {
        int former = -1;
        for (int val : row) {
            assertTrue(former < val);
            former = val;
        }
    }
}
```
The `ParallelSorter` takes an array of arrays and allows to either apply a merge sort or a quick sort on every row of the array. Be however careful when you use it:
- When using arrays of primitives, you have to call merge sort with explicit sorting ranges (e.g. `ParallelSorter.create(value).mergeSort(0, 0, 3)` in the example. Otherwise, the `ParallelSorter` has a pretty obvious bug where it tries to cast the primitive array to an array `Object[]` what will cause a `ClassCastException`.
- If the array rows are uneven, the first argument will determine the length of what row to consider. Uneven rows will either lead to the extra values not being considered for sorting or a `ArrayIndexOutOfBoundException`.

## Fast Class and Fast Members
The `FastClass` promises a faster invocation of methods than the [Java reflection API](http://docs.oracle.com/javase/tutorial/reflect/) by wrapping a Java class and offering similar methods to the reflection API:
```
@Test
public void testFastClass() throws Exception {
    FastClass fastClass = FastClass.create(SampleBean.class);
    FastMethod fastMethod = fastClass
            .getMethod(SampleBean.class.getMethod("getValue"));
    SampleBean myBean = new SampleBean();
    myBean.setValue("Hello cglib!");
    assertEquals("Hello cglib!", fastMethod.invoke(myBean, new Object[0]));
}
```
Besides the demonstrated `FastMethod`, the `FastClass` can also create `FastConstructors` but no fast fields. But how can the `FastClass` be faster than normal reflection? Java reflection is executed by JNI where method invocations are executed by some C-code. The `FastClass` on the other side creates some byte code that calls the method directly from within the JVM. However, the newer versions of the HotSpot JVM (and probably many other modern JVMs) know a concept called inflation where the JVM will [translate reflective method calls](http://www.docjar.com/html/api/sun/reflect/ReflectionFactory.java.html) into [native version](http://www.docjar.com/html/api/sun/reflect/NativeMethodAccessorImpl.java.html)'s of `FastClass` when a reflective method is executed often enough. You can even control this behavior (at least on a HotSpot JVM) with setting the `sun.reflect.inflationThreshold` property to a lower value. (The default is 15.) This property determines after how many reflective invocations a JNI call should be substituted by a byte code instrumented version. I would therefore recommend to not use `FastClass` on modern JVMs, it can however fine-tune performance on older Java virtual machines.

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

## A Final Word of Warning
After this overview of cglib's functionality, I want to speak a final word of warning. All cglib classes generate byte code which results in additional classes being stored in a special section of the JVM's memory: The so called perm space. This permanent space is, as the name suggests, used for permanent objects that do not usually get garbage collected. This is however not completely true: Once a `Class` is loaded, it cannot be unloaded until the loading `ClassLoader` becomes available for garbage collection. This is only the case the `Class` was loaded with a custom `ClassLoader` which is not a native JVM system `ClassLoader`. This `ClassLoader` can be garbage collected if itself, all Classes it ever loaded and all instances of all Classes it ever loaded become available for garbage collection. This means: If you create more and more classes throughout the life of a Java application and if you do not take care of the removal of these classes, you will sooner or later run of of perm space what will result in your [application's death by the hands of an `OutOfMemoryError`](http://ghb.freshblurbs.com/blog/2005/05/19/explaining-java-lang-outofmemoryerror-permgen-space.html). Therefore, use cglib sparingly. However, if you use cglib wisely and carefully, you can really do amazing things with it that go beyond what you can do with non-instrumented Java applications.

## Summary
CGLIB is a powerful, high performance code generation library. It is complementary to the JDK dynamic proxy in that it provides proxying classes that do not implement interfaces. Under the covers, it uses ASM bytecode manipulation framework. Essentially, CGLIB dynamically generates a subclass to override the non-final methods of the proxied class. It is faster than the JDK dynamic proxy approach, which uses Java reflection. CGLIB cannot proxy a final class or a class with any final methods. For general cases, you use the JDK dynamic proxy approach to create proxies. When the interfaces are not available or the performance is an issue, CGLIB is a good alternative.

## Results
- `CglibTest`

## References
- [Introduction to cglib](https://www.baeldung.com/cglib)
- [How To](https://github.com/cglib/cglib/wiki/How-To)
- [CREATE PROXIES DYNAMICALLY USING CGLIB LIBRARY](https://objectcomputing.com/resources/publications/sett/november-2005-create-proxies-dynamically-using-cglib-library)
- [CGLib: The Missing Manual](https://dzone.com/articles/cglib-missing-manual)