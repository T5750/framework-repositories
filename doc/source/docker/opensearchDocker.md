# OpenSearch Docker

OpenSearch is the flexible, scalable, open-source way to build solutions for data-intensive applications. Explore, enrich, and visualize your data with built-in performance, developer-friendly tools, and powerful integrations for machine learning, data processing, and more.

## Demo
[Playground Demo](https://playground.opensearch.org/)

## Docker
```sh
docker run -d -p 9200:9200 -p 9600:9600 -e "discovery.type=single-node" opensearchproject/opensearch
docker run -d --name opensearch -p 9200:9200 -p 9600:9600 -e "discovery.type=single-node" opensearchproject/opensearch:2.7.0
```
```sh
curl https://localhost:9200 -ku 'admin:admin'
```

## OpenSearch Dashboards
```sh
docker run -d --name opensearch-dashboards -p 5601:5601 -e "server.host=0.0.0.0" -e 'OPENSEARCH_HOSTS=["https://10.1.77.53:9200"]' opensearchproject/opensearch-dashboards:2.7.0
```
[http://localhost:5601/](http://localhost:5601/)

## Docker Compose
```sh
cat /proc/sys/vm/max_map_count
sudo vim /etc/sysctl.conf
vm.max_map_count=262144
sudo sysctl -p
```
`opensearch.yml`

## Runtime Environment
- [Java 11](https://openjdk.java.net/projects/jdk/11/)

## Screenshots
![](https://opensearch.org/docs/latest/images/discover-lucene-syntax.png)

![](https://opensearch.org/docs/latest/images/dashboards/add-sample-data.png)

![](https://opensearch.org/docs/latest/images/interact-filter-dashboard.png)

## References
- [OpenSearch](https://opensearch.org/)
- [OpenSearch GitHub](https://github.com/opensearch-project)
- [OpenSearch Docker](https://opensearch.org/docs/latest/install-and-configure/install-opensearch/docker/)
- [OpenSearch开发环境安装Docker和Docker-Compose两种方式](https://blog.csdn.net/abu935009066/article/details/134569603)