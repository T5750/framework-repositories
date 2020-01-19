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
- `Adaptive` 注解在类上的情况很少，在 Dubbo 中，仅有两个类被 `Adaptive` 注解了，分别是 `AdaptiveCompiler` 和 `AdaptiveExtensionFactory`。此种情况，表示拓展的加载逻辑由人工编码完成。
- 更多时候，`Adaptive` 是注解在接口方法上的，表示拓展的加载逻辑需由框架自动生成。


## References
- [SPI 自适应拓展](http://dubbo.apache.org/zh-cn/docs/source_code_guide/adaptive-extension.html)