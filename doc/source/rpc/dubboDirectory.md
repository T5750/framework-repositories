# Dubbo Directory

## 简介
集群容错源码包含四个部分，分别是服务目录 `Directory`、服务路由 `Router`、集群 `Cluster` 和负载均衡 `LoadBalance`。
- 服务目录中存储了一些和服务提供者有关的信息，通过服务目录，服务消费者可获取到服务提供者的信息，比如 ip、端口、服务协议等
- 服务目录在获取注册中心的服务配置信息后，会为每条配置信息生成一个 `Invoker` 对象，并把这个 `Invoker` 对象存储起来，这个 `Invoker` 才是服务目录最终持有的对象
- 服务目录可以看做是 `Invoker` 集合，且这个集合中的元素会随注册中心的变化而进行动态调整

## 继承体系
服务目录目前内置的实现有两个，分别为 `StaticDirectory` 和 `RegistryDirectory`，它们均是 `AbstractDirectory` 的子类

![directory-inherit-hierarchy-min](https://s1.wailian.download/2020/01/22/directory-inherit-hierarchy-min.jpg)

## 源码分析
`AbstractDirectory+list(Invocation invocation)`:
1. 调用 `doList` 获取 `Invoker` 列表
2. 根据 `Router` 的 `getUrl` 返回值为空与否，以及 `runtime` 参数决定是否进行服务路由。如果 `runtime` 为 `true`，那么每次调用服务前，都需要进行服务路由。这个对性能造成影响，配置时需要注意

### 1 StaticDirectory
`StaticDirectory`: 静态服务目录，它内部存放的 `Invoker` 是不会变动的

### 2 RegistryDirectory
`RegistryDirectory`: 是一种动态服务目录，实现了 `NotifyListener` 接口
- 当注册中心服务配置发生变化后，`RegistryDirectory` 可收到与当前服务相关的变化
- 收到变更通知后，`RegistryDirectory` 可根据配置变更信息刷新 `Invoker` 列表

#### 2.1 列举 Invoker
`doList(Invocation invocation)`: 可以看做是对 `methodInvokerMap` 变量的读操作

#### 2.2 接收服务变更通知
`notify(List<URL> urls)`:
1. 根据 `url` 的 `category` 参数对 `url` 进行分门别类存储
2. 通过 `toRouters` 和 `toConfigurators` 将 `url` 列表转成 `Router` 和 `Configurator` 列表
3. 调用 `refreshInvoker` 方法刷新 `Invoker` 列表

#### 2.3 刷新 Invoker 列表
- `refreshInvoker(List<URL> invokerUrls)`:
	* 根据入参 `invokerUrls` 的数量和协议头判断是否禁用所有的服务
		+ 如果禁用，则将 `forbidden` 设为 `true`，并销毁所有的 `Invoker`
		+ 若不禁用，则将 `url` 转成 `Invoker`，得到 `<url, Invoker>` 的映射关系
	* 进一步进行转换，得到 `<methodName, Invoker 列表>` 映射关系
	* 进行多组 `Invoker` 合并操作，并将合并结果赋值给 `methodInvokerMap`。`methodInvokerMap` 变量在 `doList` 方法中会被用到，`doList` 会对该变量进行读操作，在这里是写操作
	* 当新的 `Invoker` 列表生成后，销毁无用的 `Invoker`
- `Map<String, Invoker<T>> toInvokers(List<URL> urls)`: `url` 转成 `Invoker`
	* 对服务提供者 `url` 进行检测，若服务消费端的配置不支持服务端的协议，或服务端 `url` 协议头为 `empty` 时，`toInvokers` 均会忽略服务提供方 `url`
	* 合并 `url`
	* 访问缓存，尝试获取与 `url` 对应的 `invoker`
		+ 如果缓存命中，直接将 `Invoker` 存入 `newUrlInvokerMap` 中即可
		+ 如果未命中，则需新建 `Invoker`
- `toMethodInvokers(Map<String, Invoker<T>> invokersMap)`: 方法名 -> `Invoker` 列表
	1. 对入参进行遍历，然后从 `Invoker` 的 `url` 成员变量中获取 `methods` 参数，并切分成数组。随后以方法名为键，`Invoker` 列表为值，将映射关系存储到 `newMethodInvokerMap` 中
	2. 分别基于类和方法对 `Invoker` 列表进行路由操作
	3. 对 `Invoker` 列表进行排序，并转成不可变列表
- `toMergeMethodInvokerMap(Map<String, List<Invoker<T>>> methodMap)`:
	* 生成 `group` 到 `Invoker` 列表的映射关系表，若关系表中的映射关系数量大于1，表示有多组服务
	* 通过集群类合并每组 `Invoker`，并将合并结果存储到 `groupInvokers` 中
	* 将方法名与 `groupInvokers` 存到到 `result` 中，并返回
- `destroyUnusedInvokers(Map<String, Invoker<T>> oldUrlInvokerMap, Map<String, Invoker<T>> newUrlInvokerMap)`: 删除无用 `Invoker`
	* 通过 `newUrlInvokerMap` 找出待删除 `Invoker` 对应的 `url`，并将 `url` 存入到 `deleted` 列表中
	* 再遍历 `deleted` 列表，并从 `oldUrlInvokerMap` 中移除相应的 `Invoker`，销毁之

刷新 `Invoker` 列表的整个过程，简单总结：
1. 检测入参是否仅包含一个 `url`，且 `url` 协议头为 `empty`
2. 若第一步检测结果为 `true`，表示禁用所有服务，此时销毁所有的 `Invoker`
3. 若第一步检测结果为 `false`，此时将入参转为 `Invoker` 列表
4. 对上一步逻辑生成的结果进行进一步处理，得到方法名到 `Invoker` 的映射关系表
5. 合并多组 `Invoker`
6. 销毁无用 `Invoker`

## References
- [服务字典](http://dubbo.apache.org/zh-cn/docs/source_code_guide/directory.html)