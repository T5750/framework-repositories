# Camunda Docker

Camunda is a Java-based framework supporting BPMN for workflow and process automation, CMMN for Case Management and DMN for Business Decision Management.

## Docker
```sh
docker run -d --name camunda -p 8080:8080 camunda/camunda-bpm-platform
```
- [http://localhost:8080/camunda-welcome/index.html](http://localhost:8080/camunda-welcome/index.html)
- User: demo / demo

## Modeler
- [Camunda Modeler](https://docs.camunda.org/manual/7.20/modeler/): Modeling tool for BPMN 2.0 and CMMN 1.1 diagrams as well as DMN 1.3 decision tables.
- [bpmn.io](http://bpmn.io/): Open-source project for the modeling framework and toolkits.

## Runtime Environment
- [Java 17](https://github.com/openjdk/jdk)
- [Spring Boot 3.x](https://spring.io/projects/spring-boot)

## Architecture
![](https://docs.camunda.org/manual/7.20/introduction/img/architecture-overview.png)

![](https://docs.camunda.org/manual/7.20/introduction/img/process-engine-architecture.png)

## Screenshots
![](https://docs.camunda.org/manual/7.20/webapps/welcome/img/welcome-start-page-view.png)

![](https://docs.camunda.org/manual/7.20/webapps/admin/img/admin-start-page-view.png)

![](https://docs.camunda.org/manual/7.20/webapps/cockpit/img/dashboard.png)

![](https://docs.camunda.org/manual/7.20/webapps/tasklist/img/tasklist-dashboard.png)

## References
- [Camunda](https://camunda.com/)
- [Camunda GitHub](https://github.com/camunda/camunda-bpm-platform)
- [Camunda Docker](https://docs.camunda.org/manual/7.20/installation/docker/)
- [Camunda Architecture Overview](https://docs.camunda.org/manual/7.20/introduction/architecture/)
- [bpmn.io GitHub](https://github.com/bpmn-io)