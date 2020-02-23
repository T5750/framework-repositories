# Dubbo Impls Config Center

## 设计目的
配置中心的核心功能是作为Key-Value存储，Dubbo框架告知配置中心其关心的key，配置中心返回该key对应的value值。

按照应用场景划分，配置中心在Dubbo框架中主要承担以下职责：
- 作为外部化配置中心，即存储`dubbo.properties`配置文件，此时，key值通常为文件名如`dubbo.properties`，value则为配置文件内容
- 存储单个配置项，如各种开关项、常量值等
- 存储服务治理规则，此时key通常按照`服务名+规则类型`的格式来组织，而value则为具体的治理规则

## 扩展接口
* `org.apache.dubbo.configcenter.DynamicConfigurationFactory`
* `org.apache.dubbo.configcenter.DynamicConfiguration`

## 已知扩展
* `org.apache.dubbo.configcenter.support.zookeeper.ZookeeperDynamicConfigurationFactory`
* `org.apache.dubbo.configcenter.support.zookeeper.NacosDynamicConfigurationFactory`
* `org.apache.dubbo.configcenter.support.zookeeper.EtcdDynamicConfigurationFactory`
* `org.apache.dubbo.configcenter.support.zookeeper.ConsulDynamicConfigurationFactory`
* `org.apache.dubbo.configcenter.support.zookeeper.ApolloDynamicConfigurationFactory`

## 实现原理
### ZooKeeper
ZooKeeper提供了一个树状的存储模型，其实现原理如下：

![configcenter_zk_model](https://s1.wailian.download/2020/02/23/configcenter_zk_model-min.jpg)

namespace, group, key等分别对应不同层级的ZNode节点，而value则作为根ZNode节点的值存储

1. 外部化配置中心 `dubbo.properties`

    ![configcenter_zk_properties](https://s1.wailian.download/2020/02/23/configcenter_zk_properties-min.jpg)

2. 单个配置项

    ![configcenter_zk_singleitem](https://s1.wailian.download/2020/02/23/configcenter_zk_singleitem-min.jpg)

3. 服务治理规则

    ![configcenter_zk_rule](https://s1.wailian.download/2020/02/23/configcenter_zk_rule-min.jpg)

Dubbo同时支持应用、服务两种粒度的服务治理规则，对于这两种粒度，其key取值规则如下：
* 应用粒度 `应用名 + 规则后缀`。如：
    - `demo-application.configurators`
    - `demo-application.tag-router`
* 服务粒度 `服务接口名:[服务版本]:[服务分组] + 规则后缀`，其中服务版本、服务分组是可选的，如果它们有配置则在key中体现，没被配置则用`:`占位。如：
    - `org.apache.dubbo.demo.DemoService::.configurators`
    - `org.apache.dubbo.demo.DemoService:1.0.0:group1.configurators`

### Nacos
与ZooKeeper实现的对应关系如下：

![configcenter_nacos_model](https://s1.wailian.download/2020/02/23/configcenter_nacos_model-min.jpg)

## References
- [Dubbo配置中心](http://dubbo.apache.org/zh-cn/docs/dev/impls/config-center.html)