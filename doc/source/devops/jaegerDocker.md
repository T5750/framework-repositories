# Jaeger Docker

CNCF Jaeger, a Distributed Tracing Platform

## Docker
### All-in-one
```sh
## make sure to expose only the ports you use in your deployment scenario!
docker run -d --name jaeger \
  -e COLLECTOR_OTLP_ENABLED=true \
  -e COLLECTOR_ZIPKIN_HOST_PORT=:9411 \
  -p 5775:5775/udp \
  -p 6831:6831/udp \
  -p 6832:6832/udp \
  -p 5778:5778 \
  -p 16686:16686 \
  -p 14250:14250 \
  -p 14268:14268 \
  -p 14269:14269 \
  -p 4317:4317 \
  -p 4318:4318 \
  -p 9411:9411 \
  quay.io/jaegertracing/all-in-one:1.58
```
[http://localhost:16686/](http://localhost:16686/)

### Sample App: HotROD
```sh
docker run --rm --link jaeger \
  -p8080-8083:8080-8083 \
  -e OTEL_EXPORTER_OTLP_ENDPOINT="http://jaeger:4318" \
  quay.io/jaegertracing/example-hotrod:1.58 \
  all --otel-exporter=otlp
```
[http://localhost:8080/](http://localhost:8080/)

## Runtime Environment
- [Go v1.13](https://github.com/golang/go)

## Screenshots
![](https://www.jaegertracing.io/img/traces-ss.png)

![](https://www.jaegertracing.io/img/trace-detail-ss.png)

![](https://www.jaegertracing.io/img/frontend-ui/spm.png)

## References
- [Jaeger](https://www.jaegertracing.io/)
- [Jaeger GitHub](https://github.com/jaegertracing/jaeger)
- [Jaeger Docker](https://www.jaegertracing.io/docs/1.58/deployment/)
- [Jaeger Getting Started](https://www.jaegertracing.io/docs/1.58/getting-started/)