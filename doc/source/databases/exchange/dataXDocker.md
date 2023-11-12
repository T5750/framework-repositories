# DataX Docker

DataX 是阿里巴巴集团内被广泛使用的离线数据同步工具/平台，实现包括 MySQL、SQL Server、Oracle、PostgreSQL、HDFS、Hive、HBase、OTS、ODPS 等各种异构数据源之间高效的数据同步功能。

## Linux
[DataX下载地址](https://datax-opensource.oss-cn-hangzhou.aliyuncs.com/202309/datax.tar.gz)
```sh
tar -xzvf datax.tar.gz
python datax/bin/datax.py datax/job/job.json
python datax.py {YOUR_JOB.json}
```

### 从stream读取数据并打印到控制台
第一步、创建作业的配置文件（json格式）
```sh
python datax/bin/datax.py -r streamreader -w streamwriter
vi datax/job/stream2stream.json
```
```json
{
  "job": {
    "content": [
      {
        "reader": {
          "name": "streamreader",
          "parameter": {
            "sliceRecordCount": 10,
            "column": [
              {
                "type": "long",
                "value": "10"
              },
              {
                "type": "string",
                "value": "hello，你好，世界-DataX"
              }
            ]
          }
        },
        "writer": {
          "name": "streamwriter",
          "parameter": {
            "encoding": "UTF-8",
            "print": true
          }
        }
      }
    ],
    "setting": {
      "speed": {
        "channel": 5
       }
    }
  }
}
```

第二步：启动DataX
```sh
python datax/bin/datax.py datax/job/stream2stream.json
```

## MysqlReader
```sh
vi datax/job/mysqlreader.json
```
配置一个从Mysql数据库同步抽取数据到本地的作业:
```json
{
    "job": {
        "setting": {
            "speed": {
                 "channel": 3
            },
            "errorLimit": {
                "record": 0,
                "percentage": 0.02
            }
        },
        "content": [
            {
                "reader": {
                    "name": "mysqlreader",
                    "parameter": {
                        "username": "root",
                        "password": "root",
                        "column": [
                            "id",
                            "name"
                        ],
                        "splitPk": "db_id",
                        "connection": [
                            {
                                "table": [
                                    "table"
                                ],
                                "jdbcUrl": [
     "jdbc:mysql://127.0.0.1:3306/database"
                                ]
                            }
                        ]
                    }
                },
               "writer": {
                    "name": "streamwriter",
                    "parameter": {
                        "print":true
                    }
                }
            }
        ]
    }
}
```
配置一个自定义SQL的数据库同步任务到本地内容的作业：
```json
{
    "job": {
        "setting": {
            "speed": {
                 "channel":1
            }
        },
        "content": [
            {
                "reader": {
                    "name": "mysqlreader",
                    "parameter": {
                        "username": "root",
                        "password": "root",
                        "connection": [
                            {
                                "querySql": [
                                    "select db_id,on_line_flag from db_info where db_id < 10;"
                                ],
                                "jdbcUrl": [
                                    "jdbc:mysql://bad_ip:3306/database",
                                    "jdbc:mysql://127.0.0.1:bad_port/database",
                                    "jdbc:mysql://127.0.0.1:3306/database"
                                ]
                            }
                        ]
                    }
                },
                "writer": {
                    "name": "streamwriter",
                    "parameter": {
                        "print": false,
                        "encoding": "UTF-8"
                    }
                }
            }
        ]
    }
}
```
```sh
python datax/bin/datax.py datax/job/mysqlreader.json
```

## MysqlWriter
```sh
vi datax/job/mysqlwriter.json
```
从内存产生到 Mysql 导入的数据
```json
{
    "job": {
        "setting": {
            "speed": {
                "channel": 1
            }
        },
        "content": [
            {
                 "reader": {
                    "name": "streamreader",
                    "parameter": {
                        "column" : [
                            {
                                "value": "DataX",
                                "type": "string"
                            },
                            {
                                "value": 19880808,
                                "type": "long"
                            },
                            {
                                "value": "1988-08-08 08:08:08",
                                "type": "date"
                            },
                            {
                                "value": true,
                                "type": "bool"
                            },
                            {
                                "value": "test",
                                "type": "bytes"
                            }
                        ],
                        "sliceRecordCount": 1000
                    }
                },
                "writer": {
                    "name": "mysqlwriter",
                    "parameter": {
                        "writeMode": "insert",
                        "username": "root",
                        "password": "root",
                        "column": [
                            "id",
                            "name"
                        ],
                        "session": [
                          "set session sql_mode='ANSI'"
                        ],
                        "preSql": [
                            "delete from test"
                        ],
                        "connection": [
                            {
                                "jdbcUrl": "jdbc:mysql://127.0.0.1:3306/datax?useUnicode=true&characterEncoding=gbk",
                                "table": [
                                    "test"
                                ]
                            }
                        ]
                    }
                }
            }
        ]
    }
}
```
```sh
python datax/bin/datax.py datax/job/mysqlwriter.json
```

## Docker
```sh
wget https://github.com/alibaba/DataX/raw/master/core/src/main/job/job.json
docker run --rm -v $PWD:/data -w /data vimagick/datax /data/job.json
```

## References
- [DataX GitHub](https://github.com/alibaba/DataX)
- [DataX Quick Start](https://github.com/alibaba/DataX/blob/master/userGuid.md)
- [DataX MysqlReader 插件文档](https://github.com/alibaba/DataX/blob/master/mysqlreader/doc/mysqlreader.md)
- [DataX MysqlWriter](https://github.com/alibaba/DataX/blob/master/mysqlwriter/doc/mysqlwriter.md)
- [vimagick/datax Docker](https://github.com/vimagick/dockerfiles/tree/master/datax)