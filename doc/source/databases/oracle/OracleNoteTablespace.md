# Oracle笔记 Chapter 5

## 5.1.1 表空间使用
Oracle创建表空间
1. 创建临时表空间
	```sql
	create temporary tablespace zcdb_temp
	tempfile 'D:\oracle\datafile\zcdb_temp.dbf'
	size 1024m
	autoextend on;
	next 50m maxsize 20480m
	extent management local;
	```
1. 创建数据表空间
	```sql
	create tablespace zcdb
	datafile 'D:\oracle\datafile\zcdb.dbf'
	size 10240m
	autoextend on;
	next 50m maxsize 20480m
	extent management local;
	--alter tablespace zcdb add datafile 'D:\oracle\datafile\zcdb2.dbf' size 10240m autoextend on;
	```
1. 创建用户，并指定表空间
	```sql
	create user zcdb identified by zcdb
	default tablespace zcdb
	temporary tablespace zcdb_temp;
	```
1. 给用户授予权限
	```sql
	grant dba to zcdb;
	```

## 5.1.2 查看表空间
查看Oracle表空间和使用率：
```sql
SELECT t.tablespace_name, round(SUM(bytes/(1024 * 1024)),0) ts_size
FROM dba_tablespaces t, dba_data_fils d
WHERE t.tablespace_name = d.tablespace_name
GROUP BY t.tablespace_name;
```
查看数据库实例名称：
```sql
select instance_name from v$instance;
```

## 5.2.1 Oracle表类型
表的功能：存储、管理数据的基本单元（二维表：有行和列组成）
1. 堆表：heap table：数据存储时，行是无序的，对它的访问采用全表扫描
1. 分区表，表>2G
1. 索引组织表（IOT）
1. 簇表
1. 临时表
1. 压缩表
1. 嵌套表

日常开发使用的分表分库的问题，其实都是基OLTP和OLAP的业务前提，然后对数据做切分，例如垂真、水平切分。在Oracle里早就有这个概念了，就是使用分区的方案。

## 5.2.2 OLTP和OLAP
- 在互联网时代，海量数据的存储与访问成为系统设计与使用的瓶颈问题，对海量数据处理，按照使用场景，主要分为两种类型：联机事务处理（OLTP）和联机分析处理（OLAP）。
- 联机事务处理（OLTP）也称为面向交易的处理系统，其基本特征是原始数据可以立即传送到计算中心进行处理，并在很短的时间内给出处理结果。
- 联机分析处理（OLAP）是指通过多维的方式对数据进行分析、查询和报表，可以同数据挖掘工具、统计分析工具配合使用，增强决策分析能力。

## 5.2.3 OLTP和OLAP区别

对比项 | OLTP | OLAP
---|-----|-----
系统功能 | 日常交易处理 | 统计、分析、报表
DB设计 | 面向实时交易类应用 | 面向统计分析类应用
数据处理 | 当前的，最新的，细节的，二维的，分立的 | 历史的，聚集的，多维的，集成的，统一的
实时性 | 实时读写要求高 | 实时读写要求低
事务 | 强一致性 | 弱事务
分析要求 | 低、简单 | 高、复杂

## 5.2.4 关系型数据库和NoSQL数据库对比

对比项 | 关系型数据库 | NoSQL数据库
---|-----|-----
特点 | 数据关系模型基于关系模型，结构化存储，完整性约束。基于二维表及其之间的联系，需要连接、并、交、差、除等数据操作。采用结构化的查询语言（SQL）做数据读写。操作需要数据的一致性，需要事务甚至是强一致性。 | 非结构化的存储。基于多维关系模型。具有特有的使用场最。
优点 | 保持数据的一致性（事务处理）。可以进行`join`等复杂查询。通用化，技术成熟。 |  高并发，大数据下读写能力较强。基本支持分布式，易于扩展，可伸缩。简单，弱结构化存储。
缺点 | 数据读写必须经过SQL解析，大量数据、高并发下读写性能不足。对数据做读写，或修改数据结构时需要加锁，影响并发操作。无法适应非结构化存储。扩展困难。 | `join`等复杂操作能力较弱。事务支持较弱。通用性差。无完整约束复杂业务场景支持较差。

## 5.2.5 何为数据切分
- 简单来说，指通过某种特定的条件和规则，将放在同一个数据库中的数据分散放到多个数据库（主机）上，以达到分散单台设备负载。
- 数据的切分（Sharding）根据其切分规则的类型，可以分为两种切分模式：
    - 垂直（纵向）切分：按照不同的表（或Schema）分到不同的数据库（主机）上
    - 水平（横向）切分：根据表中的数据的逻辑关系，将同一个表中的数据按照某种条件拆分到多台数据库（主机）上
- 垂直切分的最大特点就是规则简单，实施也更为方便，尤其适合各业务的耦合度非常低、相互影响很小、业务逻辑非常清晰的系统。这种系统中，可以很容易做到将不同的业务模块所使用的表拆分到不同的数据库中。根据不同的表来进行拆分，对应用程序的影响也更小，拆分规则也简单清晰。
- 水平切分对于垂直切分相比，稍微复杂一些，因为需要将同一张表中不同数据拆分到不同的数据库中，对应用程序而言，拆分规则本身就较按照表明来拆分更复杂，后期的数据维护也会更复杂一些。

## 5.2.6 垂直切分
把不同业务对应的表分到不同的数据库中，这样也就将数据或压力分摊到不同的库上

![verticalSegmentation-min](https://www.wailian.work/images/2019/01/29/verticalSegmentation-min.jpg)

## 5.2.7 水平切分
水平拆分不是将表做分类，而是按照某个字段的某种规则来分散到多个数据库中，每个表中包含一部分数据。简单来说，可以将数据的水平切分理解为是按照数据行进行拆分，就是将表中的某些行切分到一个数据库，而另外的某些行又切分到其它数据库中，如图：

![horizontalSegmentation-min](https://www.wailian.work/images/2019/01/29/horizontalSegmentation-min.jpg)

分表/分片原则：（最大50G）
1. 访问频繁
2. 数据量大

## 5.2.8 数据拆分优点和最佳实践方案
- 垂直拆分的优点：
    - 业务逻辑清晰；
    - 可扩展性强；
    - 维护简单方便。
- 水平拆分的优点：
    - 拆分规则做的足够好，基本可以单库实现`join`操作；
    - 应用端改造较少，可以稍微轻松实现业务逻辑，但是后期需求变更维护比较麻烦。
    - 不存在单库多数据，以及高并发下性能的瓶颈问题，提高系统的稳定性和负载能力。
- 垂直拆分就是最上层的业务逻辑拆分，比如电商的供应商、商品、库存、订单、网站等模块的业务流程非常清晰可见，最上层垂直拆分即可。
- 水平拆分比如涉及到用户信息，订单信息，一般会涉及多个系统，比如：
    - 用户信息系统统计用户信息，根据用户级别等划分到不同的库
    - 或根据用户类型的方式把不同级别的用户分散到不同的数据库节点上去
    - 或按照用户的序号ID，做求模方式分散等
- 水平拆分一定是在精通业务的前提下才可以去进行的，保证拆分的正确性，后期的维护扩展性等，可以根据不同的数据信息，如：
    - 时间单位年月日；不同类型的角色用户如供应商、会员用户或按照不同的业务规则等去做的
    - 又比如在电商网站购买商品时，商品也会最大分类，比如自营商品和第三方商品，那么就会进行分库操作，订单提交后会流转到不同的服务节点上去

## 5.2.9 数据拆分缺点解决方案
无论水平和垂直拆分，都有很多共通的缺点：
- 引入了分布式事务的问题。（解决方案：针对不同场景案例，具体分析解决）
    - 业务逻辑复杂时，使用SOA做通用服务，在Service层上做多个切面，配置多个事务。
    - 数据量大且分析逻辑复杂时，使用缓冲库（中间库）、缓存表等数据库设计做出来。
    - 要求实时性非常高且数据信息、业务逻辑简单单一，使用第三方数据通信组件，例如：
        * 消息队列做事务的回调服务
        * 或使用ZooKeeper建立分布式锁进行数据同步
        * 或使用直连的Netty进行通信、类似WebService、RESTful等直接请求。
- 跨节点`join`的问题、跨节点合并、排序、分页等处理数据的问题。
    - 通用方案是把数据组织好以后放到缓存中去，定时或实时进行同步。
    - 如果要求实时性不是特别高，那么也可以使用中间库的手段去解决。
- 多数据源管理问题。
    - 使用类似MyCat的代理平台，管理多个数据源。
    - 在每个应用程序模块中配置管理所需要的一个（或多个）数据源，直接访问各个数据库，在模块内完成数据的整合。

## 5.2.10 分区表介绍
- 表分区是日常开发中最常用的技术，主要针对于大数据量、频繁查询数据等需求，有了表分区，可以对表进行区间的拆分和组织，提高查询的效率。
- 一般来讲，Oracle表分区的一个区间数据最好不大于500W条，也就是说500W条数据左右可以划分为一个区间，根据实际业务需求和表分区的性能而定。
- Oracle11g提供了7种分区，功能非常强大，基本满足我们开发的90%以上的需求，作为一个优秀的高程/架构方向的程序员，一定要对数据库存储的概念非常透彻，即使是使用MySQL也会有类似的分区技术，早期MySQL多用于水平和垂直"分区"，拆表拆字段的形式。
    - range分区
    - hash分区
    - list分区
    - 复合分区
    - 间隔分区
    - system分区

## 5.2.11 range分区
range分区就是区域分区，按照定义的区域，进行划分。语法：
```
create table(...)
partition by range(field)(
	partition p1 values less than(value),
	partition p2 values less than(value),
	partition p3 values less than(value)
);
```
- 查看分区情况：`select * from user_tab_partitions;`
- 查看分区数据：`select * from table partition(p1);`
- 修改分区：
    - 添加：`alter table tableName add partition p4 values less than(maxvalue);`
    - 删除：`alter table tableName drop partition p4;`
    - 更新数据时操作时不可以跨分区操作，会出现错误，需要设置可移动的分区才能进行跨分区查询。`alter table tablename enable row movement;`

## 5.2.12 分区索引
分区之后虽然可以提高查询的效率，但也仅仅是提高了数据的范围，所以在有必要的情况需要建立分区索引，从而进一步提高效率。

分区索引大体上分俩大类，一类叫做local，一类叫做global。
- local：在每个分区上建立索引。
- global：一种是在全局上建立索引，这种方式分不分区都一样，一般不使用；还有一种就是自义数据区间的索引，也叫做前缀索引，这个是非常有意义的，自定义区域值时注意必须要`maxvalue`。

另外要注意一点：在分区上建立的索引必须是分区字段列。

- local方式语法：`create index idxname on table(field) local;`
- 查看分区索引：`select * from user_ind_partitions;`
- global自定义全局（前缀索引）引方式语法：
```sql
create index idxname on table(field) global
partition by range(field)(
	partition p1 values less than(value),
	partition p2 values less than(maxvalue)
);
```
- global全局索引方式语法：`create index idxname on table(field) global;`

## 5.2.13 hash分区
hash分区实现均匀的负载值分配，増加hash分区可以重新分布数据。
1. 建立散列分区表
	```sql
	create table my_emp(
		empno number,
		ename varchar2(10)
	partition by hash(empno)(
		partition p1, partition p2
	);
	```
1. 査看分区表结构
	```sql
	select * from user_tab_partitions where table_name='MY_EMP';
	```
1. 插入数据
	```sql
	insert into my_emp values(1,'A');
	insert into my_emp values(2,'B');
	insert into my_emp values(3,'C');
	```
1. 査看分区数据
	```sql
	select * from my_emp partition(p1);
	select * from my_emp partition(p2);
	```

## 5.2.14 list分区
```sql
create table personcity(
	id number,
	name varchar2(10),
	city varchar2(10)
)
partition by list(city)(
	partition east values('tianjin','dalian'),
	partition vest values('xian'),
	partition south values('shanghai'),
	partition north values('herbin'),
	partition other values(default)
);
insert into personcity values(1,'sohu','tianjin');
insert into personcity values(2,'sina','herbin');
insert into personcity values(3,'yahoo','dalian');
insert into personcity values(4,'360','zhengzhou');
insert into personcity values(5,'baidu','xian');
select * from personcity partition(east);
```

## 5.2.15 复合分区
把范围分区和散列分区相结合，或范围分区和列表分区相结合
```sql
create table student(
	sno number,
	sname varchar2(10)
)
partition by range(sno)
subpartition by hash(sname)
subpartitions 4(
	partition p1 values less than(1000),
	partition p2 values less than(2000),
	partition p3 values less than(maxvalue)
);
select * from user_tab_partitions where table_name='STUDENT';
select * from user_tab_subpartitions where table_name='STUDENT';
```

## 5.2.16 间隔分区
- Interval Partitioning是一种分区自动化的分区，可以指定时间间隔进行分区，这是Oracle11g的新特性，这个功能在实际的工作中也非常常用。
- Interval Partitioning一直是Oracle数据库引以为荣的一项技术，正是分区的存在让Oracle高效的处理海量数据成为可能。
- Interval Partitioning实际上是由range分区引申的，最终实现了range分区的自动化。语法：
```sql
create table interval_sale(sid int, sdate timestamp)
partition by range(sdate)
interval(numtoyminterval(1,'MONTH'))(
	partition p1 values less than (TIMESTAMP '2014-02-01 00:00:00.00')
)
```

