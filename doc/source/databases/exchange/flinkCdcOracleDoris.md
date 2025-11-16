# Flink CDC Oracle Doris
Oracle 同步数据至 Doris

## Oracle 安装并配置
[helowin/oracle_11g](../oracle/oracleDocker.md)

## 配置数据库恢复和归档日志
登录 SQL*Plus 并执行以下命令：
```sql
su - oracle
sqlplus /nolog
-- 以 DBA 身份登录
CONN /AS SYSDBA

-- 设置数据库恢复
ALTER SYSTEM SET DB_RECOVERY_FILE_DEST_SIZE = 10G;
ALTER SYSTEM SET DB_RECOVERY_FILE_DEST = '/home/oracle/app/oracle/product/11.2.0' SCOPE=SPFILE;
-- 启用归档日志模式
SHUTDOWN IMMEDIATE;
STARTUP MOUNT;
ALTER DATABASE ARCHIVELOG;
ALTER DATABASE OPEN;

-- 查看归档日志状态
ARCHIVE LOG LIST;

-- 为整个数据库启用增强日志记录
ALTER DATABASE ADD SUPPLEMENTAL LOG DATA;

-- 创建用户并赋予 dba 角色
CREATE USER admin IDENTIFIED BY admin123;
-- 为该用户赋予 dba 角色
GRANT DBA TO admin;
```

## 创建表空间和用户
创建表空间：
```sql
CREATE TABLESPACE logminer_tbs
DATAFILE '/home/oracle/app/oracle/product/11.2.0/logminer_tbs.dbf'
SIZE 25M REUSE AUTOEXTEND ON MAXSIZE UNLIMITED;
```
创建测试用户并授权：
```sql
CREATE USER flinkuser IDENTIFIED BY flinkpw DEFAULT TABLESPACE LOGMINER_TBS QUOTA UNLIMITED ON LOGMINER_TBS;

-- 授予必要权限
GRANT CREATE SESSION, CREATE TABLE, LOCK ANY TABLE, ALTER ANY TABLE, CREATE SEQUENCE TO flinkuser;
GRANT SELECT ON V_$DATABASE TO flinkuser;
GRANT FLASHBACK ANY TABLE TO flinkuser;
GRANT SELECT ANY TABLE TO flinkuser;
GRANT SELECT_CATALOG_ROLE, EXECUTE_CATALOG_ROLE TO flinkuser;
GRANT SELECT ANY TRANSACTION TO flinkuser;
GRANT EXECUTE ON DBMS_LOGMNR TO flinkuser;
GRANT EXECUTE ON DBMS_LOGMNR_D TO flinkuser;
GRANT SELECT ON V_$LOG TO flinkuser;
GRANT SELECT ON V_$LOG_HISTORY TO flinkuser;
GRANT SELECT ON V_$LOGMNR_LOGS TO flinkuser;
GRANT SELECT ON V_$LOGMNR_CONTENTS TO flinkuser;
GRANT SELECT ON V_$LOGMNR_PARAMETERS TO flinkuser;
GRANT SELECT ON V_$LOGFILE TO flinkuser;
GRANT SELECT ON V_$ARCHIVED_LOG TO flinkuser;
GRANT SELECT ON V_$ARCHIVE_DEST_STATUS TO flinkuser;

-- 为特定表启用增强日志记录
ALTER TABLE FLINKUSER.CUSTOMERS ADD SUPPLEMENTAL LOG DATA (ALL) COLUMNS;
```

## 创建示例表
切换到 flinkuser 并创建表：
```sql
sqlplus flinkuser/flinkpw

-- 创建 customers 表
CREATE TABLE customers (
    customer_id NUMBER PRIMARY KEY,
    customer_name VARCHAR2(50),
    email VARCHAR2(100),
    phone VARCHAR2(20)
) TABLESPACE LOGMINER_TBS;

-- 创建 product 表
CREATE TABLE product (
    product_id NUMBER PRIMARY KEY,
    product_name VARCHAR2(50),
    price NUMBER
) TABLESPACE LOGMINER_TBS;

-- 查看表空间中的表
SELECT tablespace_name, table_name FROM user_tables WHERE tablespace_name = 'LOGMINER_TBS';

-- 插入 10 条模拟数据
INSERT ALL
  INTO customers (customer_id, customer_name, email, phone) VALUES (1, 'John Doe', 'john.doe@example.com', '123-456-7890')
  INTO customers (customer_id, customer_name, email, phone) VALUES (2, 'Jane Smith', 'jane.smith@example.com', '234-567-8901')
  INTO customers (customer_id, customer_name, email, phone) VALUES (3, 'Bob Johnson', 'bob.johnson@example.com', '345-678-9012')
  INTO customers (customer_id, customer_name, email, phone) VALUES (4, 'Alice Brown', 'alice.brown@example.com', '456-789-0123')
  INTO customers (customer_id, customer_name, email, phone) VALUES (5, 'Charlie Davis', 'charlie.davis@example.com', '567-890-1234')
  INTO customers (customer_id, customer_name, email, phone) VALUES (6, 'Eva Wilson', 'eva.wilson@example.com', '678-901-2345')
  INTO customers (customer_id, customer_name, email, phone) VALUES (7, 'Frank Miller', 'frank.miller@example.com', '789-012-3456')
  INTO customers (customer_id, customer_name, email, phone) VALUES (8, 'Grace Lee', 'grace.lee@example.com', '890-123-4567')
  INTO customers (customer_id, customer_name, email, phone) VALUES (9, 'Henry Taylor', 'henry.taylor@example.com', '901-234-5678')
  INTO customers (customer_id, customer_name, email, phone) VALUES (10, 'Ivy Chen', 'ivy.chen@example.com', '012-345-6789')
SELECT * FROM dual;

-- 验证插入的数据
SELECT * FROM customers;

-- 插入 5 条模拟数据
INSERT ALL
  INTO product (product_id, product_name, price) VALUES (1, 'Product A', 10)
  INTO product (product_id, product_name, price) VALUES (2, 'Product B', 20)
  INTO product (product_id, product_name, price) VALUES (3, 'Product C', 30)
  INTO product (product_id, product_name, price) VALUES (4, 'Product D', 40)
  INTO product (product_id, product_name, price) VALUES (5, 'Product E', 50)
SELECT * FROM dual;

-- 验证插入的数据
SELECT * FROM product;

-- 提交事务
COMMIT;
```

## Doris-Flink-Connector 同步
- [Flink Doris Connector 24.0.1](https://doris.apache.org/download#doris-ecosystem)
- [flink-sql-connector-oracle-cdc-3.5.0](https://repo1.maven.org/maven2/org/apache/flink/flink-sql-connector-oracle-cdc/3.5.0/flink-sql-connector-oracle-cdc-3.5.0.jar)
- [ojdbc8-19.3.0.0](https://repo1.maven.org/maven2/com/oracle/ojdbc/ojdbc8/19.3.0.0/ojdbc8-19.3.0.0.jar)

```sh
docker exec -it jobmanager bash
bin/flink run \
    -Dexecution.checkpointing.interval=10s \
    -Dparallelism.default=1 \
    -c org.apache.doris.flink.tools.cdc.CdcTools \
    ./lib/flink-doris-connector-1.20-24.0.1.jar \
    oracle-sync-database \
    --database test_db \
    --oracle-conf hostname=127.0.0.1 \
    --oracle-conf port=1521 \
    --oracle-conf username=flinkuser \
    --oracle-conf password="flinkpw" \
    --oracle-conf database-name=helowin \
    --oracle-conf schema-name=FLINKUSER \
    --oracle-conf debezium.log.mining.strategy=online_catalog \
    --oracle-conf debezium.log.mining.continuous.mine=true \
    --oracle-conf debezium.database.history.store.only.captured.tables.ddl=true \
    --including-tables "CUSTOMERS|PRODUCT" \
    --sink-conf fenodes=127.0.0.1:8030 \
    --sink-conf username=root \
    --sink-conf password=\
    --sink-conf jdbc-url=jdbc:mysql://127.0.0.1:9030 \
    --sink-conf sink.label-prefix=label \
    --table-conf replication_num=1
```

debezium 相关参数
- `--oracle-conf debezium.log.mining.strategy=online_catalog`: 设置日志挖掘策略。比其他策略更快，不需要解析重做日志来获取元数据
- `--oracle-conf debezium.log.mining.continuous.mine=true`: 启用连续挖掘模式。可以显著提高性能
- `--oracle-conf debezium.database.history.store.only.captured.tables.ddl=true`: 只存储被捕获表的 DDL（数据定义语言）历史。减少存储开销

## Runtime Environment
- [Doris 2.1](https://doris.apache.org/zh-CN/download)
- [Flink Doris Connector 24.0.1](https://doris.apache.org/zh-CN/download)
- [Flink 1.20](https://flink.apache.org/downloads/)
- [Flink CDC 3.5.0](https://github.com/apache/flink-cdc/releases)
- [Oracle Database 11g](https://www.oracle.com/downloads/)

## References
- [使用 Flink 同步 Oracle 数据至 Doris](https://juejin.cn/post/7424901256136048666)
- [Oracle CDC Connector](https://nightlies.apache.org/flink/flink-cdc-docs-release-3.5/zh/docs/connectors/flink-sources/oracle-cdc/)