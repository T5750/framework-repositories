# Matomo Docker

Matomo (formerly Piwik) is the leading open-source analytics platform that gives you more than just powerful analytics

## Demo
- [Online Demo](https://demo.matomo.cloud/)
- [How to Setup Matomo Analytics (formerly Piwik Analytics) [Video]](https://matomo.org/blog/2012/10/how-to-install-piwik-analytics-video/)

## Docker
```sh
docker run -d --name matomo --link mysql:db matomo
docker run -d --name matomo -p 8080:80 --link mysql:db -v matomo:/var/www/html matomo
```
[http://localhost:8080/](http://localhost:8080/)

## Runtime Environment
- [PHP](https://www.php.net/downloads)
- [MySQL 5.x](http://www.mysql.com/)

## Screenshots
![](https://matomo.org/wp-content/uploads/2008/11/2a-check-success1.png)

![](https://matomo.org/wp-content/uploads/2008/11/7-javascript-tag1.png)

![](https://matomo.org/wp-content/uploads/2008/11/8-congrats1.png)

![](https://matomo.org/wp-content/uploads/2020/06/website-graphics-2020-v5-1-e1611111459689.png)

## References
- [Matomo](https://matomo.org/)
- [Matomo GitHub](https://github.com/matomo-org/matomo)
- [Matomo Docker](https://github.com/matomo-org/docker)
- [Installing Matomo On-Premise](https://matomo.org/faq/on-premise/installing-matomo/)