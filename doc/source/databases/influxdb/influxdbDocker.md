# InfluxDB Docker

InfluxDB is a time series database built from the ground up to handle high write and query loads. InfluxDB is meant to be used as a backing store for any use case involving large amounts of timestamped data, including DevOps monitoring, application metrics, IoT sensor data, and real-time analytics.

## Docker
```
docker run -d -p 8086:8086 \
      --name influxdb2 \
      -v $PWD:/var/lib/influxdb2 \
      influxdb:2.0
```
[http://localhost:8086/](http://localhost:8086/)

![](https://www.influxdata.com/wp-content/uploads/APM-Diagram-2.png)

## Screenshots
![](https://docs.influxdata.com/img/influxdb/2-0-data-explorer.png)

![](https://docs.influxdata.com/img/flux/windowed-data.png)

![Configure Grafana to use Flux](https://docs.influxdata.com/img/influxdb/2-0-tools-grafana.png)

## References
- [InfluxDB Docker](https://hub.docker.com/_/influxdb/)
- [InfluxDB OSS 2.0 Documentation](https://docs.influxdata.com/influxdb/v2.0/)