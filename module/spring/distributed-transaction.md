# Distributed Transaction

## Contents
- [分布式事务部署](../../doc/source/spring/transaction/distributedTransactionDeploy.md)
- [分布式事务解决方案笔记](../../doc/source/spring/transaction/distributedTransactionNote.md)
    - [分布式事务解决方案介绍](../../doc/source/spring/transaction/distributedTransactionIntroduction.md)
    - [消息发送一致性](../../doc/source/spring/transaction/distributedTransactionConsistency.md)
    - [消息发送一致性的异常流程处理](../../doc/source/spring/transaction/distributedTransactionException.md)
    - [常规MQ队列消息的处理流程和特点](../../doc/source/spring/transaction/distributedTransactionMessage.md)
    - [消息重复发送问题与业务接口的幂等性设计](../../doc/source/spring/transaction/distributedTransactionIdempotent.md)
    - [TCC两阶段型方案](../../doc/source/spring/transaction/distributedTransactionTcc.md)

## Runtime Environment
- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Spring Framework 4.3.8.RELEASE](https://spring.io/projects/spring-framework)
- [Dubbo 2.6.x](https://github.com/apache/dubbo/releases)
- [dubbo-admin](https://github.com/apache/dubbo-admin)
- [ZooKeeper 3.4.10](https://zookeeper.apache.org/)
- [Curator 4.0.1](http://curator.apache.org/)
- [DWZ 1.5.2](https://gitee.com/dwzteam/dwz_jui/tree/v1.5.2)
- [ActiveMQ 5.14.5 Release](http://activemq.apache.org/download.html)
- [MyBatis 3.4.0](http://www.mybatis.org/mybatis-3/)
- [Druid 1.1.10](https://github.com/alibaba/druid)
- [MySQL 5.6](http://www.mysql.com/)
- [Tomcat 7](http://tomcat.apache.org/)