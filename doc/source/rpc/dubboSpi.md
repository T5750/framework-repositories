# Dubbo SPI

## 简介
- SPI 全称为 Service Provider Interface，是一种服务发现机制。
- SPI 的本质：将接口实现类的全限定名配置在文件中，并由服务加载器读取配置文件，加载实现类。这样可以在运行时，动态为接口替换实现类。
- Dubbo 就是通过 SPI 机制加载所有的组件。不过，Dubbo 并未使用 Java 原生的 SPI 机制，而是对其进行了增强，使其能够更好的满足需求。

## SPI 示例
### Java SPI
- `META-INF/services`
- `JavaSPITest`

### Dubbo SPI
- `META-INF/dubbo`
- `@SPI`
- `DubboSPITest`

## Dubbo SPI 源码分析
通过 `ExtensionLoader` 的 `getExtensionLoader` 方法获取一个 `ExtensionLoader` 实例，然后再通过 `ExtensionLoader` 的 `getExtension` 方法获取拓展类对象。
- `getExtensionLoader(Class<T> type)`: 从缓存中获取与拓展类对应的 `ExtensionLoader`，若缓存未命中，则创建一个新的实例
- `getExtension(String name)`: 检查缓存，缓存未命中则创建拓展对象
- `createExtension(String name)`:
	1. 通过 `getExtensionClasses` 获取所有的拓展类
	2. 通过反射创建拓展对象
	3. 向拓展对象中注入依赖
	4. 将拓展对象包裹在相应的 Wrapper 对象中

### 获取所有的拓展类
- `getExtensionClasses()`: 根据配置文件解析出拓展项名称到拓展类的映射关系表（`Map<名称, 拓展类>`），之后再根据拓展项名称从映射关系表中取出相应的拓展类即可
- `loadExtensionClasses()`:
	1. 对 SPI 注解进行解析
	2. 调用 `loadDirectory` 方法加载指定文件夹配置文件
- `loadDirectory(Map<String, Class<?>> extensionClasses, String dir)`: 通过 `classLoader` 获取所有资源链接，再通过 `loadResource` 方法加载资源
	* `META-INF/dubbo/internal/`
	* `META-INF/dubbo/`
	* `META-INF/services/`
- `loadResource(Map<String, Class<?>> extensionClasses, ClassLoader classLoader, java.net.URL resourceURL)`: 读取和解析配置文件，并通过反射加载类，最后调用 `loadClass` 方法进行其他操作
- `loadClass(Map<String, Class<?>> extensionClasses, java.net.URL resourceURL, Class<?> clazz, String name)`: 用于操作缓存，比如 `cachedAdaptiveClass`、`cachedWrapperClasses` 和 `cachedNames` 等

### Dubbo IOC
Dubbo IOC 是通过 setter 方法注入依赖。`injectExtension(T instance)`:
- 通过反射获取到实例的所有方法
- 遍历方法列表，检测方法名是否具有 setter 方法特征。若有，则通过 `ObjectFactory` 获取依赖对象
- 最后通过反射调用 setter 方法将依赖设置到目标对象中

Dubbo 目前提供了两种 `ExtensionFactory`:
- `SpiExtensionFactory`: 用于创建自适应的拓展
- `SpringExtensionFactory`: 用于从 Spring 的 IOC 容器中获取所需的拓展

## References
- [Dubbo SPI](http://dubbo.apache.org/zh-cn/docs/source_code_guide/dubbo-spi.html)