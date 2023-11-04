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

## Docker
```sh
wget https://github.com/alibaba/DataX/raw/master/core/src/main/job/job.json
docker run --rm -v $PWD:/data -w /data vimagick/datax /data/job.json
```

## References
- [DataX GitHub](https://github.com/alibaba/DataX)
- [DataX Quick Start](https://github.com/alibaba/DataX/blob/master/userGuid.md)
- [vimagick/datax Docker](https://github.com/vimagick/dockerfiles/tree/master/datax)