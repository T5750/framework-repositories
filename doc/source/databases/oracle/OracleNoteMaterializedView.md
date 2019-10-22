# Oracle笔记 Chapter 3.5-3.7

## 3.5.1 物化视图概念
视图（VIEW）是一种虚表，其目的仅仅是为了方便进行综合数据的查询而已，它并不能够帮助我们提高性能。在相应的场景下，可以使用物化视图提高查询效率。

物化视图是一种特殊的物理表，"物化"（Materialized）视图是相对普通视图而言的。物化视图的特点：
- 物化视图在某种意义上说就是一个物理表（而且不仅仅是一个物理表），这通过其可以被`user_tables`查询出来，而得到佐证
- 物化视图也是一种段（segment），所以其有自己的物理存储属性
- 物化视图会占用数据库磁盘空间，这点从`user_segment`的查询结果，可得到佐证

## 3.5.2 物化视图的使用
物化视图的类型：Oracle提供了两种方式，手工刷新和自动刷新，默认为手工刷新。即：`ON DEMAND`、`ON COMMIT`，二者的区别在于刷新方法的不同
- `ON DEMAND`顾名思义，仅在该物化视图"需要"被刷新了，才进行刷新（REFRESH），即更新物化视图，以保证和基表数据的一致性；
- 而`ON COMMIT`是说，一旦基表有了COMMIT，即事务提交，则立刻刷新，立刻更新物化视图，使得数据和基表一致。

创建语句：
```
create materialized view mv_name [选项N] as select * from table_name;
```
- `[选项1] BUILD [IMMEDIATE, DEFERRED]`是否在创建视图时生成数据，默认生成，`DEFERRED`为不生成数据，需要的时候生成。
- `[选项2] REFRESH [FAST, COMPLETE, FORCE, NEVER]`
    - `FAST`是增量刷新，或者说叫快速刷新；
	- `COMPLETE`为全表刷新；
	- `FORCE`为如果增量刷新可以使用，则使用增量刷新，否则全表刷新；
	- `NEVER`则是不进行刷新（不使用）。
- `[选项3] ON [DEMAND, COMMIT]`即手工刷新和提交时刷新。
- `[选项4] START WITH`通知数据库完成从主表到本地表第一次复制的时间。
- `[选项5] NEXT`说明了刷新的时间间隔，`下一次刷新的时间=上一次执行完成的时间+时间间隔`。

## 3.5.3 COMMIT刷新
注意：基表必须要有主键
```
create table B (clsid int primary key, name varchar2(10));
create table A (id int prmary key, name varchar2(10),clsid int, constraint fk_cls id foreign key (clsid) references B(clsid));
insert into b values(1,'一班');
insert into b values(2,'二班');
insert into a values(1,'张三',1);
insert into a values(2,'李四',1);
insert into a values(3,'王五',2);
commit;
```
语法如下：
```
CREATE MATERIALIZED VIEW V_AB
REFRESH FORCE ON COMMIT
AS
SELECT A.ID, A.NAME, B.CLSID, B.NAME AS CLSNAME
FROM A,B
WHERE A.CLSID = B.CLSID;
```

## 3.5.4 DEMAND刷新
语法如下：
```
CREATE MATERIALIZED VIEW MV_AB
REFRESH FORCE ON DEMAND
	START WITH SYSDATE
	NEXT SYSDATE+1
AS
SELECT A.ID, A.NAME, B.CLSID, B.NAME AS CLSNAME
FROM A,B
WHERE A.CLSID = B.CLSID;
```

## 3.5.5 快速刷新的建立
语法如下：
```
CREATE MATERIALIZED VIEW LOG ON A WITH ROWID;
CREATE MATERIALIZED VIEW LOG ON B WITH ROWID;
CREATE MATERIALIZED VIEW MV_AB
REFRESH FAST ON DEMAND
	START WITH SYSDATE
	NEXT SYSDATE+1/1440
AS
SELECT A.ROWID AS AROWID, B.ROWID AS BROWID,A.ID, A.NAME, B.CLSID, B.NAME AS CLSNAME
FROM A,B
WHERE A.CLSID= B.CLSID;
```

## 3.6.1 同义词
从字面上理解就是别名的意思和视图的功能类似，就是一种映射关系。

私有同义词：一般是普通用户自己建立的同义词，用于创建这需要`create synonym`权限
```
grant create synonym to scott;
create synonym abc for emp;
```
公有同义词：一般是由DBA创建，所有的用户可以使用，创建者需要`create public synonym`权限
```
create public synonym to scott;
create public synonym xyz for emp;
drop public synonym xyz;
```

## 3.6.2 同义词使用要点
- 私有同义词是模式对象，一般在自己的模式中使用，如其它模式使则必须用模式名前缀限定。
- 公有同义词不是模式对象，不能用模式名做前缀。
- 私有和公有同义词同名时，如果指向不同的对象，私有同义词优先。
- 引用的同义词的对象（表或视图）被删除了，同义词仍然存在，这同视图类似，重新创建该对象名，下次访问同义词时自动编译。

例：
```
create synonym wyz for emp1;
drop table emp1;
select * from wyz; --已删除表，义词转换不再有效
flashback table emp1 to before drop;
select * from wyz; --利用闪回,同义词再次有效
```

## 3.7.1 DBLINK
Oracle的dblink用于对不同的数据库实例或者远程进行连接，语法如下：
```
create public database link LINKNAME
connect to USERNAME identified by PASSWD using
'(DESCRIPTION=
(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=)(PORT=)))
(CONNECT_DATA=(SERVICE_NAME=))
)';
```