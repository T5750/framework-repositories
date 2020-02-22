# Dubbo Impls Protocol

## 扩展说明
RPC 协议扩展，封装远程调用细节

契约：
* 当用户调用 `refer()` 所返回的 `Invoker` 对象的 `invoke()` 方法时，协议需相应执行同 URL 远端 `export()` 传入的 `Invoker` 对象的 `invoke()` 方法
* 其中，`refer()` 返回的 `Invoker` 由协议实现，协议通常需要在此 `Invoker` 中发送远程请求，`export()` 传入的 `Invoker` 由框架实现并传入，协议不需要关心

注意：
* 协议不关心业务接口的透明代理，以 `Invoker` 为中心，由外层将 `Invoker` 转换为业务接口
* 协议不一定要是 TCP 网络通讯，比如通过共享文件，IPC 进程间通讯等

## 扩展接口
* `org.apache.dubbo.rpc.Protocol`
* `org.apache.dubbo.rpc.Exporter`
* `org.apache.dubbo.rpc.Invoker`

```java
public interface Protocol {
    /**
     * 暴露远程服务：<br>
     * 1. 协议在接收请求时，应记录请求来源方地址信息：RpcContext.getContext().setRemoteAddress();<br>
     * 2. export()必须是幂等的，也就是暴露同一个URL的Invoker两次，和暴露一次没有区别。<br>
     * 3. export()传入的Invoker由框架实现并传入，协议不需要关心。<br>
     * 
     * @param <T> 服务的类型
     * @param invoker 服务的执行体
     * @return exporter 暴露服务的引用，用于取消暴露
     * @throws RpcException 当暴露服务出错时抛出，比如端口已占用
     */
    <T> Exporter<T> export(Invoker<T> invoker) throws RpcException;
 
    /**
     * 引用远程服务：<br>
     * 1. 当用户调用refer()所返回的Invoker对象的invoke()方法时，协议需相应执行同URL远端export()传入的Invoker对象的invoke()方法。<br>
     * 2. refer()返回的Invoker由协议实现，协议通常需要在此Invoker中发送远程请求。<br>
     * 3. 当url中有设置check=false时，连接失败不能抛出异常，需内部自动恢复。<br>
     * 
     * @param <T> 服务的类型
     * @param type 服务的类型
     * @param url 远程服务的URL地址
     * @return invoker 服务的本地代理
     * @throws RpcException 当连接服务提供方失败时抛出
     */
    <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException;
}
```

## 已知扩展
* `org.apache.dubbo.rpc.protocol.injvm.InjvmProtocol`
* `org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol`
* `org.apache.dubbo.rpc.protocol.rmi.RmiProtocol`
* `org.apache.dubbo.rpc.protocol.http.HttpProtocol`
* `org.apache.dubbo.rpc.protocol.http.hessian.HessianProtocol`

## References
- [协议扩展](http://dubbo.apache.org/zh-cn/docs/dev/impls/protocol.html)