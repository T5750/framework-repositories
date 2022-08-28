# StreamSets Data Collector Docker

Build data ingestion pipelines from any source to any destination

## Docker
```sh
docker run --restart=always -p 18630:18630 -d --name streamsets-dc streamsets/datacollector
docker run --restart=always -p 18630:18630 -d --name streamsets-dc streamsets/datacollector:3.18.1
```
- [http://localhost:18630/](http://localhost:18630/)
- User: admin / admin

### Detailed Usage
```sh
docker run -v $PWD/sdc.properties:/etc/sdc/sdc.properties:ro -v $PWD/sdc-data:/data:rw -p 18630:18630 -d streamsets/datacollector dc
```

### Creating a Data Volumes
```sh
docker volume create --name sdc-data
docker run -v sdc-data:/data -P -d streamsets/datacollector dc
```

## Screenshots
![](https://streamsets.b-cdn.net/wp-content/uploads/summer_21_beta_setup_a_deployment.png)

![](https://streamsets.b-cdn.net/wp-content/uploads/summer_21_beta_build_a_pipeline.png)

![](https://streamsets.b-cdn.net/wp-content/uploads/summer_21_beta_run_a_job.png)

![](https://streamsets.b-cdn.net/wp-content/uploads/summer_21_beta_monitor_a_job.png)

## References
- [StreamSets Data Collector Docker](https://hub.docker.com/r/streamsets/datacollector)
- [StreamSets Data Collector GitHub](https://github.com/streamsets/datacollector-docker)
- [StreamSets Getting Started](https://streamsets.com/getting-started/)