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
>采用 `URL` 作为配置信息的统一格式，所有扩展点都通过传递 `URL` 携带配置信息。

1. 检查配置
	- `export()`: 对 `export` 和 `delay` 配置进行了检查，并根据配置执行相应的动作
	- `ServiceConfig#doExport()`:
		1. 检测 `<dubbo:service>` 标签的 `interface` 属性合法性，不合法则抛出异常
		2. 检测 `ProviderConfig`、`ApplicationConfig` 等核心配置类对象是否为空，若为空，则尝试从其他配置类对象中获取相应的实例。
		3. 检测并处理泛化服务和普通服务类
		4. 检测本地存根配置，并进行相应的处理
		5. 对 `ApplicationConfig`、`RegistryConfig` 等配置类进行检测，为空则尝试创建，若无法创建则抛出异常
2. 多协议多注册中心导出服务
	- `ServiceConfig-doExportUrls()`: 对多协议，多注册中心进行了支持
	- `AbstractInterfaceConfig#loadRegistries(boolean provider)`: 加载注册中心链接
		1. 检测是否存在注册中心配置类，不存在则抛出异常
        2. 构建参数映射集合，也就是 `map`
        3. 构建注册中心链接列表
        4. 遍历链接列表，并根据条件决定是否将其添加到 `registryList` 中
3. 组装 `URL`: `URL` 之于 Dubbo，犹如水之于鱼
	- `doExportUrlsFor1Protocol(ProtocolConfig protocolConfig, List<URL> registryURLs)`
	- 注意：这里的 `URL` 并非 `java.net.URL`，而是 `com.alibaba.dubbo.common.URL`
	- `appendParameters(Map<String, String> parameters, Object config)`: 用于将对象字段信息添加到 `map` 中
		* 实现上则是通过反射获取目标对象的 getter 方法，并调用该方法获取属性值
		* 然后再通过 getter 方法名解析出属性名
		* 如果用户传入了属性名前缀，此时需要将属性名加入前缀内容
		* 最后将 `<属性名，属性值>` 键值对存入到 `map` 中
	```
	private void doExportUrlsFor1Protocol(ProtocolConfig protocolConfig, List<URL> registryURLs) {
		if (methods != null && !methods.isEmpty()) {
			// 检测 type 属性是否为空，或者空串（分支1 ⭐）
			if (argument.getType() != null && argument.getType().length() > 0) {
				if (argument.getIndex() != -1) {
					// 检测 ArgumentConfig 中的 type 属性与方法参数列表中的参数名称是否一致，不一致则抛出异常(分支2 ⭐)
					if (argtypes[argument.getIndex()].getName().equals(argument.getType())) {
					} else {    // 分支3 ⭐
					for (int j = 0; j < argtypes.length; j++) {}
					}
				}
			// 用户未配置 type 属性，但配置了 index 属性，且 index != -1
			} else if (argument.getIndex() != -1) {    // 分支4 ⭐
			}
		}
	}
	```
	```
	// 获取 ArgumentConfig 列表
	for (遍历 ArgumentConfig 列表) {
		if (type 不为 null，也不为空串) {    // 分支1
			1. 通过反射获取 interfaceClass 的方法列表
			for (遍历方法列表) {
				1. 比对方法名，查找目标方法
				2. 通过反射获取目标方法的参数类型数组 argtypes
				if (index != -1) {    // 分支2
					1. 从 argtypes 数组中获取下标 index 处的元素 argType
					2. 检测 argType 的名称与 ArgumentConfig 中的 type 属性是否一致
					3. 添加 ArgumentConfig 字段信息到 map 中，或抛出异常
				} else {    // 分支3
					1. 遍历参数类型数组 argtypes，查找 argument.type 类型的参数
					2. 添加 ArgumentConfig 字段信息到 map 中
				}
			}
		} else if (index != -1) {    // 分支4
			1. 添加 ArgumentConfig 字段信息到 map 中
		}
	}
	```

### 导出 Dubbo 服务
根据 `url` 中的 `scope` 参数决定服务导出方式，分别如下：
- `scope = none`: 不导出服务
- `scope != remote`: 导出到本地
- `scope != local`: 导出到远程

>`Invoker` 是实体域，它是 Dubbo 的核心模型，其它模型都向它靠扰，或转换成它，它代表一个可执行体，可向它发起 `invoke` 调用，它有可能是一个本地的实现，也可能是一个远程的实现，也可能一个集群实现。

1. `Invoker` 创建过程
	- `Invoker` 是由 `ProxyFactory` 创建而来，Dubbo 默认的 `ProxyFactory` 实现类是 `JavassistProxyFactory`
	- `JavassistProxyFactory+getInvoker(T proxy, Class<T> type, URL url)`
	- `Wrapper+getWrapper(Class<?> c)`: 仅包含一些缓存操作
	- `Wrapper-makeWrapper(Class<?> c)`:
		1. 初始化操作
			* `c1`: 用于存储 `setPropertyValue` 方法代码
			* `c2`: 用于存储 `getPropertyValue` 方法代码
			* `c3`: 用于存储 `invokeMethod` 方法代码
			* `pts`: 用于存储成员变量名和类型
			* `ms`: 用于存储方法描述信息（可理解为方法签名）及 `Method` 实例
			* `mns`: 为方法名列表
			* `dmns`: 用于存储“定义在当前类中的方法”的名称
		2. 为 `public` 级别的字段生成条件判断取值与赋值代码
		3. 为定义在当前类中的方法生成判断语句，和方法调用语句
		4. 处理 getter、setter 以及以 is/has/can 开头的方法
		5. 通过 `ClassGenerator` 为刚刚生成的代码构建 Class 类，并通过反射创建对象
	- `ClassGenerator`: 核心是 `toClass()` 的重载方法 `toClass(ClassLoader, ProtectionDomain)`，该方法通过 javassist 构建 Class
2. 导出服务到本地
	- `exportLocal(URL url)`:
		* 首先根据 `URL` 协议头决定是否导出服务
		* 若需导出，则创建一个新的 `URL` 并将协议头、主机名以及端口设置成新的值
		* 然后创建 `Invoker`，并调用 `InjvmProtocol` 的 `export` 方法导出服务
	- `InjvmProtocol+export(Invoker<T> invoker)`: 仅创建了一个 `InjvmExporter`
3. 导出服务到远程
	- `RegistryProtocol+export(final Invoker<T> originInvoker)`:
		1. 调用 `doLocalExport` 导出服务
        2. 向注册中心注册服务
        3. 向注册中心进行订阅 `override` 数据
        4. 创建并返回 `DestroyableExporter`
	- `RegistryProtocol-doLocalExport(final Invoker<T> originInvoker)`
	- `DubboProtocol+export(Invoker<T> invoker)`:
		* 获取服务标识
		* 创建 `DubboExporter`
		* 将 `<key, exporter>` 键值对放入缓存中
		* 本地存根相关代码
		* 启动服务器
		* 优化序列化
	- `DubboProtocol-openServer(URL url)`: 若某个端口上已有服务器实例，此时则调用 `reset` 方法重置服务器的一些配置
	- `DubboProtocol-createServer(URL url)`:
		1. 检测是否存在 `server` 参数所代表的 `Transporter` 拓展，不存在则抛出异常
		2. 创建服务器实例
		3. 检测是否支持 `client` 参数所表示的 `Transporter` 拓展，不存在也是抛出异常
	- `Exchangers+bind(URL url, ExchangeHandler handler)`
	- `HeaderExchanger+bind(URL url, ExchangeHandler handler)`: 创建 `HeaderExchangeServer` 实例
	- `Transporters+bind(URL url, ChannelHandler... handlers)`
	- `Transporters+getTransporter()`: 获取的 `Transporter` 是在运行时动态创建的，类名为 `TransporterAdaptive`，也就是自适应拓展类。默认为 `NettyTransporter`
	- `NettyTransporter+bind(URL url, ChannelHandler listener)`
	- `NettyServer`
	- `AbstractServer+AbstractServer(URL url, ChannelHandler handler)`
	- `AbstractServer#doOpen()`: `NettyServer` 创建的过程，dubbo 默认使用的 `NettyServer` 是基于 netty 3.x 版本实现的
		```
		protected void doOpen() throws Throwable {
			NettyHelper.setNettyLoggerFactory();
			// 创建 boss 和 worker 线程池
			ExecutorService boss = Executors.newCachedThreadPool(new NamedThreadFactory("NettyServerBoss", true));
			ExecutorService worker = Executors.newCachedThreadPool(new NamedThreadFactory("NettyServerWorker", true));
			ChannelFactory channelFactory = new NioServerSocketChannelFactory(boss, worker, getUrl().getPositiveParameter(Constants.IO_THREADS_KEY, Constants.DEFAULT_IO_THREADS));
			// 创建 ServerBootstrap
			bootstrap = new ServerBootstrap(channelFactory);
			final NettyHandler nettyHandler = new NettyHandler(getUrl(), this);
			channels = nettyHandler.getChannels();
			bootstrap.setOption("child.tcpNoDelay", true);
			// 设置 PipelineFactory
			bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
				@Override
				public ChannelPipeline getPipeline() {
					NettyCodecAdapter adapter = new NettyCodecAdapter(getCodec(), getUrl(), NettyServer.this);
					ChannelPipeline pipeline = Channels.pipeline();
					pipeline.addLast("decoder", adapter.getDecoder());
					pipeline.addLast("encoder", adapter.getEncoder());
					pipeline.addLast("handler", nettyHandler);
					return pipeline;
				}
			});
			// 绑定到指定的 ip 和端口上
			channel = bootstrap.bind(getBindAddress());
		}
		```
4. 服务注册
	- 对于 Dubbo 来说，注册中心虽不是必需，但却是必要的
	- `RegistryProtocol+export(Invoker<T> originInvoker)`
	- `RegistryProtocol+register(URL registryUrl, URL registedProviderUrl)`
		1. 获取注册中心实例
		2. 向注册中心注册服务
	1. 创建注册中心
		- `AbstractRegistryFactory+getRegistry(URL url)`:
			* 先访问缓存
			* 缓存未命中则调用 `createRegistry` 创建 `Registry`
			* 然后写入缓存
		- `ZookeeperRegistryFactory+createRegistry(URL url)`
		- `ZookeeperRegistry+ZookeeperRegistry(URL url, ZookeeperTransporter zookeeperTransporter)`
		- `CuratorZookeeperTransporter+connect(URL url)`: 默认为 `CuratorZookeeperTransporter`
		- `CuratorZookeeperClient+CuratorZookeeperClient(URL url)`: 用于创建和启动 `CuratorFramework` 实例
			```
			public class CuratorZookeeperClient extends AbstractZookeeperClient<CuratorWatcher> {
				private final CuratorFramework client;
				
				public CuratorZookeeperClient(URL url) {
					super(url);
					try {
						// 创建 CuratorFramework 构造器
						CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
								.connectString(url.getBackupAddress())
								.retryPolicy(new RetryNTimes(1, 1000))
								.connectionTimeoutMs(5000);
						String authority = url.getAuthority();
						if (authority != null && authority.length() > 0) {
							builder = builder.authorization("digest", authority.getBytes());
						}
						// 构建 CuratorFramework 实例
						client = builder.build();
						// 添加监听器
						client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
							@Override
							public void stateChanged(CuratorFramework client, ConnectionState state) {
								if (state == ConnectionState.LOST) {
									CuratorZookeeperClient.this.stateChanged(StateListener.DISCONNECTED);
								} else if (state == ConnectionState.CONNECTED) {
									CuratorZookeeperClient.this.stateChanged(StateListener.CONNECTED);
								} else if (state == ConnectionState.RECONNECTED) {
									CuratorZookeeperClient.this.stateChanged(StateListener.RECONNECTED);
								}
							}
						});
						// 启动客户端
						client.start();
					} catch (Exception e) {
						throw new IllegalStateException(e.getMessage(), e);
					}
				}
			}
			```
	2. 节点创建
		- 以 Zookeeper 为例，所谓的服务注册，本质上是将服务配置数据写入到 Zookeeper 的某个路径的节点下
		- `FailbackRegistry+register(URL url)`
		- `ZookeeperRegistry#doRegister(URL url)`: 调用了 Zookeeper 客户端创建服务节点
		- `AbstractZookeeperClient+create(String path, boolean ephemeral)`: 通过递归创建当前节点的上一级路径，然后再根据 `ephemeral` 的值决定创建临时还是持久节点
		- `CuratorZookeeperClient+createEphemeral(String path)`: 通过 Curator 框架创建节点
	- 整个过程可简单总结为：先创建注册中心实例，之后再通过注册中心实例注册服务
5. 订阅 override 数据
	- // 待补充

## Tips
- class`+`method: `public`
- class`#`method: `protected`
- class`-`method: `private`

## References
- [服务导出](http://dubbo.apache.org/zh-cn/docs/source_code_guide/export-service.html)