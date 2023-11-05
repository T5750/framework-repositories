# TimescaleDB Docker

An open-source time-series SQL database optimized for fast ingest and complex queries. Packaged as a PostgreSQL extension.

## Docker
The [timescaledb-ha](https://hub.docker.com/r/timescale/timescaledb-ha) image offers the most complete TimescaleDB experience. It includes the [TimescaleDB Toolkit](https://github.com/timescale/timescaledb-toolkit), and support for PostGIS and Patroni. If you need the smallest possible image, use the `timescale/timescaledb:latest-pg14` image instead.
```sh
#docker run -d --name timescaledb -p 5432:5432 -e POSTGRES_PASSWORD=password timescale/timescaledb-ha:pg14-latest
docker run -d --name timescaledb -p 5432:5432 -e POSTGRES_PASSWORD=password timescale/timescaledb:latest-pg14
```

### Set up the TimescaleDB extension
```sh
docker exec -it timescaledb psql -U postgres
CREATE database example;
\c example
CREATE EXTENSION IF NOT EXISTS timescaledb;
docker exec -it timescaledb psql -U postgres -h localhost -d example
\dx
```

## References
- [TimescaleDB](https://www.timescale.com/)
- [TimescaleDB GitHub](https://github.com/timescale/timescaledb)
- [TimescaleDB Docker](https://docs.timescale.com/self-hosted/latest/install/installation-docker/)