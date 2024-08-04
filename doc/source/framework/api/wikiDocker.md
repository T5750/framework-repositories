# Wiki.js Docker

A modern and powerful wiki app built on Node.js

## Demo
[Demo](https://docs.requarks.io/demo)

## Docker
```sh
# PostgreSQL is the recommended engine for best performance, features and future compatibility.
docker run -d -p 8080:3000 --name wiki --restart unless-stopped -e "DB_TYPE=postgres" -e "DB_HOST=db" -e "DB_PORT=5432" -e "DB_USER=wikijs" -e "DB_PASS=wikijsrocks" -e "DB_NAME=wiki" ghcr.io/requarks/wiki:2
# MySQL
docker run -d -p 8080:3000 --name wiki --restart unless-stopped -e "DB_TYPE=mysql" -e "DB_HOST=db" -e "DB_PORT=3306" -e "DB_USER=wikijs" -e "DB_PASS=wikijsrocks" -e "DB_NAME=wiki" ghcr.io/requarks/wiki:2
# SQLite is not recommended for production use. It is only provided for low-end systems and development purposes.
docker run -d -p 8080:3000 --name wiki --restart unless-stopped -e "DB_TYPE=sqlite" -e "DB_FILEPATH=db.sqlite" ghcr.io/requarks/wiki:2
```
[http://localhost:8080/](http://localhost:8080/)

## Docker Compose
```
version: "3"
services:

  db:
    image: postgres:15-alpine
    environment:
      POSTGRES_DB: wiki
      POSTGRES_PASSWORD: wikijsrocks
      POSTGRES_USER: wikijs
    logging:
      driver: "none"
    restart: unless-stopped
    volumes:
      - db-data:/var/lib/postgresql/data

  wiki:
    image: ghcr.io/requarks/wiki:2
    depends_on:
      - db
    environment:
      DB_TYPE: postgres
      DB_HOST: db
      DB_PORT: 5432
      DB_USER: wikijs
      DB_PASS: wikijsrocks
      DB_NAME: wiki
    restart: unless-stopped
    ports:
      - "80:3000"

volumes:
  db-data:
```

## Runtime Environment
- [Vue.js](https://github.com/vuejs/vue)

## Screenshots
![](https://js.wiki/img/wiki-screenshot-2x.830b799c.png)

![](https://docs.requarks.io/assets/ui/ui-basics.jpg)

![](https://docs.requarks.io/assets/diagrams/diag-letsencrypt.png)

## References
- [Wiki.js](https://js.wiki)
- [Wiki.js Docker](https://docs.requarks.io/install/docker)
- [Wiki.js GitHub](https://github.com/requarks/wiki)
- [Wiki.js Interface](https://docs.requarks.io/guide/intro)
- [Wiki.js Configuration](https://docs.requarks.io/install/config)
- [Wiki.js Git](https://docs.requarks.io/en/storage/git)