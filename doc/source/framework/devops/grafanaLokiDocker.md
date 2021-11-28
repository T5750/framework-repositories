# Grafana Loki Docker

Grafana Loki is a log aggregation tool, and it is the core of a fully-featured logging stack.

## Docker
```
wget https://raw.githubusercontent.com/grafana/loki/v2.4.1/cmd/loki/loki-local-config.yaml -O loki-config.yaml
docker run --name loki -v $(pwd):/mnt/config -p 3100:3100 grafana/loki:2.4.1 -config.file=/mnt/config/loki-config.yaml
wget https://raw.githubusercontent.com/grafana/loki/v2.4.1/clients/cmd/promtail/promtail-docker-config.yaml -O promtail-config.yaml
docker run -v $(pwd):/mnt/config -v /var/log:/var/log --link loki grafana/promtail:2.4.1 -config.file=/mnt/config/promtail-config.yaml
```

## Docker Compose
```
wget https://raw.githubusercontent.com/grafana/loki/v2.4.1/production/docker-compose.yaml -O docker-compose.yaml
docker-compose -f docker-compose.yaml up
```
- `mkdir grafana && chmod 777 grafana`
- `grafana-loki-promtail.yaml`
    * `loki-config.yaml`
    * `promtail-config.yaml`
- [http://localhost:3000/](http://localhost:3000/)

## LogQL: Log query language
Arithmetic Examples
```
1 + 1
```
Log range aggregations
```
count_over_time({job="varlogs"}[5m])
```

## HTTP API
These endpoints are exposed by all components:
- GET /ready
- GET /metrics
- GET /config
- GET /loki/api/v1/status/buildinfo

## Grafana Loki’s Architecture
![](https://grafana.com/docs/loki/latest/fundamentals/overview/loki-overview-2.png)

![](https://grafana.com/docs/loki/latest/fundamentals/architecture/loki_architecture_components.svg)

## References
- [Install Grafana Loki with Docker or Docker Compose](https://grafana.com/docs/loki/latest/installation/docker/)
- [Grafana Loki’s Architecture](https://grafana.com/docs/loki/latest/fundamentals/architecture/)
- [LogQL: Log query language](https://grafana.com/docs/loki/latest/logql/)
- [Grafana Loki HTTP API](https://grafana.com/docs/loki/latest/api/)