# Monica Docker

Personal CRM. Remember everything about your friends, family and business relationships.

## Docker
```sh
mysqlCid="$(docker run -d \
 -e MYSQL_RANDOM_ROOT_PASSWORD=true \
 -e MYSQL_DATABASE=monica \
 -e MYSQL_USER=homestead \
 -e MYSQL_PASSWORD=secret \
 "mysql:5.7")"
docker run -d \
 --link "$mysqlCid":mysql \
 -e DB_HOST=mysql \
 -p 8080:80 \
 monica
 docker run --name monica -d -p 8080:80 -e DB_HOST=mysqlIP -e DB_USERNAME=root -e DB_PASSWORD=123456 monica
```
[http://localhost:8080/](http://localhost:8080/)

## Docker Compose
```
version: "3.4"
services:
  app:
    image: monica
    depends_on:
      - db
    ports:
      - 8080:80
    environment:
      - APP_KEY=
      - DB_HOST=db
      - DB_USERNAME=usermonica
      - DB_PASSWORD=secret
    volumes:
      - data:/var/www/html/storage
    restart: always
  db:
    image: mysql:5.7
    environment:
      - MYSQL_RANDOM_ROOT_PASSWORD=true
      - MYSQL_DATABASE=monica
      - MYSQL_USER=usermonica
      - MYSQL_PASSWORD=secret
    volumes:
      - mysql:/var/lib/mysql
    restart: always
volumes:
  data:
    name: data
  mysql:
    name: mysql
```

## Runtime Environment
- [PHP](https://www.php.net/downloads)
- [MySQL 5.x](http://www.mysql.com/)

## Screenshots
![](https://www.monicahq.com/img/contacts.png)

![](https://www.monicahq.com/img/dashboard.png)

![](https://www.monicahq.com/img/journal.png)

## References
- [Monica Docker](https://github.com/monicahq/docker)
- [Monica GitHub](https://github.com/monicahq/monica)
- [Monica Features](https://www.monicahq.com/features)