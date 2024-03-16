# DbGate Docker

The Smartest SQL+noSQL Database Client

## Demo
[Live demo](https://demo.dbgate.org/)

## Docker
```sh
docker run -d --name dbgate --restart always -p 3000:3000 dbgate/dbgate
docker run -d --name dbgate --restart always -p 3000:3000 -e CONNECTIONS='mssql' -e LABEL_mssql='MS SQL' -e SERVER_mssql='SERVER_IP' -e USER_mssql='USER' -e PASSWORD_mssql='PWD' -e ENGINE_mssql='mssql@dbgate-plugin-mssql' dbgate/dbgate
```
[http://localhost:3000/](http://localhost:3000/)

## Docker Compose
```
version: '3'
services:
  dbgate:
    image: dbgate/dbgate
    restart: always
    ports:
      - 80:3000
    volumes:
      - dbgate-data:/root/.dbgate
    environment:
      CONNECTIONS: con1,con2,con3,con4

      LABEL_con1: MySql
      SERVER_con1: mysql
      USER_con1: root
      PASSWORD_con1: TEST
      PORT_con1: 3306
      ENGINE_con1: mysql@dbgate-plugin-mysql

      LABEL_con2: Postgres
      SERVER_con2: postgres
      USER_con2: postgres
      PASSWORD_con2: TEST
      PORT_con2: 5432
      ENGINE_con2: postgres@dbgate-plugin-postgres

      LABEL_con3: MongoDB
      URL_con3: mongodb://mongo:27017
      ENGINE_con3: mongo@dbgate-plugin-mongo

      LABEL_con4: SQLite
      FILE_con4: /home/jan/feeds.sqlite
      ENGINE_con4: sqlite@dbgate-plugin-sqlite

volumes:
  dbgate-data:
    driver: local
```

## Runtime Environment
- [Svelte](https://svelte.dev/)
- [TypeScript](https://www.typescriptlang.org/)

## Screenshots
![](https://dbgate.org/assets/screenshots/datagrid.png)

![](https://dbgate.org/assets/screenshots/diagram.png)

![](https://dbgate.org/assets/screenshots/mongosave.png)

## References
- [DbGate](https://dbgate.org/)
- [DbGate GitHub](https://github.com/dbgate/dbgate)
- [DbGate Docker](https://hub.docker.com/r/dbgate/dbgate)