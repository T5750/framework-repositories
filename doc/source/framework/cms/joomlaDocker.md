# Joomla Docker

Joomla! is an open source content management system.

## Docker
```
docker run --name joomla -e JOOMLA_DB_HOST=localhost:3306 \
    -e JOOMLA_DB_USER=root -e JOOMLA_DB_PASSWORD=123456 -p 8080:80 -d joomla
```
- [http://localhost:8080/](http://localhost:8080/)
- [http://localhost:8080/administrator/](http://localhost:8080/administrator/)

## Docker Compose
```
version: '3.1'
services:
  joomla:
    image: joomla
    restart: always
    links:
      - joomladb:mysql
    ports:
      - 8080:80
    environment:
      JOOMLA_DB_HOST: joomladb
      JOOMLA_DB_PASSWORD: example
  joomladb:
    image: mysql:5.6
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: example
```

## Runtime Environment
- [PHP](https://www.php.net/downloads)
- [MySQL 5.x](http://www.mysql.com/)

## Screenshots
![](https://launch.joomla.org/images/what-is-joomla2.jpg)

## References
- [Joomla Docker](https://hub.docker.com/_/joomla)
- [Joomla](https://www.joomla.org/)