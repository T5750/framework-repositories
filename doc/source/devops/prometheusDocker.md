# Prometheus Docker

The [Prometheus](https://github.com/prometheus) monitoring system and time series database.

## Docker
```
docker run -d -p 9090:9090 \
      --name prometheus \
      -v $PWD/prometheus-standalone.yaml:/etc/prometheus/prometheus.yml \
      prom/prometheus
```
[http://localhost:9090/](http://localhost:9090/)

## Architecture
![](https://prometheus.io/assets/architecture.png)

## References
- [INSTALLATION | Prometheus](https://prometheus.io/docs/prometheus/latest/installation/)
- [prometheus](https://github.com/prometheus/prometheus)