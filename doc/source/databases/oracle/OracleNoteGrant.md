# Oracle笔记 Chapter 3.1-3.3

## 3.1.1 用户操作
用户锁定和解锁/密码设置：
```sql
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
```sql
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
6 | X(EXCLUSIVE) | 排它锁 | alter table、drop table、drop index、truncate table 、lock exclusive

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
```sql
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
```sql
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