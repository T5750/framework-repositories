# Elasticsearch Docker

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

## Tips
AccessDeniedException: /usr/share/elasticsearch/data/nodes
```
chmod 777 elasticsearch/*
```

## References
- [Install Elasticsearch with Docker](https://www.elastic.co/guide/en/elasticsearch/reference/7.5/docker.html)