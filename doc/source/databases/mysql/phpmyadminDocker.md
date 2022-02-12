# phpMyAdmin Docker

A web interface for MySQL and MariaDB

## Demo
[Try demo](https://demo.phpmyadmin.net/master-config/)

## Docker
```
docker run --name phpmyadmin -d -e PMA_HOST=dbhost -p 8080:80 phpmyadmin:5.1
```
[http://localhost:8080/](http://localhost:8080/)

## Docker Compose
`phpmyadmin.yml`
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

## Environment variables summary
- `PMA_ARBITRARY` - when set to 1 connection to the arbitrary server will be allowed
- `PMA_HOST` - define address/host name of the MySQL server
- `PMA_VERBOSE` - define verbose name of the MySQL server
- `PMA_PORT` - define port of the MySQL server
- `PMA_HOSTS` - define comma separated list of address/host names of the MySQL servers
- `PMA_VERBOSES` - define comma separated list of verbose names of the MySQL servers
- `PMA_PORTS` - define comma separated list of ports of the MySQL servers
- `PMA_USER` and `PMA_PASSWORD` - define username to use for config authentication method
- `PMA_ABSOLUTE_URI` - define user-facing URI
- `HIDE_PHP_VERSION` - if defined, will hide the php version (`expose_php = Off`). Set to any value (such as `HIDE_PHP_VERSION=true`).
- `UPLOAD_LIMIT` - if set, will override the default value for apache and php-fpm (default value is 2048 kb)
- `PMA_CONFIG_BASE64` - if set, will override the default `config.inc.php` with the base64 decoded contents of the variable
- `PMA_USER_CONFIG_BASE64` - if set, will override the default `config.user.inc.php` with the base64 decoded contents of the variable

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