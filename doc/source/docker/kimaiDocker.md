# Kimai Docker

Time tracking for project-driven teams

## Demo
- [Demo](https://www.kimai.org/en/demo.html)
- [Kimai API](https://demo.kimai.org/api/doc)

## Docker
```sh
docker run -d \
  --name=kimai \
  -e PUID=1000 \
  -e PGID=1000 \
  -e TZ=Etc/UTC \
  -e DATABASE_URL=mysql://your_db_user:your_db_pass@your_db_host:3306/your_db_name?charset=your_db_charset&serverVersion=your_db_version \
  -p 80:80 \
  -p 443:443 \
  -v /path/to/kimai/config:/config \
  --restart unless-stopped \
  lscr.io/linuxserver/kimai:latest

# This container requires an external application to be run separately.
docker run -d \
  --name=mariadb \
  -e PUID=1000 \
  -e PGID=1000 \
  -e TZ=Etc/UTC \
  -e MYSQL_ROOT_PASSWORD=ROOT_ACCESS_PASSWORD \
  -e MYSQL_DATABASE=your_db_name `#optional` \
  -e MYSQL_USER=your_db_user `#optional` \
  -e MYSQL_PASSWORD=your_db_pass `#optional` \
  -p 3306:3306 \
  -v path_to_data:/config \
  --restart unless-stopped \
  lscr.io/linuxserver/mariadb:latest
```

## Docker Compose
`kimai.yml`
```
---
services:
  kimai:
    image: lscr.io/linuxserver/kimai:latest
    container_name: kimai
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Etc/UTC
      - DATABASE_URL=mysql://your_db_user:your_db_pass@your_db_host:3306/your_db_name?charset=your_db_charset&serverVersion=your_db_version
    volumes:
      - /path/to/kimai/config:/config
    ports:
      - 80:80
      - 443:443
    restart: unless-stopped

# This container requires an external application to be run separately.
# MariaDB
  mariadb:
    image: lscr.io/linuxserver/mariadb:latest
    container_name: mariadb
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Etc/UTC
      - MYSQL_ROOT_PASSWORD=ROOT_ACCESS_PASSWORD
      - MYSQL_DATABASE=your_db_name #optional
      - MYSQL_USER=your_db_user #optional
      - MYSQL_PASSWORD=your_db_pass #optional
    volumes:
      - path_to_data:/config
    ports:
      - 3306:3306
    restart: unless-stopped
```

## Application Setup
### Configure your database connection
```sh
docker exec -it mariadb bash
sh -c 'mysql -u root -p123456 -e "SELECT VERSION() \G"'
```
- example for MariaDB use "10.11.4-MariaDB-log"
- example for MySQL use "8.0.32"

### Create your first user
```sh
docker exec -it kimai console kimai:user:create your_username admin@example.com ROLE_SUPER_ADMIN
```
you can configure [LDAP](https://www.kimai.org/documentation/ldap.html) or [SAML](https://www.kimai.org/documentation/saml.html) for authentication

## Runtime Environment
- [PHP](https://www.php.net/downloads)
- [MariaDB](https://mariadb.org/download/)
- [MySQL](http://www.mysql.com/)

## Screenshots
![](https://www.kimai.org/images/screenshots/screenshot-dashboard.webp)

![](https://www.kimai.org/images/screenshots/screenshot-reporting.webp)

## References
- [Kimai](https://www.kimai.org/)
- [Kimai GitHub](https://github.com/kimai/kimai)
- [Kimai Docker](https://www.kimai.org/documentation/docker.html)
- [linuxserver/kimai Docker](https://docs.linuxserver.io/images/docker-kimai/)
- [Kimai Installation](https://www.kimai.org/documentation/installation.html)
- [Kimai Plugins](https://www.kimai.org/store/)
- [Kimai Backups](https://www.kimai.org/documentation/backups.html)
- [Quickstart for Kimai API](https://www.kimai.org/documentation/rest-api.html)