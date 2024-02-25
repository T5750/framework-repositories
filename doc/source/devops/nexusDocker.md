# Nexus Docker

Sonatype Nexus Repository Manager; Open-source codebase mirror

## Docker
```sh
docker run -d --name nexus -p 8081:8081 sonatype/nexus3
```
[http://localhost:8081/](http://localhost:8081/)

## Docker Compose
`nexus.yml`

```sh
docker exec -it nexus bash
cat /nexus-data/admin.password
```

## Runtime Environment
- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

## References
- [Nexus](https://www.sonatype.com/products/sonatype-nexus-oss-download)
- [Nexus GitHub](https://github.com/sonatype/nexus-public)
- [Nexus Docker](https://hub.docker.com/r/sonatype/nexus3/)