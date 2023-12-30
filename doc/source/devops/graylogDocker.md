# Graylog Docker

Graylog provides answers to your team’s security, application, and IT infrastructure questions by enabling you to combine, enrich, correlate, query, and visualize all your log data in one place.

## Docker
```sh
docker run --name graylog --link mongo --link elasticsearch -p 9000:9000 -p 12201:12201 -p 1514:1514 -p 5555:5555 -e GRAYLOG_HTTP_EXTERNAL_URI="http://127.0.0.1:9000/" -d graylog/graylog:5.1
```
[http://localhost:9000/](http://localhost:9000/)

## Docker Compose
`graylog.yml`

## Runtime Environment
- [Java 17](https://github.com/openjdk/jdk)

## Architecture
### Minimum Setup
![](https://go2docs.graylog.org/5-2/resources/images/planning_your_deployment/architec_small_setup.png)

### Bigger Production Setup
![](https://go2docs.graylog.org/5-2/resources/images/planning_your_deployment/architec_bigger_setup.png)

## Screenshots
![](https://go2docs.graylog.org/5-2/resources/images/docker_installation_inputs.png)

![](https://go2docs.graylog.org/5-2/resources/images/docker_installation_input_modal.png)

## References
- [Graylog](https://graylog.org/)
- [Graylog GitHub](https://github.com/Graylog2/graylog2-server)
- [Graylog Docker](https://go2docs.graylog.org/5-2/downloading_and_installing_graylog/docker_installation.htm)
- [Architectural Considerations](https://go2docs.graylog.org/5-2/planning_your_deployment/planning_your_deployment.html)
- [如何在 Docker 容器中运行 Graylog Server](https://cn.linux-console.net/?p=20662)