# st

A web interface for MySQL and MariaDB

## Demo
[Try demo](https://demo.phpmyadmin.net/master-config/)

## Docker
```
docker run --name phpmyadmin -d -e PMA_HOST=dbhost -p 8080:80 phpmyadmin:5.1
```
[http://localhost:8080/](http://localhost:8080/)

## Docker Compose
```
version: '3.1'
services:
  db:
    image: mariadb:10.3
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: notSecureChangeMe
  phpmyadmin:
    image: phpmyadmin:5.1
    restart: always
    ports:
      - 8080:80
    environment:
      - PMA_ARBITRARY=1
```

## Screenshots
![](https://www.phpmyadmin.net/static/images/screenshots/monitor.png)

![](https://www.phpmyadmin.net/static/images/screenshots/designer.png)

![](https://www.phpmyadmin.net/static/images/screenshots/users.png)

![](https://www.phpmyadmin.net/static/images/screenshots/gis.png)

![](https://www.phpmyadmin.net/static/images/screenshots/structure.png)

![](https://www.phpmyadmin.net/static/images/screenshots/transformations.png)

![](https://www.phpmyadmin.net/static/images/screenshots/rtl.png)

![](https://www.phpmyadmin.net/static/images/screenshots/advisor.png)

## References
- [phpmyadmin Docker](https://hub.docker.com/_/phpmyadmin)
- [Bringing MySQL to the web](https://www.phpmyadmin.net/try/)