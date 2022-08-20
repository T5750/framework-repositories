# Logstash Docker

Logstash - transport and process your logs, events, or other data

## Docker
```sh
docker run -d --name=logstash logstash:7.17.4
docker exec -it logstash /bin/bash
vi config/logstash.yml
xpack.monitoring.elasticsearch.url: http://192.168.1.103:9200
```
`vi pipeline/logstash.conf`
```
input {
  tcp {
    mode => "server"
    host => "0.0.0.0"
    port => 4560
    codec => json_lines
  }
}
output {
  elasticsearch {
    hosts => "es:9200"
    index => "springboot-logstash-%{+YYYY.MM.dd}"
  }
}
```

## Screenshots
![](https://static-www.elastic.co/v3/assets/bltefdd0b53724fa2ce/bltb5aad3a600b5091a/5ca6b1b3e2c6d6592e0b14da/screenshot-arcsight-network.jpg)

![](https://static-www.elastic.co/v3/assets/bltefdd0b53724fa2ce/bltf4648d9066ddf5da/5d27824e8d27ea11c3824290/diagram-logstash.svg)

## References
- [Logstash Docker](https://hub.docker.com/_/logstash)
- [Logstash GitHub](https://github.com/elastic/logstash)
- [Logstash](https://www.elastic.co/cn/logstash/)