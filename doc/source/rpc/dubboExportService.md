# Dubbo Export Service

## 简介
整个逻辑大致可分为三个部分：
1. 前置工作，主要用于检查参数，组装 `URL`。
2. 导出服务，包含导出服务到本地 (JVM)，和导出服务到远程两个过程。
3. 向注册中心注册服务，用于服务发现。

## 源码分析
服务导出的入口方法是 `ServiceBean` 的 `onApplicationEvent`。
- `onApplicationEvent(ContextRefreshedEvent event)`: 在收到 Spring 上下文刷新事件后执行服务导出操作
- `isDelay()`:
	* `true`: 不延迟导出
	* `false`: 延迟导出
- `supportedApplicationListener`: 表示当前的 Spring 容器是否支持 `ApplicationListener`，这个值初始为 `false`

### 前置工作
>采用 URL 作为配置信息的统一格式，所有扩展点都通过传递 URL 携带配置信息。

1. 检查配置
	- `export()`: 对 `export` 和 `delay` 配置进行了检查，并根据配置执行相应的动作
	- `ServiceConfig.doExport()`:
		* 检测 `<dubbo:service>` 标签的 `interface` 属性合法性，不合法则抛出异常
		* 检测 `ProviderConfig`、`ApplicationConfig` 等核心配置类对象是否为空，若为空，则尝试从其他配置类对象中获取相应的实例。
		* 检测并处理泛化服务和普通服务类
		* 检测本地存根配置，并进行相应的处理
		* 对 `ApplicationConfig`、`RegistryConfig` 等配置类进行检测，为空则尝试创建，若无法创建则抛出异常
2. 多协议多注册中心导出服务
3. 组装 `URL`

### 导出 Dubbo 服务
1. `Invoker` 创建过程
2. 导出服务到本地
3. 导出服务到远程
4. 服务注册
	1. 创建注册中心
	2. 节点创建

## References
- [服务导出](http://dubbo.apache.org/zh-cn/docs/source_code_guide/export-service.html)