# pgAdmin Docker

pgAdmin is the most popular and feature rich Open Source administration and development platform for PostgreSQL, the most advanced Open Source database in the world.

## Docker
```sh
docker run -p 5433:80 \
    -e 'PGADMIN_DEFAULT_EMAIL=admin@pg.com' \
    -e 'PGADMIN_DEFAULT_PASSWORD=postgres' \
    -d dpage/pgadmin4
```

## Docker Compose
`pgadmin.yml`

- [http://localhost:5433/](http://localhost:5433/)
- User: admin@pg.com / postgres

## ERD Tool
Generate ERD (Beta)

## Screenshots
![](https://www.pgadmin.org/static/COMPILED/assets/img/screenshots/pgadmin4-welcome-light.png)

![](https://www.pgadmin.org/static/COMPILED/assets/img/screenshots/pgadmin4-dashboard.png)

![](https://www.pgadmin.org/static/docs/pgadmin4-5.7-docs/_images/erd_tool.png)

![](https://www.pgadmin.org/static/COMPILED/assets/img/screenshots/pgadmin4-geometry.png)

![](https://www.pgadmin.org/static/COMPILED/assets/img/screenshots/pgadmin4-explain.png)

## References
- [pgAdmin](https://www.pgadmin.org/)
- [Container Deployment](https://www.pgadmin.org/docs/pgadmin4/latest/container_deployment.html)
- [ERD Tool](https://www.pgadmin.org/docs/pgadmin4/5.7/erd_tool.html)
- [pgAdmin Docker](https://hub.docker.com/r/dpage/pgadmin4/)
- [pgAdmin Download](https://www.pgadmin.org/download/)