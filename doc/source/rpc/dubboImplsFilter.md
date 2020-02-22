# Dubbo Impls Filter

## 扩展说明
服务提供方和服务消费方调用过程拦截，Dubbo 本身的大多功能均基于此扩展点实现，每次远程方法执行，该拦截都会被执行，请注意对性能的影响

约定：
* 用户自定义 filter 默认在内置 filter 之后
* 特殊值 `default`，表示缺省扩展点插入的位置。比如：`filter="xxx,default,yyy"`，表示 `xxx` 在缺省 filter 之前，`yyy` 在缺省 filter 之后
* 特殊符号 `-`，表示剔除。比如：`filter="-foo1"`，剔除添加缺省扩展点 `foo1`。比如：`filter="-default"`，剔除添加所有缺省扩展点
* provider 和 service 同时配置的 filter 时，累加所有 filter，而不是覆盖。比如：`<dubbo:provider filter="xxx,yyy"/>` 和 `<dubbo:service filter="aaa,bbb" />`，则 `xxx`,`yyy`,`aaa`,`bbb` 均会生效。如果要覆盖，需配置：`<dubbo:service filter="-xxx,-yyy,aaa,bbb" />`

## 扩展接口
`org.apache.dubbo.rpc.Filter`

## 已知扩展
* `org.apache.dubbo.rpc.filter.EchoFilter`
* `org.apache.dubbo.rpc.filter.GenericFilter`
* `org.apache.dubbo.rpc.filter.GenericImplFilter`
* `org.apache.dubbo.rpc.filter.TokenFilter`
* `org.apache.dubbo.rpc.filter.AccessLogFilter`
* `org.apache.dubbo.rpc.filter.CountFilter`
* `org.apache.dubbo.rpc.filter.ActiveLimitFilter`
* `org.apache.dubbo.rpc.filter.ClassLoaderFilter`
* `org.apache.dubbo.rpc.filter.ContextFilter`
* `org.apache.dubbo.rpc.filter.ConsumerContextFilter`
* `org.apache.dubbo.rpc.filter.ExceptionFilter`
* `org.apache.dubbo.rpc.filter.ExecuteLimitFilter`
* `org.apache.dubbo.rpc.filter.DeprecatedFilter`

## References
- [调用拦截扩展](http://dubbo.apache.org/zh-cn/docs/dev/impls/filter.html)