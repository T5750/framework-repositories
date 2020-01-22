# Dubbo Adaptive Extension

## 原理
- 在 Dubbo 中，很多拓展都是通过 SPI 机制进行加载的，比如 `Protocol`、`Cluster`、`LoadBalance` 等。
- 有时，有些拓展并不想在框架启动阶段被加载，而是希望在拓展方法被调用时，根据运行时参数进行加载。
- 自适应拓展机制的实现逻辑比较复杂
	* 首先 Dubbo 会为拓展接口生成具有代理功能的代码
	* 然后通过 javassist 或 jdk 编译这段代码，得到 Class 类
	* 最后再通过反射创建代理类
- 自适应拓展类的核心实现：在拓展接口的方法被调用时，通过 SPI 加载具体的拓展实现类，并调用拓展对象的同名方法。

### Results
- `AdaptiveExtensionTest`

## 源码分析
```
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Adaptive {
    String[] value() default {};
}
```
`Adaptive` 可注解在类或方法上
- 当 `Adaptive` 注解在类上时，Dubbo 不会为该类生成代理类。
- 注解在方法（接口方法）上时，Dubbo 则会为该方法生成代理逻辑。
- 手工编码：`Adaptive` 注解在类上的情况很少，在 Dubbo 中，仅有两个类被 `Adaptive` 注解了，分别是 `AdaptiveCompiler` 和 `AdaptiveExtensionFactory`。此种情况，表示拓展的加载逻辑由人工编码完成。
- 自动生成：更多时候，`Adaptive` 是注解在接口方法上的，表示拓展的加载逻辑需由框架自动生成。

### 1 获取自适应拓展
- `getAdaptiveExtension()`: 获取自适应拓展的入口方法，检查缓存，缓存未命中，则调用 `createAdaptiveExtension` 方法创建自适应拓展
- `createAdaptiveExtension()`:
	1. 调用 `getAdaptiveExtensionClass` 方法获取自适应拓展 Class 对象
	2. 通过反射进行实例化
	3. 调用 `injectExtension` 方法向拓展实例中注入依赖。目的是为手工编码的自适应拓展注入依赖
- `getAdaptiveExtensionClass()`: 如果某个实现类被 `Adaptive` 注解修饰了，那么该类就会被赋值给 `cachedAdaptiveClass` 变量
	1. 调用 `getExtensionClasses` 获取所有的拓展类
	2. 检查缓存，若缓存不为空，则返回缓存
	3. 若缓存为空，则调用 `createAdaptiveExtensionClass` 创建自适应拓展类
- `createAdaptiveExtensionClass()`: 用于生成自适应拓展类
	* 生成自适应拓展类的源码
	* 通过 `Compiler` 实例（Dubbo 默认使用 javassist 作为编译器）编译源码，得到代理类 Class 实例

### 2 自适应拓展类代码生成
`createAdaptiveExtensionClassCode()`

#### 2.1 Adaptive 注解检测
通过反射检测接口方法是否包含 `Adaptive` 注解。对于要生成自适应拓展的接口，Dubbo 要求该接口至少有一个方法被 `Adaptive` 注解修饰。

#### 2.2 生成类
- 生成 package 代码：`package + type 所在包`
- 生成 import 代码：`import + ExtensionLoader 全限定名`
- 生成类代码：`public class + type简单名称 + $Adaptive + implements + type全限定名 + {`
- `${生成方法}`

#### 2.3 生成方法
1. 无 `Adaptive` 注解方法代码生成逻辑
2. 获取 `URL` 数据，并为之生成判空和赋值代码
3. 获取 `Adaptive` 注解值
4. 检测 `Invocation` 参数
5. 生成拓展名获取逻辑。本段逻辑可能会生成但不限于下面的代码：
	- `String extName = (url.getProtocol() == null ? "dubbo" : url.getProtocol());`
	- `String extName = url.getMethodParameter(methodName, "loadbalance", "random");`
	- `String extName = url.getParameter("client", url.getParameter("transporter", "netty"));`
6. 生成拓展加载与目标方法调用逻辑
	```
	com.alibaba.dubbo.rpc.Protocol extension = (com.alibaba.dubbo.rpc.Protocol) ExtensionLoader
		.getExtensionLoader(com.alibaba.dubbo.rpc.Protocol.class).getExtension(extName);
	return extension.refer(arg0, arg1);
	```
7. 生成完整的方法
	```
	public com.alibaba.dubbo.rpc.Invoker refer(java.lang.Class arg0, com.alibaba.dubbo.common.URL arg1) {
		// 方法体
	}
	```

```
for (Method method : methods) {
    Class<?> rt = method.getReturnType();
    Class<?>[] pts = method.getParameterTypes();    // 获取参数类型列表
    Class<?>[] ets = method.getExceptionTypes();

    Adaptive adaptiveAnnotation = method.getAnnotation(Adaptive.class);
    StringBuilder code = new StringBuilder(512);
    if (adaptiveAnnotation == null) {
        // ${无 Adaptive 注解方法代码生成逻辑}
    } else {
        // ${获取 URL 数据}
        // ${获取 Adaptive 注解值}
        // ${检测 Invocation 参数}
        // ${生成拓展名获取逻辑}
        // ${生成拓展加载与目标方法调用逻辑}
    }
}
// ${生成完整的方法}
```

### Results
- `AdaptiveExtensionClassTest`

## References
- [SPI 自适应拓展](http://dubbo.apache.org/zh-cn/docs/source_code_guide/adaptive-extension.html)