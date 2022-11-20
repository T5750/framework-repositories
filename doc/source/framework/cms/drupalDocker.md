# Drupal Docker

Drupal is an open source content management platform powering millions of websites and applications.

## Docker
```sh
docker run --name drupal -p 8080:80 -d drupal
```
[http://localhost:8080/](http://localhost:8080/)

### Volumes
```sh
docker run --name drupal -d \
    -v /path/on/host/modules:/var/www/html/modules \
    -v /path/on/host/profiles:/var/www/html/profiles \
    -v /path/on/host/sites:/var/www/html/sites \
    -v /path/on/host/themes:/var/www/html/themes \
    drupal
```

## Docker Compose
```
version: '3.1'
services:
  drupal:
    image: drupal:8-apache
    ports:
      - 8080:80
    volumes:
      - /var/www/html/modules
      - /var/www/html/profiles
      - /var/www/html/themes
      # this takes advantage of the feature in Docker that a new anonymous
      # volume (which is what we're creating here) will be initialized with the
      # existing content of the image at the same location
      - /var/www/html/sites
    restart: always
  postgres:
    image: postgres:10
    environment:
      POSTGRES_PASSWORD: example
    restart: always
```

## Runtime Environment
- [PHP](https://www.php.net/downloads)

## Screenshots
![](https://www.drupal.org/files/docs/user_guide/en/images/config-overview-pencils.png)

![](https://www.drupal.org/files/docs/user_guide/en/images/config-basic-TimeZone.png)

## References
- [Drupal](https://www.drupal.org/)
- [Drupal Docker](https://hub.docker.com/_/drupal)
- [Drupal GitHub](https://github.com/drupal/drupal)