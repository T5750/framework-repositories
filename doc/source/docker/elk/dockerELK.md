# Docker ELK

## ElasticSearch
```sh
docker search elasticsearch
docker pull docker.elastic.co/elasticsearch/elasticsearch:6.3.2
docker images
docker run -d --name es -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:6.3.2
```
```sh
docker exec -it es /bin/bash
vi config/elasticsearch.yml
http.cors.enabled: true
http.cors.allow-origin: "*"
```
```sh
docker restart es
```
[http://localhost:9200/](http://localhost:9200/)

## ElasticSearch-Head
Running with docker
```sh
docker pull mobz/elasticsearch-head:5
docker run -d --name es_admin -p 9100:9100 mobz/elasticsearch-head:5
```

Running with built in server
```sh
git clone git://github.com/mobz/elasticsearch-head.git
cd elasticsearch-head
npm install
npm run start
```
[http://localhost:9100/](http://localhost:9100/)

## Logstash
```sh
docker run --name es_logstash docker.elastic.co/logstash/logstash:6.2.4
docker exec -it es_logstash /bin/bash
vi config/logstash.yml
xpack.monitoring.elasticsearch.url: http://192.168.1.103:9200
```

## Kibana
```sh
docker search kibana
#docker pull kibana:5.6.14
docker pull docker.elastic.co/kibana/kibana-oss:6.3.2
docker run --name kibana-oss -p 5601:5601 -d -e ELASTICSEARCH_URL=http://192.168.1.103:9200 docker.elastic.co/kibana/kibana-oss:6.3.2
docker exec -it kibana-oss /bin/bash
vi config/kibana.yml
```

```
vi pipeline/logstash.conf
input {
  file {
      codec=> json
      path => "/usr/local/*.json"
  }
}
filter {
  #定义数据的格式
  grok {
    match => { "message" => "%{DATA:timestamp}\|%{IP:serverIp}\|%{IP:clientIp}\|%{DATA:logSource}\|%{DATA:userId}\|%{DATA:reqUrl}\|%{DATA:reqUri}\|%{DATA:refer}\|%{DATA:device}\|%{DATA:textDuring}\|%{DATA:duringTime:int}\|\|"}
  }
}
output {
  elasticsearch{
    hosts=> "http://192.168.1.103:9200"
    index => "user-%{+YYYY.MM.dd}"
  }
}
```
[http://localhost:5601/](http://localhost:5601/)

## References
- [基于docker安装ELK](https://www.jianshu.com/p/a0bd70301eec)