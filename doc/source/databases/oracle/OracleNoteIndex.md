# Oracle笔记 Chapter 3.4

## 3.4.1 索引
索引分为B树索引和位图索引。主要研究B树索引

![bTreeIndex-min](https://www.wailian.work/images/2019/01/25/bTreeIndex-min.jpg)

## 3.4.2 SQL索引概念
一般SQL优化有几种解决方案：
1. 索引（index）
1. 分区（partition）
1. 物化视图
1. 并行查询

索引分为两大结构：
1. B树索引结构（balance）：类似于字典查询，最后到leaf block存的是数据rowid和数据项
    1. 叶块之间使用双向链连接，为了可以范围查询
    1. 删除表行时，索引叶块也会更新，但只是逻辑更改，并不做物理的删除叶块
    1. 索引叶块中不保存表行键值的null信息
1. 位图索引结构（bitmap）：离散度比较低时，需要用位图索引，离散度指的是重复度[比较多用位图索引]
    - `create bitmap index job_bitmap on emp1(job);`（因为job的值重复分布的比较多，即离散度比较低）

## 3.4.3 索引的说明和目的
索引的说明：
- 索引是与表相关的一个可选结构，在逻辑上和物理上都独立于表的数据，索引能优化查询，**不能优化DML操作**，Oracle自动维护索引，频繁的DML操作反而会引起大量的索引维护。
- 如果SQL语句仅访问被索引的列，那么数据库只需从索引中读取数据，而不用读取表。
- 如果该语句同时还要访问除索引列之外的列，那么，数据库会使用rowid来查找表中的行。
- 通常，为检索表数据，数据库以交替方式先读取索引块，然后读取相应的表块。

索引的目的是：主要是减少IO，这是本质，这样才能体现索引的效率。
1. 大表，返回的行数<5%
1. 经常使用where子句查询的列
1. 离散度高的列
1. 更新键值代价低
1. 逻辑AND、OR效率高
1. 查看索引在建在那表、列：
	```
	select * from user_indexes;
	select * from user_ind_columns;
	```

## 3.4.4 索引的使用
1. 唯一索引（unique or non unique）：指键值不重复
	```
	create unique index empno_idx on emp1(empno);
	--drop index empno_idx;
	```
1. 一般索引：指键值可以重复
	```
	create index empno_idx on emp1(empno);
	```
1. 组合索引（composite）：绑定了2个或更多的索引
	```
	create index job_deptno_idx on emp1(job, deptno);
	--drop index job_deptno_idx;
	```
1. 反向键索引（reverse）：为了避免平衡树索引热块，比如emp表中很多开头都是'7'，这样在构建索引树时，很有可能把所有数据都分配到一个块里面，使用反向键则使索引值反向，避免此类问题，使索引树的数据分布均匀。
	```
	create index mgr_idx on emp1(mgr) reverse;
	--drop index mgr_idx;
	```
1. 函数索引（function index）：查询时必须用到这个函数，才会使用到
	```
	create index fun_idx on emp1(lower(ename));
	--select * from emp1 where lower(ename) = 'scott';
	--drop index fun_idx;
	```
1. 压缩索引（compress）
	```
	create index comp_idx on emp1 (sal) compress;
	--drop index comp_idx;
	```
1. 升序降序索引
	```
	create index deptno_job_idx on emp(deptno desc, job asc);
	--drop index deptno_job_idx;
	```

## 3.4.5 索引的问题
查看执行计划：`set autotrace traceonly explain;`

索引碎片问题：由于对基表做DML操作，导致索引表块的自动更改操作，尤其是基表的delete操作会引起index表的`index_entries`的逻辑删除，注意只有当个索引块中的全部`index_entry`都被删除了，才会把这个索引块删除，索引对基表的delete、insert操作都会产生索引碎片问题。

在Oracle文档里并没有清晰的给出索引碎片的量化标准，Oracle建议通过Segment属性Advisor（段顾问）解决表和索引的碎片问题。如果你想自行解决，可以通过查看index_stats视图，当以下三种情形之一发生时，说明积累的碎片应该整反情理了（仅供参考）。
1. `HEIGHT>=4`
1. `PCT_USED<50%`
1. `DEL_LF_ROWS/LF_ROWS>0.2`

## 3.4.6 解决索引碎片问题
建立表、索引：
```
create table t(id int);
create index ind_1 on t(id);
```
执行插入记录：
```
begin
	for i in 1..1000000 loop
		insert into t values (i);
		if mod(i, 100)=0 then
			commit;
		end if;
	end loop;
end;
/
```
索引分析：
```
analyze index ind_1 validate structure;
select name,HEIGHT,PCT_USED,DEL_LF_ROWS/LF_ROWS from index_stats;
delete t where rownum<700000;
alter index ind_1 rebuild [online] [tablespace name];
```

## 数据库设计与优化

### 数据库表的设计
1. 业务需要学会切分
1. 逻辑分层（数据库分层）
1. 数据库表结构设计与拆分
    - MySQL水平拆分（分片）
    - 分区
    - 物化视图
    - 中间表的方案
    - 设计的方案

### 对于结构优化的设计
建立索引
1. 建立普通索引
1. 建立规则索引
1. 复合索引
1. 数据规则（添加必要的扩展字段）
1. 预留字段（用于关联其它业务的，外键...）
1. 做一些合理的冗余