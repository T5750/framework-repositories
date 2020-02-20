# Dubbo Implementation

## 初始化过程细节
### 暴露服务
1. 只暴露服务端口
    - `ServiceConfig` 解析出的 URL 的格式为： `dubbo://service-host/com.foo.FooService?version=1.0.0`
2. 向注册中心暴露服务
    - `ServiceConfig` 解析出的 URL 的格式为: 
    `registry://registry-host/org.apache.dubbo.registry.RegistryService?`
    `export=URL.encode("dubbo://service-host/com.foo.FooService?version=1.0.0")`

### 引用服务
1. 直连引用服务
    - `ReferenceConfig` 解析出的 URL 的格式为：`dubbo://service-host/com.foo.FooService?version=1.0.0`
2. 从注册中心发现引用服务
    - `ReferenceConfig` 解析出的 URL 的格式为： `registry://registry-host/org.apache.dubbo.registry.RegistryService?`
    `refer=URL.encode("consumer://consumer-host/com.foo.FooService?version=1.0.0")`

## 远程调用细节
### 服务提供者暴露一个服务的详细过程
![dubbo_rpc_export](https://s1.wailian.download/2020/02/20/dubbo_rpc_export-min.jpg)

Dubbo 处理服务暴露的关键就在 `Invoker` 转换到 `Exporter` 的过程
- Dubbo 的实现: 在 `DubboProtocol` 类的 `export` 方法
- RMI 的实现: 在 `RmiProtocol` 类的 `export` 方法

### 服务消费者消费一个服务的详细过程
![dubbo_rpc_refer](https://s1.wailian.download/2020/02/20/dubbo_rpc_refer-min.jpg)

`ReferenceConfig` 类的 `init` 方法调用 `Protocol` 的 `refer` 方法生成 `Invoker` 实例，这是服务消费的关键

### 满眼都是 Invoker
![dubbo_rpc_invoke](https://s1.wailian.download/2020/02/20/dubbo_rpc_invoke-min.jpg)

服务消费者代码：
```
public class DemoClientAction {
    private DemoService demoService;
 
    public void setDemoService(DemoService demoService) {
        this.demoService = demoService;
    }
 
    public void start() {
        String hello = demoService.sayHello("world" + i);
    }
}
```
`DemoService` 就是上图中服务消费端的 proxy，用户代码通过这个 proxy 调用其对应的 `Invoker`，而该 `Invoker` 实现了真正的远程服务调用
```
public class DemoServiceImpl implements DemoService {
    public String sayHello(String name) throws RemoteException {
        return "Hello " + name;
    }
}
```
上面这个类会被封装成为一个 `AbstractProxyInvoker` 实例，并新生成一个 `Exporter` 实例。这样当网络通讯层收到一个请求后，会找到对应的 `Exporter` 实例，并调用它所对应的 `AbstractProxyInvoker` 实例，从而真正调用了服务提供者的代码

## 远程通讯细节
### 协议头约定
![data-format-min](https://s1.wailian.download/2020/01/24/data-format-min.jpg)

### 线程派发模型
![send-request-process-min](https://s1.wailian.download/2020/01/23/send-request-process-min.jpg)

- Dispather: `all`, `direct`, `message`, `execution`, `connection`
- ThreadPool: `fixed`, `cached`

## References
- [实现细节](http://dubbo.apache.org/zh-cn/docs/dev/implementation.html)