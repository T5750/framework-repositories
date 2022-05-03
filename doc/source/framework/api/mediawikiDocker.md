# MediaWiki Docker

MediaWiki is free and open-source wiki software. Originally developed by Magnus Manske and improved by Lee Daniel Crocker, it runs on many websites, including Wikipedia, Wiktionary and Wikimedia Commons. It is written in the PHP programming language and stores the contents into a database. Like WordPress, which is based on a similar licensing and architecture, it has become the dominant software in its category.

## Docker
```
docker run --name some-mediawiki -p 8080:80 -d mediawiki
docker cp ./LocalSettings.php some-mediawiki:/var/www/html
```

[http://localhost:8080/](http://localhost:8080/)

## Runtime Environment
- [PHP](https://www.php.net/downloads)
- [MySQL 5.x](http://www.mysql.com/)

## References
- [MediaWiki Docker](https://hub.docker.com/_/mediawiki)