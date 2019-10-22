# Oracle中TO_DATE TO_CHAR格式

## TO_CHAR
使用`TO_CHAR`函数处理数字：`TO_CHAR(number,'格式');`

`TO_CHAR(salary,'$99,999.99');`

使用`TO_CHAR`函数处理日期：`TO_CHAR(date,'格式');` 

SQL | Result | Comment 
------|----|----
`SYSDATE` | 2009-6-16 15:25:10 | 　
`TRUNC(SYSDATE)` | 2009-6-16 | 　
`TO_CHAR(SYSDATE,'YYYYMMDD')` | 20090616 | 到日
`TO_CHAR(SYSDATE,'YYYYMMDD HH24:MI:SS')` | 20090616 15:25:10 | 到秒
`TO_CHAR(SYSTIMESTAMP,'YYYYMMDD HH24:MI:SS.FF3')` | 20090616 15:25:10.848 | 到毫秒
`TO_CHAR(SYSDATE,'AD')` | 公元 | 　
`TO_CHAR(SYSDATE,'AM')` | 下午 | 　
`TO_CHAR(SYSDATE,'BC')` | 公元 | 　
`TO_CHAR(SYSDATE,'CC')` | 21 | 　
`TO_CHAR(SYSDATE,'D')` | 3 | 老外的星期几
`TO_CHAR(SYSDATE,'DAY')` | 星期二 | 星期几
`TO_CHAR(SYSDATE,'DD')` | 16 | 　
`TO_CHAR(SYSDATE,'DDD')` | 167 | 　
`TO_CHAR(SYSDATE,'DL')` | 2009年6月16日 星期二 | 　
`TO_CHAR(SYSDATE,'DS')` | 2009-06-16 | 　
`TO_CHAR(SYSDATE,'DY')` | 星期二 | 　
`TO_CHAR(SYSTIMESTAMP,'SS.FF3')` | 10.848 | 毫秒
`TO_CHAR(SYSDATE,'FM')` | 　 | 　
`TO_CHAR(SYSDATE,'FX')` | 　 | 　
`TO_CHAR(SYSDATE,'HH')` | 03 | 　
`TO_CHAR(SYSDATE,'HH24')` | 15 | 　
`TO_CHAR(SYSDATE,'IW')` | 25 | 第几周
`TO_CHAR(SYSDATE,'IYY')` | 009 | 　
`TO_CHAR(SYSDATE,'IY')` | 09 | 　
`TO_CHAR(SYSDATE,'J')` | 2454999 | 　
`TO_CHAR(SYSDATE,'MI')` | 25 | 　
`TO_CHAR(SYSDATE,'MM')` | 06 | 　
`TO_CHAR(SYSDATE,'MON')` | 6月  | 　
`TO_CHAR(SYSDATE,'MONTH')` | 6月  | 　
`TO_CHAR(SYSTIMESTAMP,'PM')` | 下午 | 　
`TO_CHAR(SYSDATE,'Q')` | 2 | 第几季度
`TO_CHAR(SYSDATE,'RM')` | VI   | 　
`TO_CHAR(SYSDATE,'RR')` | 09 | 　
`TO_CHAR(SYSDATE,'RRRR')` | 2009 | 　
`TO_CHAR(SYSDATE,'SS')` | 10 | 　
`TO_CHAR(SYSDATE,'SSSSS')` | 55510 | 　
`TO_CHAR(SYSDATE,'TS')` | 下午 3:25:10 | 　
`TO_CHAR(SYSDATE,'WW')` | 24 | 　
`TO_CHAR(SYSTIMESTAMP,'W')` | 3 | 　
`TO_CHAR(SYSDATE,'YEAR')` | TWO THOUSAND NINE | 　
`TO_CHAR(SYSDATE,'YYYY')` | 2009 | 　
`TO_CHAR(SYSTIMESTAMP,'YYY')` | 009 | 　
`TO_CHAR(SYSTIMESTAMP,'YY')` | 09

## TO_DATE
使用`TO_DATE`函数将字符转换为日期：`TO_DATE(date,'格式')`

格式控制 | 描述
----|----
`YYYY`、`YYY`、`YY` | 分别代表4位、3位、2位的数字年
`YEAR` | 年的拼写
`MM` | 数字月
`MONTH` | 月的全拼
`MON` | 月的缩写
`DD` | 数字日
`DAY` | 星期的全拼
`DY` | 星期的缩写
`AM` | 表示上午或者下午
`HH24`、`HH12` | 12小时制或24小时制
`MI` | 分钟
`SS` | 秒钟
`SP` | 数字的拼写
`TH` | 数字的序数词

```
SELECT TO_DATE('11:25:34','HH12:MI:SS AM') FROM DUAL;
SELECT TO_DATE('2006-05-01 19:25:34','YYYY-MM-DD HH24:MI:SS') FROM DUAL;
SELECT TO_DATE('2006-05-01 19:25','YYYY-MM-DD HH24:MI') FROM DUAL;
SELECT TO_DATE('2006-05-01 19','YYYY-MM-DD HH24') FROM DUAL;
SELECT TO_DATE('2006-05-01','YYYY-MM-DD') FROM DUAL;
SELECT TO_DATE('2006-05','YYYY-MM') FROM DUAL;
SELECT TO_DATE('2006','YYYY') FROM DUAL;
```

日期说明：
- 当省略`HH`、`MI`和`SS`对应的输入参数时，Oracle使用0作为DEFAULT值。如果输入的日期数据忽略时间部分，Oracle会将时、分、秒部分都置为0，也就是说会取整到日。
- 同样，忽略了`DD`参数，Oracle会采用1作为日的默认值，也就是说会取整到月。
- 但是，不要被这种“惯性”所迷惑，如果忽略`MM`参数，Oracle并不会取整到年，取整到当前月。

## TO_NUMBER
使用`TO_NUMBER`函数将字符转换为数字：`TO_NUMBER(number,'格式')`

数字格式 | 格式
----|----
`9` | 代表一个数字
`0` | 强制显示0
`$` | 放置一个$符
`L` | 放置一个浮动本地货币符
`.` | 显示小数点
`,` | 显示千位指示符

## 注意
1. 在使用Oracle的`TO_DATE`函数来做日期转换时，可能会直觉地采用`yyyy-MM-dd HH:mm:ss`的格式作为格式进行转换，但是在Oracle中会引起错误：“ORA 01810 格式代码出现两次”。如：`select to_date('2005-01-01 13:14:20','yyyy-MM-dd HH24:mm:ss') from dual;`原因是SQL中不区分大小写，`MM`和`mm`被认为是相同的格式代码，所以Oracle的SQL采用了`mi`代替分钟。`select to_date('2005-01-01 13:14:20','yyyy-MM-dd HH24:mi:ss') from dual;`
2. 另要以24小时的形式显示出来要用`HH24`
	```
	SELECT TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS') FROM DUAL;// MI是分钟
	SELECT TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MM:SS') FROM DUAL;// MM会显示月份
	```

## References
- [Oracle中TO_DATE TO_CHAR格式](https://blog.csdn.net/delphi308/article/details/25654455)