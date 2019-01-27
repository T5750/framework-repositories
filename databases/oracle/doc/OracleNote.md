# Oracle笔记

## 概述
目前数据库大体上分为两类，一类是关系型数据库，一类是非关系型数据库。其实在业界中，大体上可以分为三大类软件公司：
- 互联网行业（如电商、P2P、O2O、互联网金融等）
- 传统领域软件行业（如交通、气象、电信、银行等某个领域的软件需求）
- 产品软件行业（如医疗、军工、OA、企业级管理系统、小行业等第三方的软件需求）

但是无论任何软件公司，他们都离不开数据库，根据需求的不同，选择相应的数据库作为存储。有人说一般互联网行业都会选择使用MySQL数据库，原因很简单，开源且成本低，其实一般的大型互联公司，往往不是只单单使用一种数据库。可能是多种混合这使用，比如互联网金融公司的核心数据库一般采用Oracle或者DB2，为了对安全做保证，外围数据库可能使用MySQL，一些即时的信息或安全度不是特别高的信息可能会使用缓存。所以，使用什么数据库是要看具体的需求而定的，并不能一概而论。

### 数据库类型
对于关系型数据库，不在强调具体细节，关系型数据库的最大特点就是事务的一致性：传统的关系型数据库读写操作都是事务的，具有ACID的特点，这个特性使得关系型数据库可以用于几乎所有对一致性有要求的系统中，如典型的银行系统。

相反地，关系型数据库为了维护一致性所付出的巨大代价就是其读写性能比较差，而像微博、facebook这类SNS的应用，对并发读写能力要求极高，关系型数据库已经无法应付，所以会采用一系列的非关系型数据库，或者缓存来提供系统的性能，尤其是web2.0的兴起。

关系型数据库和非关系型数据库的区别？

最终无论数据怎么做处理都要有数据落地（入库），一般我们会存储在关系型数据库中，所以无论关系型、非关系型数据库，我们都要掌握它们的使用和应用范围、场景，一个好的数据设计，往往可以规避很多复杂的逻辑和后期的不可预见性，所以一般设计数据库的工程师在企业里都是很重要的核心成员。

### 关系型数据库
所接触了解最多的是关系型数据库，如Oracle、MySQL、DB2、SQLServer等，这些数据库你不可能每种都精通，但是如果你学会了Oracle，那么其它的数据库学习起来就相对的容易很多。在关系数据中，Oracle也是最复杂、最博大精深的。

## 1.1 数据库语言
数据操作语言：DML(data manipulation language)
- `select insert update delete merge`

数据定义语言：DDL(data definition language)
- `create alter drop truncate`

事务控制语言：TCL(transaction control language)
- `commit rollback savepoint`

数据控制语言：DCL(Data Control Language )
- `grant revoke`

## 1.2 数据类型
SQL的数据类型：1字符型，2数值型，3日期型，4大对象型
- `char`：固定字符，最长2000个
- `varchar2`：可变长，最长4000，最小值1
- `number`类型
- `date`、`timestamp`
- `clob`：存储单字节数据，文本数据
- `blob`：存储二进制数据

## 1.3 常用数据类型转换
常用转换：`date`、`char`、`number`三者之间经常进行转换
```
SELECT TO_DATE('2015-08-19','YYYY-MM-DD') AS A_DAY FROM DUAL;
SELECT TO_CHAR(SYSDATE, 'YYYY-MM-DD') AS TODAY FROM DUAL;
SELECT TO_TIMESTAMP('2015-08-19 17:40:32.11', 'YYYY-MM-DD HH24:MI:SS.FF') AS A_DAY FROM DUAL;
SELECT TO_CHAR(SYSTIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.FF') AS TODAY FROM DUAL;
SELECT TO_NUMBER(REPLACE(TO_CHAR(SYSDATE, 'YYYY-MM-DD'),'-'))FROM DUAL;
```
[Oracle中TO_DATE TO_CHAR格式](OracleDateChar.md)：`SELECT TO_CHAR(SYSDATE, '参数') FROM DUAL;`

## 1.4 常用函数
`decode`函数：`SELECT DEPTNO,DECODE(DEPTNO,10,'部门1',20,'部门2','部门3') FROM EMP;`

`case`表达式：`SELECT DEPTNO,CASE WHEN DEPTNO=10 THEN '部门1' WHEN DEPTNO=20 THEN '部门2' ELSE '部门3' END FROM EMP;`

学习使用函数：`TRUNC`、`ADD_MONTHS`、`MONTH_BETWEEN`、`LAST_DAY`、`REPLACE`、`SUBSTR`、`CONCAT`、`ABS`、`ROUND`

## 1.5 分组
组函数：`sum min max avg count`

分组特性：`group by ... having`

1. 查询每个部门的平均薪水之后，显示部门平均薪水大于2000的部门编号和其平均薪水。答案：`SELECT DEPTNO, AVG(SAL) FROM EMP GROUP BY DEPTNO HAVING AVG(SAL) > 2000;`
1. 查询每个部门薪水大于2000，且平均薪水大于3000的部门编号和其平均薪水。答案：`SELECT DEPTNO, AVG(SAL) FROM EMP WHERE SAL > 2000 GROUP BY DEPTNO HAVING AVG(SAL) > 3000;`
1. 查询每个部门的薪水和，再算出所有部门的薪水平均值。答案：`SELECT DEPTNO, SUM(DEPTNO), AVG(SAL) FROM EMP GROUP BY DEPTNO;`

## 1.6 集合查询操作
1. 交叉连接（笛卡尔积）
1. 等值、非等值连接
1. 内连接
1. 外连接（左外、右外、全连接）
1. 自连接
1. 自然连接（隐含链接条件，自动匹配链接自动）
1. 集合运算
    - `union`（求合集，重复记录只显示一次）
    - `union all`（求合集，显示所有记录信息）
    - `intersect`（求交集，显示公共的数据部分）
    - `minus`（集合相减，哪个表在前面以哪个表的数据为主）

```
CREATE TABLE A (ID INT, NAME VARCHAR2(10));
CREATE TABLE B (ID INT, NAME VARCHAR2(10));
INSERT INTO A VALUES(1, '张三');
INSERT INTO A VALUES(2, '李四');
INSERT INTO B VALUES(2, '李四');
INSERT INTO B VALUES(3, '王五');
SELECT * FROM A;
SELECT * FROM B;
-- INTERSECT
SELECT * FROM A INTERSECT SELECT * FROM B;
-- MINUS（A表为主）
SELECT * FROM A MINUS SELECT * FROM B;
-- MINUS（B表为主）
SELECT * FROM B MINUS SELECT * FROM A;
```

## 1.7 子查询
非关联子查询：主查询和子查询是相对独立的，唯一的，子查询查询结果和主查询进行比较
```
SELECT A.ENAME, A.SAL FROM EMP A WHERE A.DEPTNO= (SELECT B.DEPTNO FROM DEPT B WHERE B.LOC = 'NEW YORK');
```
关联子查询：主查询和子查询是产生关联关系的主查询的一个列字段代入到子查询中进行比较
```
SELECT A.DEPTNO, (SELECT B.LOC FROM DEPT B WHERE B.DEPTNO =A.DEPTNO) FROM EMP A;
```
`IN`和`EXISTS`，`IN`是做全表扫描，`EXISTS`是做是否存在，非全表扫描。
- 查询属于领导（大小领导都算）的员工。答案：`SELECT * FROM EMP A WHERE EXISTS (SELECT 1 FROM EMP B WHERE B.MGR=A.EMPNO);`
- 查询哪个部门不存在员工的部门信息。答案：`SELECT * FROM DEPT D WHERE NOT EXISTS (SELECT 1 FROM EMP E WHERE E.DEPTNO=D.DEPTNO);`

## 1.8 ROWNUM使用
使用`ROWNUM`需要注意：使用`<`可以查询结果，使用`>`没有结果，必须使用别名的形式查询`>`才能有结果
- 查询前小于5的记录。答案：`SELECT * FROM EMP WHERE ROWNUM < 5;`
- 查询大于5的记录数。答案：`SELECT * FROM (SELECT E.*,ROWNUM AS RN FROM EMP E) WHERE RN>5;`
- 查询薪水前三名。答案：`SELECT * FROM (SELECT * FROM EMP ORDER BY SAL DESC)WHERE ROWNUM <=3;`
- 分页查询5-10条数据。答案：`SELECT * FROM (SELECT EMP.*,ROWNUM RN FROM EMP) A WHERE A.RN BETWEEN 5 AND 10;`

## 2.1 CT和IS
`SELECT INTO`和`INSERT INTO SELECT`两种表复制语句
- CT：`create table <new table> as select * from <exists table>`
- 要求目标表不存在，因为在插入时会自动创建表，并格查询表中指定字段数据复制到新建的表中
- IS：`insert into table2 (f1,f2,...) select v1,2,... from table1`
- 要求目标表table2必须存在，由于目标表table2已经存在，所以除了插入源表table1的字段外，还可以插入常量

## 2.2 MERGE用法
MERGE INTO用法：
```
merge into 表A
using 与表A产生关联字段值
on 进行和表A关联
    when matched then
        update set...
    when not matched then
        insert (...) values ...
```

## 2.3 递归函数
`START WITH CONNECT BY`是Oracle提供的递归查询（分层查询）函数，非常的好用，在进行递归遍历树形结构的时候可以使用。
```
start with（从某个节点id开始）
connect by prior（子节点id和父节点pid之间的关系需要）
```
形如：
```
SELECT * FROM EMP
START WITH EMPNO=7369
CONNECT BY PRIOR MGR=EMPNO;（父节点=子节点向上查询，反之向下查询）
```
可以添加WHERE条件限制。可以指定多个起始节点查询。可以进行排序。

## 2.4 分析函数
- `over`函数
- `over partition by`组合
- `over partition by order by`组合
- `row_number`函数
- `rollup`函数
- `cube`函数
- `grouping`函数

## 3.1.1 用户操作
用户锁定和解锁/密码设置：
```
alter user [USER] account lock;
alter user [USER] account unlock;
alter user scott identified by tiger;
select * from dba users;--查看用户信息表
```
创建用户：`create user [USER] identified by [PASSWD];`

删除用户：`drop user [USER] cascade;`

## 3.1.2 简单赋权
对于权限的赋予和收回语法如下：
```
grant [权限] to [用户]
revoke [权限] from [用户]
```
在Oracle里有两个最著名的角色：connect、resource除了dba之外的最大角色。

查看当前用户的所有权限：`select * from session_privs;`与scott用户下的权限进行对比，发现多了一个`unlimited`，其含义是拥有所有表空间配额的使用权限，这个权限太大了，一般来讲需要进行回收，然后重新进行分配一个表空间配额。
```
revoke unlimited tablespace from [USER];
select username,default tablespace from user_users;--查看用户缺省表空间
alter user [USER] quota 10m on users;
```

- 想让新建用户拥有对scott.emp表的查询权限：`grant select on scott.emp to [USER];`
- 想让新建用户拥有对scott的建立表操作：`grant create any table to tim;`
- 想让新建用户拥有对scott.emp表的修改权限：`grant select, update(sal) on emp to tim;`<注意：可以精确到字段>

## 3.1.3 权限的传递和回收
1.系统权限的传递与回收：`WITH ADMIN OPTION 选项`<总结：覆水难收>
```
--新建2个用户
SQL> create user tim1 identified by tim1;
User created
SQL> create user tim2 identified by tim2;
User created

--sys赋于权限create session给tim1，tim1赋予权限create session给tim2;<注意使用权限传递语法>
SQL> grant create session to tim1 with admin option;
Grant succeeded
SQL> grant create session to tim2;
Grant succeeded

--这时候tim1、tim2都可以登录。然后sys回收tim1的create session权限，tim1肯定不能登录，但tim2还可以继续登录。
SQL> revoke create session from tim1;
Revoke succeeded

<结果>
SQL> conn tim1/tim1
ERROR：
0RA-01045:用户tim1没有CREATE SESSION权限;登录被拒绝
SQL> conn tim2/tim2
已连接。
```
<总结>系统权限：覆水难收

2.对象权限的传递与回收：`WITH GRANT OPTION 选项`<总结：株连九族>
```
--scott赋予权限select给tim1，tim1赋于权限select给tim2;<注意使用权限传递语法>
SQL> conn scott/scott
已连接。
SQL> grant select on emp to tim1 with grant option;
授权成功。
sQL> conn tim1/tim1
已连接。
SQL> select * from scott.emp --可以看到scott.emg表
SQL> grant select on emp to tim2;
SQL> conn tim2/tim2
已连接。
SQL> select * from scott.emp --可以看到scott.emp表
SQL> conn scott/scott
已连接。
SQL> revoke select on emp from tim1;
撤销成功。
--再用tim1、tim2登录查询scott.emp都无法看到表信息
<结果>
SQL> select * from scott.emp;
第1行出现错误：
ORA-00942:表或视图不存在
```
<总结>对象权限：株连九族

## 3.2.1 事务特性
事务必须具备以下四个属性，简称ACID属性：
- 原子性（Atomicity）：事务是一个完整的操作。事务的各步操作是不可分的（原子的）；要么都执行，要么都不执行
    - 场景：银行转账，A-100 B+100同时成功或同时失败
- 一致性（Consistency）：一个查询的结果必须与数据库在查询开始的状态一致（读不等待写，写不等待读）。
    - 场景：查询数据，9:00开始查询数据9:15查询完毕，在这期间所查询的数据被其它操作更新，且在9:00-1:15之间查询结果显示的是9:00时候并没有被更改的数据。一般Oracle是把这个没有更新的数据放入'undo'里，如果Oracle在'undo'里没有找到数据，则宁可报错，也不会让你看到其它操作更新的新的数据。
- 隔离性（Isolation）：对于其它会话来说，未完成的（也就是未提交的）事务必须不可见。
    - 场景：事务和事务之间相互隔离，2个session一个查询一个更新，那么在更新操作没有commit之前，查询所看到的数据是没有提交之前的，相互没有影响。
- 持久性（Durability）：事务一旦提交完成后，数据库就不可以丢失这个事务的结果，数据库通过日志能够保持事务的持久性。
    - 场景：事务提交之后不可逆，提交数据是由内存的数据刷新到磁盘上，这个过程的快慢和性能有关。那么Oracle主要是靠'rudo'日志，先记录日志，在写到磁盘上。

## 3.2.2 事务的开始和结束
事务采用隐性的方式，起始于session的第一条DML语句

查看事务：`select * from v$transaction;`

事务结束于：
1. COMMIT（提交）或ROLLBACK（回滚）
1. DDL语句被执行（提交）
1. DCL语句被执行（提交）
1. 用户退出SQLPLUS（正常退出时提交，非正常退出时回滚）
1. 机器故障或系统崩溃（回滚）
1. shutdown immediate（回滚）

## 3.3.1 锁
锁大概分为：共享锁与排它锁。
- 排它锁（独占），排斥其它排它锁和共享锁。
- 共享锁，排斥其它排它锁，但不排斥其它共享锁。

锁类型：
- DML锁（data locks，数据锁），用于保护数据的完整性。TX（行级锁），TM（表级锁），日常所使用的DML操作就会产生事务和锁。
- 查看事务：`select * from v$transaction;`
- 查看锁：`select * from v$lock;`
- DDL锁（dictionary locks，数据字典锁），用于保护数据库对象的结构，如表、索引等的结构定义。
- SYSTEM锁（internal locks and latches），保护数据库的内部结构

锁用途：只有有事务才会产生锁，保证数据的完整性和正确性。

## 3.3.2 锁类型
TX：一种锁

TM：五种

锁模式 | 锁描述 | 解释 | SQL操作
---|---|---|-----
2 | RS(ROW SHARE) | 行级共享锁，其它对象只能查询这些数据行 | select for update、lock for update、lock row share
3 | RX(ROW EXCLUSIVE) | 行级排它锁，在提交前不允许做DML操作 | insert、update、delete、lock row share
4 | S(SHARE) | 共享锁 | create index、lock share
5 | SRX(SHARE ROW EXCLUSIVE) | 共享行级排它锁 | lock share row exclusive
6 | X(EXCLUSIVE) | 排它锁 | alter table、drop able、drop index、truncate table 、lock exclusive

SQL语句 | 加锁模式 | 许可其他用户的加锁模式
------|---|----
select * from table_name | 无 | RS，RX，S，SRX，X
insert，update，delete（DML操作） | RX | RS，RX
select * from table_name for update | RX | RS，RX
lock table table_name in row share mode | RS | RS，RX，S，SRX
lock table table_name in row exclusive mode  | RX | RS，RX
lock table table_name in share mode | S | RS，S
lock table table_name in share row exclusive mode | SRX | RS
lock table table_name in exclusive mode | X | 无

## 3.3.3 加锁模式
自动加锁，做DML操作时，如insert，update，delete，以及`select...for update`由Oracle自动完成加锁。`select * from emp1 where deptno = 10 for update;`

修改其部门为10的记录则会被锁定，可以进行试探要修改数据记录是否被加锁。如下三种形式均可：
```
select * from emp1 where empno = 7782 for update nowait;
select * from emp1 where empno = 7782 for update wait 5;
select * from emp1 where job= 'CLERK' for update skip locked;
```
如果这个锁占用的时间太长，可以通过管理员杀掉session用户（表霸）。
- 首先，找到是哪个sid占用了太长时间，查看v$lock表
- 然后，根据v$lock表的SID，去v$session里面去找到，进行kill操作。
	```
	select sid, serial# from v$session where sid = 170;
	alter system kill session 'sid,serial';
	```

## 3.3.4 死锁问题
Oracle会自动解决死锁问题
```
CREATE TABLE A(id int);
insert into a values(100);
insert into a values(200);
insert into a values(300);
commit;
select * from a;

--建立死锁机制：
--A用户
update a set id=1000 where id=100;
--B用户
update a set id=2000 where id=200;
--A用户去触碰B用户正在使用的id=200的数据
update a set id=3000 where id=200;
--B用户则触碰A用户正在使用的id=100的数据
update a set id=4000 where id=100;
--这样就会造成死锁，Oracle会自动进行解除死锁
```

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


## References
- Oracle
- [EMP and DEPT](https://livesql.oracle.com/apex/livesql/file/content_O5AEB2HE08PYEPTGCFLZU9YCV.html)