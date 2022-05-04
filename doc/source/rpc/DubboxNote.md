# Dubbox笔记

## 主要功能
* **支持REST风格远程调用（HTTP + JSON/XML)**：基于非常成熟的JBoss [RestEasy](http://resteasy.jboss.org/)框架，在dubbo中实现了REST风格（HTTP + JSON/XML）的远程调用，以显著简化企业内部的跨语言交互，同时显著简化企业对外的Open API、无线API甚至AJAX服务端等等的开发。事实上，这个REST调用也使得Dubbo可以对当今特别流行的“微服务”架构提供基础性支持。 另外，REST调用也达到了比较高的性能，在基准测试下，HTTP + JSON与Dubbo 2.x默认的RPC协议（即TCP + Hessian2二进制序列化）之间只有1.5倍左右的差距，详见文档中的基准测试报告。
* **支持基于Kryo和FST的Java高效序列化实现**：基于当今比较知名的[Kryo](https://github.com/EsotericSoftware/kryo)和[FST](https://github.com/RuedigerMoeller/fast-serialization)高性能序列化库，为Dubbo默认的RPC协议添加新的序列化实现，并优化调整了其序列化体系，比较显著的提高了Dubbo RPC的性能，详见文档中的基准测试报告。
* **支持基于Jackson的JSON序列化**：基于业界应用最广泛的[Jackson](http://jackson.codehaus.org/)序列化库，为Dubbo默认的RPC协议添加新的JSON序列化实现。
* **支持基于嵌入式Tomcat的HTTP remoting体系**：基于嵌入式tomcat实现dubbo的HTTP remoting体系（即dubbo-remoting-http），用以逐步取代Dubbo中旧版本的嵌入式Jetty，可以显著的提高REST等的远程调用性能，并将Servlet API的支持从2.5升级到3.1。（注：除了REST，dubbo中的WebServices、Hessian、HTTP Invoker等协议都基于这个HTTP remoting体系）。

## 文档资料
* [在Dubbo中开发REST风格的远程调用（RESTful Remoting）](https://dangdangdotcom.github.io/dubbox/rest.html)
* [在Dubbo中使用高效的Java序列化（Kryo和FST）](https://dangdangdotcom.github.io/dubbox/serialization.html)
* [使用JavaConfig方式配置dubbox](https://dangdangdotcom.github.io/dubbox/java-config.html)
* [Dubbo Jackson序列化使用说明](https://dangdangdotcom.github.io/dubbox/jackson.html)
* [Demo应用简单运行指南](https://dangdangdotcom.github.io/dubbox/demo.html)
* [Dubbox@InfoQ](http://www.infoq.com/cn/news/2014/10/dubbox-open-source)
* [Dubbox Wiki](https://github.com/dangdangdotcom/dubbox/wiki) （由社区志愿者自由编辑的）

## 步骤
- `git clone https://github.com/dangdangdotcom/dubbox`
- dubbox目录执行`mvn install -Dmaven.test.skip=true`来尝试编译一下dubbo
- 将dubbo的jar安装到本地maven库`mvn install:install-file -Dfile=C:\Users\Administrator\.m2\repository\com\alibaba\dubbo\2.8.4\dubbo-2.8.4.jar -DgroupId=com.alibaba -DartifactId=dubbo -Dversion=2.8.4 -Dpackaging=jar`
- gradle使用maven本地缓存库`mavenLocal()`
- IDEA: Project Structure -> Libraries -> Gradle: com.alibaba:dubbo:2.8.4 -> Add Classes and Sources

## Tips
Dubbox支持多种远程调用方式：
- Dubbo RPC（二进制序列化 + tcp协议）
- http invoker（二进制序列化 + http协议，至少在开源版本没发现对文本序列化的支持）
- hessian（二进制序列化 + http协议）
- WebServices（文本序列化 + http协议）
- REST风格远程调用（文本序列化 + http协议）

![rest-min](https://s0.wailian.download/2018/12/09/rest-min.jpg)

## Results
- dubbox-provider
    - GET: [http://localhost:8083/services/hello/world](http://localhost:8083/services/hello/world)
    - GET: [http://localhost:8083/services/users/1001](http://localhost:8083/services/users/1001)
    - GET: [http://localhost:8083/services/users/1001.xml](http://localhost:8083/services/users/1001.xml)
    - POST: [http://localhost:8083/services/users/register](http://localhost:8083/services/users/register)
    - JSON: `{"id":1001,"username":"hello"}`
- dubbox-consumer
    - GET: [http://localhost:8080/dubbox-consumer/hello?name=world](http://localhost:8080/dubbox-consumer/hello?name=world)
    - GET: [http://localhost:8080/dubbox-consumer/hello.json?name=world](http://localhost:8080/dubbox-consumer/hello.json?name=world)
    - GET: [http://localhost:8080/dubbox-consumer/users/getUser?id=1](http://localhost:8080/dubbox-consumer/users/getUser?id=1)
    - GET: [http://localhost:8080/dubbox-consumer/users/getUser.json?id=1](http://localhost:8080/dubbox-consumer/users/getUser.json?id=1)
    - GET: [http://localhost:8080/dubbox-consumer/u/getUser?id=1](http://localhost:8080/dubbox-consumer/u/getUser?id=1)
    - POST: [http://localhost:8080/dubbox-consumer/u/registerUser](http://localhost:8080/dubbox-consumer/u/registerUser)
    - GET: [http://localhost:8080/dubbox-consumer/bid/start](http://localhost:8080/dubbox-consumer/bid/start)
    - GET: [http://localhost:8080/dubbox-consumer/bid/throwNPE](http://localhost:8080/dubbox-consumer/bid/throwNPE)
- 序列化测试：`SerializationTest`

## References
- [Dubbox GitHub](https://github.com/dangdangdotcom/dubbox)