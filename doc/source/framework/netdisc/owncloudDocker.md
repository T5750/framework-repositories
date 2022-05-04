# ownCloud Docker

[ownCloud](https://owncloud.org/) is a self-hosted file sync and share server. It provides access to your data through a web interface, sync clients or WebDAV while providing a platform to view, sync and share across devices easily—all under your control. ownCloud’s open architecture is extensible via a simple but powerful API for applications and plugins and it works with any storage.

## Docker
```
docker run -d --name owncloud -p 8080:80 owncloud:10
```

[http://localhost:8080/](http://localhost:8080/)

For a MySQL database you can link an database container, e.g. `--link my-mysql:mysql`, and then use `mysql` as the database host on setup.

## Runtime Environment
- [PHP](https://www.php.net/downloads)
- [MySQL 5.x](http://www.mysql.com/)

## Screenshots
![](https://doc.owncloud.com/server/10.8/user_manual/_images/files_page.png)

![](https://doc.owncloud.com/server/10.8/user_manual/_images/video_player_2.png)

## References
- [owncloud Docker](https://hub.docker.com/_/owncloud)
- [owncloud/core GitHub](https://github.com/owncloud/core)
- [ownCloud Documentation](https://doc.owncloud.com/server/10.8/)