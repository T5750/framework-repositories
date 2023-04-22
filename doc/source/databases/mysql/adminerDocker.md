# Adminer Docker

Adminer (formerly phpMinAdmin) is a full-featured database management tool written in PHP. Conversely to phpMyAdmin, it consist of a single file ready to deploy to the target server. Adminer is available for MySQL, MariaDB, PostgreSQL, SQLite, MS SQL, Oracle, Elasticsearch, MongoDB and others via plugin.

## Demo
- [Adminer Online demo](https://demo.adminer.org/adminer.php)
- [Adminer Editor Online demo](https://demo.adminer.org/editor.php?username=)

## Docker
```sh
docker run --link some_database:db -p 8080:8080 adminer
docker run -d --name adminer -p 8080:8080 adminer
```
[http://localhost:8080/](http://localhost:8080/)

## Docker Compose
```
version: '3.1'
services:
  adminer:
    image: adminer
    restart: always
    ports:
      - 8080:8080
  db:
    image: mysql:5.6
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: example
```

## Choosing a design
```sh
docker run --link some_database:db -p 8080:8080 -e ADMINER_DESIGN='nette' adminer
docker run -d --name adminer -p 8080:8080 -e ADMINER_DESIGN='nette' adminer
# https://github.com/vrana/adminer/tree/master/designs/lucas-sandery/adminer.css
docker cp adminer.css adminer:/var/www/html
```

## Screenshots
![](https://www.adminer.org/static/screenshots/auth.png)

![](https://www.adminer.org/static/screenshots/db.png)

![](https://www.adminer.org/static/screenshots/table.png)

![](https://www.adminer.org/static/designs/lucas-sandery/screenshot.png)

![](https://www.adminer.org/static/designs/pepa-linha-dark/screenshot.png)

## References
- [Adminer](https://www.adminer.org/)
- [Adminer GitHub](https://github.com/vrana/adminer)
- [Adminer Docker](https://hub.docker.com/_/adminer)