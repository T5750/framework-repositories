# NocoDB Docker

Build Databases As Spreadsheets : No-Coding Required

将 MySQL、PostgreSQL、SQL Server、SQLite 或 MariaDB 转换为智能电子表格。

## Demo
[Get Started](https://app.nocodb.com/#/signin)

## Docker
```sh
# SQLite
docker run -d --name nocodb -p 8080:8080 nocodb/nocodb
# Postgres
docker run -d --name nocodb-postgres \
-v "$(pwd)"/nocodb:/usr/app/data/ \
-p 8080:8080 \
-e NC_DB="pg://host.docker.internal:5432?u=root&p=password&d=d1" \
-e NC_AUTH_JWT_SECRET="569a1821-0a93-45e8-87ab-eb857f20a010" \
nocodb/nocodb:latest
```
[http://localhost:8080/](http://localhost:8080/)

## Docker Compose
```sh
git clone https://github.com/nocodb/nocodb
cd nocodb/docker-compose/2_pg
docker-compose up -d
```

## Runtime Environment
- [TypeScript](https://www.typescriptlang.org/)
- [Vue.js](https://github.com/vuejs/vue)

## Screenshots
![](https://cdn.nocodb.com/marketing-site/20250716131002/images/Grid-View-p-1600.png)

![](https://cdn.nocodb.com/marketing-site/20250716131002/images/new-data-viz-p-1600.png)

## References
- [NocoDB](https://nocodb.com/)
- [NocoDB GitHub](https://github.com/nocodb/nocodb)
- [NocoDB Docker](https://nocodb.com/docs/self-hosting/installation/docker)
- [NocoDB REST API](https://nocodb.com/docs/product-docs/developer-resources/rest-apis)