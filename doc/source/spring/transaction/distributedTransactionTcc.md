# 分布式事务解决方案笔记 Segment7

## 3 TCC两阶段型方案
TCC（两阶段型、补偿型）
- 对应支付系统的订单账户操作：订单处理、资金账户处理、积分账户处理
- 实时性要求比较高，数据必须可靠

![distributedTransactionTCC-min](https://www.wailian.work/images/2019/01/08/distributedTransactionTCC-min.png)

### 3.1 样例部署
- [tcc-transaction 1.1.5](https://github.com/changmingxie/tcc-transaction/tree/master) 优点：框架的工程结构清晰、轻量、有持续维护更新、社区活跃、有比较完善的应用样例、文档比较完善
- `tcc-transaction`项目工程结构：

Module | Comment
----|----
`tcc-transaction-api` | 框架核心工程
`tcc-transaction-core` | 框架核心工程
`tcc-transaction-server` | 事务活动日志管理
`tcc-transaction-spring` | 框架核心工程
`tcc-transaction-tutorial-sample` | 结合Dubbo的使用样例
`tcc-transaction-unit-test` | 单元测试工程

1. 准备环境：Dubbo注册中心、Dubbo管控台、MySQL数据库、应用部署脚本
1. 调整项目配置：JDBC配置、服务注册中心配置、Maven库配置
1. 导入数据库脚本（建库、建表）：`create_db_cap.sql`，`create_db_ord.sql`，`create_db_red.sql`，`create_db_tcc.sql`
1. 部署服务（要用到Dubbo注册）：
    - `tcc-transaction-dubbo-order`（业务订单服务，主服务）
    - `tcc-transaction-dubbo-capital`（资金账户服务，从服务）
    - `tcc-transaction-dubbo-redpacket`（红包账户服务，从服务）
1. 部署服务消费端：`tcc-transaction-dubbo-web-trade`

### 3.2 应用详解
1. 基于`framework-repositories/spring/tcc-transaction`
1. 在需要提供TCC操作的服务接口工程中（从业务服务的接口工程中）
    1. `pay-service-account-api`，`pay-service-point-api`增加`tcc-transaction-api`包引用
    1. 同时，在需要进行TCC控制的业务接口方法中，加入`TransactionContext`参数，并且是要放在第1个参数中，如：`RpAccountTransactionService.creditToAccountTcc()`
1. 在TCC主业务服务和从业务服务的接口实现工程中，`pay-service-account`，`pay-service-point`，`pay-service-trade`
    1. 引用`tcc-transaction-spring`包
    1. 在Spring主配置（`spring-context.xml`）中加载`tcc-transaction.xml`，用于启动TCC控制
    1. 在数据库配置文件中，加入`tcc-transaction`的事务控制配置：
	```
	<!--======= TCC Transaction Begin ================= -->
	<!-- 设置恢复策略(可选），V1.1.0 新增定时任务配置 -->
	<bean class="t5750.tcctransaction.spring.recover.DefaultRecoverConfig">
		<!-- maxRetryCount表示一个事务最多尝试恢复次数，超过将不在自动恢复，需要人工干预，默认是30次 -->
		<property name="maxRetryCount" value="30"/>
		<!-- recoverDuration表示一个事务日志当超过一定时间间隔后没有更新就会被认为是发生了异常，需要恢复，恢复Job将扫描超过这个时间间隔依旧没有更新的事务日志，并对这些事务进行恢复，时间单位是秒，默认是120秒 -->
		<property name="recoverDuration" value="120"/>
		<!-- cronExpression表示恢复Job触发间隔配置，默认是(每分钟)0 */1 * * * ? -->
		<property name="cronExpression" value="0 */1 * * * ?"/>
	</bean>

	<!-- TCC 业务活动日志（事务日志）的数据源 -->
	<bean id="tccDataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="clone">
		<!-- 基本属性driverClassName、 url、user、password -->
		<property name="driverClassName" value="${account.jdbc.driver}"/>
		<property name="url" value="${account.tcc.jdbc.url}"/>
		<property name="username" value="${account.jdbc.username}"/>
		<property name="password" value="${account.jdbc.password}"/>
		<!-- 配置初始化大小、最小、最大 -->
		<!-- 通常来说，只需要修改initialSize、minIdle、maxActive -->
		<!-- 初始化时建立物理连接的个数，缺省值为0 -->
		<property name="initialSize" value="${jdbc.initialSize}"/>
		<!-- 最小连接池数量 -->
		<property name="minIdle" value="${jdbc.minIdle}"/>
		<!-- 最大连接池数量，缺省值为8 -->
		<property name="maxActive" value="${jdbc.maxActive}"/>
		<!-- 获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁。 -->
		<property name="maxWait" value="${jdbc.maxWait}"/>
	</bean>

	<!-- 使用SpringJdbc事务库 -->
	<bean id="transactionRepository" class="t5750.tcctransaction.spring.repository.SpringJdbcTransactionRepository">
		<property name="dataSource" ref="tccDataSource"/>
		<property name="domain" value="ACCOUNT"/>
		<property name="tbSuffix" value="_ACCOUNT"/>
	</bean>
	<!--======= TCC Transaction Begin ================= -->
	```
1. 在`pay-common-config`工程中，`jdbc.properties`中加入TCC事务日志库的配置，并做相应调整：
	```
	account.tcc.jdbc.url=jdbc:mysql://127.0.0.1:3306/TCC?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true
	trade.tcc.jdbc.url=jdbc:mysql://127.0.0.1:3306/TCC?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true
	point.tcc.jdbc.url=jdbc:mysql://127.0.0.1:3306/TCC?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true
	```
1. 创建TCC事务活动日志库表：`pay_tcc_table.sql`（表名通过跟`TransactionRepository`关联）
1. 工程`tcc-transaction-server`中的配置
    - 在`jdbc-domain-suffix.properties`中加上`domain`与事务日志表后缀`tbSuffix`的关联映射配置，真实项目中按业务需求进行设置：
	```
	ACCOUNT=_ACCOUNT
	POING=_POING
	TRADE=_TRADE
    ```
    - 在`tcc-transaction-server.properties`配置文件中，把`tcc_domain`的值设置为`tcc-transaction-server`部署后URL上的工程名，要不然页面引用不了css样式和js，查询页面就不生效
1. TCC在支付系统中的业务代码实现

### 3.3 应用部署与测试
1. 更新TCC方案代码
1. 建库、建表、准备应用部署脚本
1. 更新部署服务（TCC）
    - `pay-service-trade`（交易服务，主服务）
    - `pay-service-account`（账户服务，从服务）
    - `pay-service-point`（积分服务，从服务）
1. TCC方案验证测试

### 3.4 处理流程
![tccTransaction-min](http://www.wailian.work/images/2019/01/22/tccTransaction-min.png)

- Try流程：主服务A（交易服务）、从服务B（资金账户服务）、从服务C（积分账户服务）
- Confirm流程：主服务、从服务
- Cancel流程：主服务、从服务
- 异常处理流程：Try、Confirm、Cancel