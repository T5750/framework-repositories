# Neo4j Docker

With Neo4j, the world's most-loved graph database

## Demo
[Web application](https://browser.graphapp.io/)

## Docker
```sh
docker run \
    --restart always \
    --publish=7474:7474 --publish=7687:7687 \
    --env NEO4J_AUTH=neo4j/your_password \
    --volume=/path/to/your/data:/data \
    neo4j:2025.06.2
docker run -d --name neo4j --restart always --publish=7474:7474 --publish=7687:7687 --env NEO4J_AUTH=neo4j/your_password neo4j
```
[http://localhost:7474/](http://localhost:7474/)

## Docker Compose
```
services:
  neo4j:
    image: neo4j:latest
    volumes:
        - /$HOME/neo4j/logs:/logs
        - /$HOME/neo4j/config:/config
        - /$HOME/neo4j/data:/data
        - /$HOME/neo4j/plugins:/plugins
    environment:
        - NEO4J_AUTH=neo4j/your_password
    ports:
      - "7474:7474"
      - "7687:7687"
    restart: always
```

## Runtime Environment
- [Java 17](https://github.com/openjdk/jdk)

## Architecture
![](https://neo4j.com/docs/getting-started/_images/neo4j-architecture-diagram.svg)

## Screenshots
![](https://neo4j.com/docs/browser-manual/current/_images/overview1.png)

![](https://neo4j.com/docs/browser-manual/current/_images/favorites1.png)

## References
- [Neo4j](https://neo4j.com/)
- [Neo4j GitHub](https://github.com/neo4j/neo4j)
- [Neo4j Docker](https://neo4j.com/docs/operations-manual/current/docker/introduction/)
- [Neo4j Browser Visual tour](https://neo4j.com/docs/browser-manual/current/visual-tour/)