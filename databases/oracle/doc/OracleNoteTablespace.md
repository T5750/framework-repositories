## 5.1.1 表空间使用
Oracle创建表空间
1. 创建临时表空间
	```
	create temporary tablespace zcdb_temp
	tempfile 'D:\oracle\datafile\zcdb_temp.dbf'
	size 1024m
	autoextend on;
	next 50m maxsize 20480m
	extent management local;
	```
1. 创建数据表空间
	```
	create tablespace zcdb
	datafile 'D:\oracle\datafile\zcdb.dbf'
	size 10240m
	autoextend on;
	next 50m maxsize 20480m
	extent management local;
	--alter tablespace zcdb add datafile 'D:\oracle\datafile\zcdb2.dbf' size 10240m autoextend on;
	```
1. 创建用户，并指定表空间
	```
	create user zcdb identified by zcdb
	default tablespace zcdb
	temporary tablespace zcdb_temp;
	```
1. 给用户授予权限
	```
	grant dba to zcdb;
	```

## 5.1.2 查看表空间
查看Oracle表空间和使用率：
```
SELECT t.tablespace_name, round(SUM(bytes/(1024 * 1024)),0) ts_size
FROM dba_tablespaces t, dba_data_fils d
WHERE t.tablespace_name = d.tablespace_name
GROUP BY t.tablespace_name;
```
查看数据库实例名称：
```
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

日常开发使用的分表分库的问题，其实都是基FOLTP和OLAP的业务前提，然后对数据做切分，例如垂真、水平切分。在Oracle里早就有这个概念了，就是使用分区的方案。

