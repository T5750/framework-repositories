# 分布式事务解决方案笔记

![distributedTransaction-problems](http://static.roncoo.com/images/yCGMB8jPtrsxKWf5PCQBpGCeKZKhzPBb.jpg)

电商购物支付流程中的分布式事务问题分析
1. 电商平台中创建订单：预留库存、预扣减积分、锁定优惠券，此时电商平台内各服务间会有分布式事务问题，因为此时已经要跨多个内部服务修改数据；
2. 支付平台中创建支付订单（选银行卡支付）：查询账户、查询限制规则，符合条件的就创建支付订单并跳转银行，此时不会有分布式事务问题，因为还不会跨服务改数据；
3. 银行平台中创建交易订单：查找账户、创建交易记录、判断账户余额并扣款、增加积分、通知支付平台，此时也会有分布式事务问题（如果是服务化架构的话）；
4. 支付平台收到银行扣款结果：更改订单状态、给账户加款、给积分帐户增加积分、生成会计分录、通知电商平台等，此时也会有分布式事务问题；
5. 电商平台收到支付平台的支付结果：更改订单状态、扣减库存、扣减积分、使用优惠券、增加消费积分等，系统内部各服务间调用也会遇到分布式事问题。

![distributedTransaction-process](http://static.roncoo.com/images/K4QcRFdKnbkT44AFFtzQYCAAX25FifDS.jpg)

支付平台收到银行扣款结果后的内部处理流程：
1. 支付平台的支付网关对银行通知结果进行校验，然后调用支付订单服务执行支付订单处理；
2. 支付订单服务根据银行扣款结果更改支付订单状态；
3. 调用资金账户服务给电商平台的商户账户加款（实际过程中可能还会有各种的成本计费；如果是余额支付，还可能是同时从用户账户扣款，给商户账户加款）；
4. 调用积分服务给用户积分账户增加积分；
5. 调用会计服务向会计（财务）系统写进交易原始凭证生成会计分录；
6. 调用通知服务将支付处理结果通知电商平台。

分布式事务问题的代码场景
```
/** 支付订单处理**/
@Transactional(rollbackFor = Exception.class)
public void completeOrder() {
	orderDao.update(); // 订单服务本地更新订单状态
	accountService.update(); // 调用资金账户服务给资金帐户加款
	pointService.update(); // 调用积分服务给积分帐户增加积分
	accountingService.insert(); // 调用会计服务向会计系统写入会计原始凭证
	merchantNotifyService.notify(); // 调用商户通知服务向商户发送支付结果通知
}
```

## 分布式事务解决方案介绍
[分布式事务解决方案介绍](distributedTransactionIntroduction.md)

## 1 可靠消息的最终一致性方案
异步确保型（可靠消息最终一致）
- 对应支付系统会计异步记账业务
- 银行通知结果信息存储与驱动订单处理
- 可以异步，但数据绝对不能丢，而且一定要记账成功

![distributedTransactionFinal-min](https://www.wailian.work/images/2019/01/08/distributedTransactionFinal-min.png)

设计分析维度：
- 消息发送一致性的正向流程
- 消息发送一致性的异常处理流程
- 消息投递（消费）的正向流程
- 消息投递（消费）的异常处理流程

### 1.1 可靠消息的最终一致性方案1（本地消息服务）

![distributedTransactionFinalLocal-min](https://www.wailian.work/images/2019/01/14/distributedTransactionFinalLocal-min.jpg)

优点：
1. 消息时效性比较高
1. 从应用设计开发的角度，实现了消息数据的可靠性，消息数据的可靠性不依赖于MQ中间件，弱化了对MQ中间件特性的依赖
1. 方案轻量，容易实现

弊端/局限：
1. 与具体的业务场景绑定，耦合性强，不可共用
1. 消息数据与业务数据同库，占用业务系统资源
1. 业务系统在使用关系型数据库的情况下，消息服务性能会受关系型数据库并发性能的局限

### 1.2 可靠消息的最终一致性方案2（独立消息服务）
![distributedTransactionFinalConsistency-min](https://www.wailian.work/images/2019/01/11/distributedTransactionFinalConsistency-min.png)

优点：
1. 消息服务独立部署、独立维护、独立伸缩
1. 消息存储可以按需选择不同的数据库来集成实现
1. 消息服务可以被相同的使用场景共用，降低重复建设消息服务的成本
1. 从应用（分布式服务）设计开发的角度实现了消息数据的可靠性，消息数据的可靠性不依赖于MQ中间件，弱化了对MQ中间件特性的依赖
1. 降低了业务系统与消息系统间的耦合，有利于系统的扩展维护

弊端/局限：
1. 一次消息发送需要两次请求
1. 主动方应用系统需要实现业务操作状态校验查询接口

#### 1.2.1 应用部署
1. 创建数据库、分库导入SQL脚本
1. 安装和配置ActiveMQ中间件
1. 安装ZooKeeper、DubboAdmin
1. 构建部署包，按顺序打包
1. 准备应用部署目录
1. 上传应用部署程序，按顺序启动应用

[Distributed Transaction Deploy](distributedTransactionDeploy.md)

#### 1.2.2 应用测试
重点：保障消息服务的可用性和可靠性
1. 订单服务故障
1. 会计服务故障
1. 实时消息服务（MQ）故障

#### 1.2.3 优化建议
- 数据库：Redis（可靠性、可用性、性能），特别注意持久化配置`appendfsync always`
- 消息日志表：适用于被动方应用业务的幂等性，判断比较麻烦或比较耗性能的情况，但会增加一定的开发工作量
- 分布式任务调度
- 实时消息服务：RocketMQ、RabbitMQ等（支持队列功能、消息数据的可靠性不依赖于MQ）
- 独立业务使用独立消息服务（提高性能、隔离解耦，但增加维护成本和工作量）
- 其它服务框架

## 2 最大努力通知型方案
最大努力通知型
- 对应支付系统的商户通知业务场景
- 按规律进行通知，不保证数据一定能通知成功，但会提供可查询操作接口进行核对

![distributedTransactionMax-min](https://www.wailian.work/images/2019/01/08/distributedTransactionMax-min.png)

### 2.1 应用部署
1. 导入数据库脚本：`rp_notify_record.sql`，`rp_notify_record_log.sql`
1. 更新项目源码：`pay-service-notify-api`，`pay-service-notify`，`pay-app-notify`
1. 重新构建部署包
1. 上传应用部署程序，按顺序启动应用

### 2.2 应用测试
1. 验证消息重发规则是否可行并准确
1. 验证消息重发恢复机制

### 2.3 优化建议
- 通知记录、通知日志可视化管理、手工触发等功能（可参考可靠消息管理）
- 可以考虑把通知服务做得更通用（通知队列分区、不同队列不同规则等）
- 保证通知服务的性能，必要时可独立数据库（也可换用Redis等）
- `pay-app-notify`启动时通知数据初始化功能的优化
- `pay-app-notify`的内存调优和流量控制（`tasks.size()`）
- 要求通知被动方处理通知接口的业务接口要实现幂等（注意点）

## 3 TCC两阶段型方案
[TCC两阶段型方案](distributedTransactionTcc.md)

## 实战应用场景
在支付系统中的实战应用场景

![distributedTransactionInAction-min](https://www.wailian.work/images/2019/01/17/distributedTransactionInAction-min.png)

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

## 可靠消息的生产与消费的正向流程
![distributedTransactionQueue-min](https://www.wailian.work/images/2019/01/11/distributedTransactionQueue-min.png)

1. 主动方应用先把消息发给消息中间件，消息状态标记为“待确认”；
2. 消息中间件收到消息后，把消息持久化到消息存储中，但并不向被动方应用投递消息；
3. 消息中间件返回消息持久化结果（成功/失败），主动方应用根据返回结果进行判断如何进行业务操作处理：
    - 失败：放弃业务操作处理，结束（必要时向上层返回失败结果）；
    - 成功：执行业务操作处理；
4. 业务操作完成后，把业务操作结果（成功/失败）发送给消息中间件；
5. 消息中间件收到业务操作结果后，根据业务结果进行处理；
    - 失败：删除消息存储中的消息，结束；
    - 成功：更新消息存储中的消息状态为“待发送（可发送）”；
6. 被动方应用监听并接收“待发送”状态的消息，执行业务处理；
7. 业务处理完成后，向消息中间件发送ACK，确认消息已经收到（消息中间件将从队列中删除该消息）。

## References
- [微服务架构的分布式事务解决方案](https://www.roncoo.com/view/20)