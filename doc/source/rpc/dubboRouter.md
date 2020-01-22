# Dubbo Router

## 简介
服务路由包含一条路由规则，路由规则决定了服务消费者的调用目标，即规定了服务消费者可调用哪些服务提供者。Dubbo 目前提供了三种服务路由实现：
- 条件路由 `ConditionRouter`
- 脚本路由 `ScriptRouter`
- 标签路由 `TagRouter`，暂时还未发布，该实现预计会在 2.7.x 版本中发布

## 源码分析
条件路由规则由两个条件组成，分别用于对服务消费者和提供者进行匹配。

条件路由规则的格式：`[服务消费者匹配条件] => [服务提供者匹配条件]`
- 如果服务消费者匹配条件为空，表示不对服务消费者进行限制
- 如果服务提供者匹配条件为空，表示对某些服务消费者禁用服务
- `host = 10.20.153.10 => host = 10.20.153.11`: 表示 IP 为 10.20.153.10 的服务消费者**只可**调用 IP 为 10.20.153.11 机器上的服务，不可调用其他机器上的服务

### 1 表达式解析
- `ConditionRouter+ConditionRouter(URL url)`:
	* 对路由规则做预处理
	* 调用 `parseRule` 方法分别对服务提供者和消费者规则进行解析，
	* 将解析结果赋值给 `whenCondition` 和 `thenCondition` 成员变量
- `MatchPair`
	* `matches`: 匹配的条件
	* `mismatches`: 不匹配的条件
- `ConditionRouter-parseRule(String rule)`
- `ROUTE_PATTERN = Pattern.compile("([&!=,]*)\\s*([^&!=,\\s]+)")`:
	* 第一个括号内的表达式：用于匹配"&", "!", "=" 和 "," 等符号
	* 第二个括号内的：用于匹配英文字母，数字等字符

### 2 服务路由
- `ConditionRouter+route(List<Invoker<T>> invokers, URL url, Invocation invocation)`:
	* 调用 `matchWhen` 对服务消费者进行匹配
		+ 如果匹配失败，直接返回 `Invoker` 列表
		+ 如果匹配成功，再对服务提供者进行匹配，匹配逻辑封装在了 `matchThen` 方法中
- `matchWhen(URL url, Invocation invocation)`
- `matchThen(URL url, URL param)`
	* `url`: 服务提供者 `url`
	* `param`: 服务消费者 `url`
- `matchCondition(Map<String, MatchPair> condition, URL url, URL param, Invocation invocation)`:
	* `whenCondition` 为服务消费者匹配条件
	* `url` 源自 `route` 方法的参数列表，该参数由外部类调用 `route` 方法时传入
	```
	// RegistryDirectory -> getConsumerUrl()
	private List<Invoker<T>> route(List<Invoker<T>> invokers, String method) {
		invokers = router.route(invokers, getConsumerUrl(), invocation);
	}
	```
- `MatchPair-isMatch(String value, URL param)`: 条件匹配调用的逻辑
- `UrlUtils+isMatchGlobPattern(String pattern, String value, URL param)`: 调用重载方法
- `UrlUtils+isMatchGlobPattern(String pattern, String value)`: 对普通的匹配过程，以及”引用消费者参数“和通配符匹配等特性提供了支持

`MatchPair-isMatch(String value, URL param)`匹配逻辑总结：

条件 | 过程
---|---
1 `matches` 非空，`mismatches` 为空 | 遍历 `matches` 集合元素，并与入参进行匹配。只要有一个元素成功匹配入参，即可返回 `true`。若全部失配，则返回 `false`
2 `matches` 为空，`mismatches` 非空 | 遍历 `mismatches` 集合元素，并与入参进行匹配。只要有一个元素成功匹配入参，立即 `false`。若全部失配，则返回 `true`
3 `matches` 非空，`mismatches` 非空 | 优先使用 `mismatches` 集合元素对入参进行匹配，只要任一元素与入参匹配成功，就立即返回 `false`，结束方法逻辑。否则再使用 `matches` 中的集合元素进行匹配，只要有任意一个元素匹配成功，即可返回 `true`。若全部失配，则返回 `false`
4 `matches` 为空，`mismatches` 为空 | 直接返回 `false`

## References
- [服务路由](http://dubbo.apache.org/zh-cn/docs/source_code_guide/router.html)