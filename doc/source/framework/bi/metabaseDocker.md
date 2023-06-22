# Metabase Docker

Help your team answer their own questions about data in just 5 minutes—no SQL required.

## Docker
```sh
docker run -d -p 3000:3000 --name metabase metabase/metabase
```
[http://localhost:3000/](http://localhost:3000/)

## Production installation
```sh
createdb metabaseappdb
docker run -d -p 3000:3000 \
  -e "MB_DB_TYPE=postgres" \
  -e "MB_DB_DBNAME=metabaseappdb" \
  -e "MB_DB_PORT=5432" \
  -e "MB_DB_USER=name" \
  -e "MB_DB_PASS=password" \
  -e "MB_DB_HOST=my-database-host" \
   --name metabase metabase/metabase
```

## Docker Compose
```
version: '3.9'
services:
  metabase:
    image: metabase/metabase:latest
    container_name: metabase
    hostname: metabase
    volumes:
    - /dev/urandom:/dev/random:ro
    ports:
      - 3000:3000
    environment:
      MB_DB_TYPE: postgres
      MB_DB_DBNAME: metabase
      MB_DB_PORT: 5432
      MB_DB_USER_FILE: /run/secrets/db_user
      MB_DB_PASS_FILE: /run/secrets/db_password
      MB_DB_HOST: postgres
    networks:
      - metanet1
    secrets:
      - db_password
      - db_user
    healthcheck:
      test: curl --fail -I http://localhost:3000/api/health || exit 1
      interval: 15s
      timeout: 5s
      retries: 5
  postgres:
    image: postgres:latest
    container_name: postgres
    hostname: postgres
    environment:
      POSTGRES_USER_FILE: /run/secrets/db_user
      POSTGRES_DB: metabase
      POSTGRES_PASSWORD_FILE: /run/secrets/db_password
    networks:
      - metanet1
    secrets:
      - db_password
      - db_user
networks:
  metanet1:
    driver: bridge
secrets:
   db_password:
     file: db_password.txt
   db_user:
     file: db_user.txt
```

## Screenshots
![](https://www.metabase.com/images/stats-dashboard.svg)

![](https://www.metabase.com/images/product/ProductFrame.png)

![](https://www.metabase.com/images/product/Share-Discoveries@3x.png)

## References
- [Metabase](https://www.metabase.com/)
- [Metabase GitHub](https://github.com/metabase/metabase)
- [Metabase Docker](https://www.metabase.com/docs/latest/installation-and-operation/running-metabase-on-docker)