# 分布式事务解决方案笔记

## 可靠消息的最终一致性方案
异步确保型（可靠消息最终一致）

![distributedTransactionFinal-min](http://www.wailian.work/images/2019/01/08/distributedTransactionFinal-min.png)

## 最大努力通知型方案
最大努力通知型

![distributedTransactionMax-min](http://www.wailian.work/images/2019/01/08/distributedTransactionMax-min.png)

## TCC两阶段型方案
TCC（两阶段型、补偿型）

![distributedTransactionTCC-min](http://www.wailian.work/images/2019/01/08/distributedTransactionTCC-min.png)

## 实战应用场景
在支付系统中的实战应用场景

![distributedTransactionInAction-min](http://www.wailian.work/images/2019/01/08/distributedTransactionInAction-min.png)

可靠消息服务方案的特点：
1. 可独立部署、独立伸缩（扩展性）；
1. 兼容所有实现JMS标准的MQ中间件；
1. 能降低业务系统与消息系统间的耦合性；
1. 可实现数据可靠的前提下确保最终一致。

TCC方案的特点：
1. 不与具体的服务框架耦合（在RPC框架中通用）；
1. 位于业务服务层，而非资源层；
1. 可以灵活选择业务资源的锁定粒度；
1. 适用于强隔离性、严格一致性要求的业务场景；
1. 适用于执行时间较短的业务。

## References
- [微服务架构的分布式事务解决方案](https://www.roncoo.com/view/20)