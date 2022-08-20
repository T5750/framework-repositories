# Elasticsearch Docker

Elasticsearch is a powerful open source search and analytics engine that makes data easy to explore.

## Docker
```sh
docker run -d --name elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.9.3
```

## Elasticsearch Standalone in Docker Compose
`elasticsearch-standalone.yml`

## Elasticsearch Cluster in Docker Compose
`elasticsearch.yml`

[http://localhost:9200/](http://localhost:9200/)

### Set `vm.max_map_count` to at least `262144`
The `vm.max_map_count` kernel setting must be set to at least `262144` for production use.

The `vm.max_map_count` setting should be set permanently in `/etc/sysctl.conf`:
```
sudo vi /etc/sysctl.conf
vm.max_map_count=262144
```

## Tests
- [http://localhost:9200/_cluster/health?pretty](http://localhost:9200/_cluster/health?pretty)
- [http://localhost:9200/_cat/nodes?pretty](http://localhost:9200/_cat/nodes?pretty)
```
172.18.0.192 55 98 23 2.61 1.65 0.71 dilmrt - es02
172.18.0.191 61 98 22 2.61 1.65 0.71 dilmrt - es01
172.18.0.193 13 98 23 2.61 1.65 0.71 dilmrt * es03
```

## Security
`vi /usr/share/elasticsearch/config/elasticsearch.yml`
```
xpack.security.enabled: true
xpack.security.authc.api_key.enabled: true
```

## Tips
AccessDeniedException: /usr/share/elasticsearch/data/nodes
```sh
chmod 777 elasticsearch/*
```

## References
- [Install Elasticsearch with Docker](https://www.elastic.co/guide/en/elasticsearch/reference/7.5/docker.html)
- [Elasticsearch Docker](https://hub.docker.com/_/elasticsearch)
- [Elasticsearch Guide REST APIs](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/rest-apis.html)
- [Security settings in Elasticsearch](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/security-settings.html#api-key-service-settings)