# Drawio Docker

## Introduction
[diagrams.net](https://github.com/jgraph/drawio) (formerly draw.io) is free online diagram software. You can use it as a flowchart maker, network diagram software, to create UML online, as an ER diagram tool, to design database schema, to build BPMN online, as a circuit diagram maker, and more.

## Docker
```
docker run -it --rm --name=draw -p 8080:8080 -p 8443:8443 jgraph/drawio
```
[http://localhost:8080/](http://localhost:8080/)

## Snapshots
![home-dia1](https://www.diagrams.net/assets/svg/home-dia1.svg)

## References
- [docker-drawio](https://github.com/jgraph/docker-drawio)