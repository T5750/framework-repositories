# Locks

## 乐观锁
乐观锁（Optimistic Lock），每次去拿数据的时候都认为别人不会修改，所以不会上锁，但是在提交更新的时候会判断一下在此期间别人有没有去更新这个数据。乐观锁适用于读多写少的应用场景，这样可以提高吞吐量

乐观锁一般来说有以下方式：
1. 使用数据版本（Version）记录机制实现
2. 使用时间戳（timestamp）
3. CAS（Compare And Set）

### Version
```
CREATE TABLE `product_stock` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `product_id` bigint(32) NOT NULL COMMENT '商品ID',
  `number` INT(8) unsigned NOT NULL DEFAULT 0 COMMENT '库存数量',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `modify_time` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_pid` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品库存表';
```
```
SELECT * FROM product_stock WHERE product_id=#{productId};
UPDATE product_stock SET number=number-1 WHERE product_id=#{productId} AND number=#{number};
```
**注意**：`UPDATE`语句的`WHERE`条件字句上需要建索引

[Mybatis-plus乐观锁插件](https://mp.baomidou.com/guide/optimistic-locker-plugin.html)，实现方式：
- 取出记录时，获取当前`version`
- 更新时，带上这个`version`
- 执行更新时，`set version = newVersion where version = oldVersion`
- 如果`version`不对，就更新失败

### CAS
Java线程之间的通信现在有了下面四种方式：
1. A线程写`volatile`变量，随后B线程读这个`volatile`变量
2. A线程写`volatile`变量，随后B线程用CAS更新这个`volatile`变量
3. A线程用CAS更新一个`volatile`变量，随后B线程用CAS更新这个`volatile`变量
4. A线程用CAS更新一个`volatile`变量，随后B线程读这个`volatile`变量

`concurrent`包通用化的实现模式：
1. 声明共享变量为`volatile`
2. 使用CAS的原子条件更新来实现线程之间的同步
3. 配合以`volatile`的读/写和CAS所具有的`volatile`读和写的内存语义来实现线程

缺点：
- ABA问题
- 循环时间长开销大
- 只能保证一个共享变量的原子操作

### Results
`LocksTest`

## 悲观锁
悲观锁（Pessimistic Lock），每次去拿数据的时候都认为别人会修改，所以每次在拿数据的时候都会上锁，这样别人想拿这个数据就会block直到它拿到锁
```
SET autocommit=0;
START TRANSACTION;
SELECT * FROM product_stock WHERE product_id=#{productId} FOR UPDATE;
UPDATE product_stock SET number=number-1 WHERE product_id=#{productId};
COMMIT;
```

- MySQL的共享锁和排它锁，是悲观锁的不同的实现
- Java的`synchronized`关键字

## 分布式锁
目前常见分布式锁实现有两种：基于Redis和基于Zookeeper，基于这两种业界也有开源的解决方案，例如[Redisson Distributed locks](https://link.jianshu.com/?t=https://github.com/redisson/redisson/wiki/8.-distributed-locks-and-synchronizers)、[Apache Curator Shared Lock](https://link.jianshu.com/?t=http://curator.apache.org/curator-recipes/shared-lock.html)

## 行锁
### 读锁
共享锁又叫做读锁（read lock），所有的事务只能对其进行读操作不能写操作，加上共享锁后在事务结束之前其他事务只能再加共享锁，除此之外其他任何类型的锁都不能再加了
```
begin;/begin work;/start transaction; // 三者选一就可以
SELECT * from TABLE where id=1 lock in share mode;
```

### 写锁
排它锁exclusive lock（也叫writer lock），若某个事物对某一行加上了排它锁，只能这个事务对其进行读写，在此事务结束之前，其他事务不能对其进行加任何锁，其他进程可以读取，不能进行写操作，需等待其释放
```
select status from TABLE where id=1 for update;
```

## 表锁
Innodb的行锁是在有索引的情况下，没有索引的表是锁定全表

## 死锁
死锁（Deadlock）：是指两个或两个以上的进程在执行过程中，因争夺资源而造成的一种互相等待的现象，若无外力作用，它们都将无法推进下去

解除正在死锁的状态有两种方法：
- 第一种
    1. 查询是否锁表: `show OPEN TABLES where In_use > 0;`
    2. 查询进程: `show processlist`
    3. 杀死进程: `kill id`
- 第二种
    1. 查看当前的事务: `SELECT * FROM INFORMATION_SCHEMA.INNODB_TRX;`
    2. 查看当前锁定的事务: `SELECT * FROM INFORMATION_SCHEMA.INNODB_LOCKS;`
    3. 查看当前等锁的事务: `SELECT * FROM INFORMATION_SCHEMA.INNODB_LOCK_WAITS;`
    4. 杀死进程: `kill id`

产生死锁的四个必要条件：
1. 互斥条件：一个资源每次只能被一个进程使用
2. 请求与保持条件：一个进程因请求资源而阻塞时，对已获得的资源保持不放
3. 不剥夺条件：进程已获得的资源，在末使用完之前，不能强行剥夺
4. 循环等待条件：若干进程之间形成一种头尾相接的循环等待资源关系

下列方法有助于最大限度地降低死锁：
1. 按同一顺序访问对象
2. 避免事务中的用户交互
3. 保持事务简短并在一个批处理中
4. 使用低隔离级别
5. 使用绑定连接

## References
- [MySQL 乐观锁与悲观锁](https://www.jianshu.com/p/f5ff017db62a)
- [乐观锁、悲观锁，这一篇就够了！](https://segmentfault.com/a/1190000016611415)
- [你了解乐观锁和悲观锁吗？](https://www.cnblogs.com/kismetv/p/10787228.html)
- [MySQL/InnoDB中，乐观锁、悲观锁、共享锁、排它锁、行锁、表锁、死锁概念的理解](https://segmentfault.com/a/1190000015815061)