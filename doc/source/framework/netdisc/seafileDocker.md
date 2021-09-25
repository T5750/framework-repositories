# Seafile Docker

Seafile is an open source cloud storage system with privacy protection and teamwork features. Collections of files are called libraries. Each library can be synced separately. A library can also be encrypted with a user chosen password. Seafile also allows users to create groups and easily sharing files into groups.

## For seafile 8.x.x
`seafile.yml`

## For seafile 6.x.x
```
docker run -d --name seafile \
  -e SEAFILE_ADMIN_EMAIL=admin@example.com \
  -e SEAFILE_ADMIN_PASSWORD=admin \
  -v /opt/seafile-data:/shared \
  -p 8000:8000 \
  -p 8082:8082 \
  seafileltd/seafile
```

- SERVICE_URL: `http://localhost:8000`
- FILE_SERVER_ROOT: `http://localhost:8082`
- [http://localhost:8000/](http://localhost:8000/)

## Snapshots
![libraries_view](https://help.seafile.com/file_folder_managing/imgs/libraries_view.png)

![library_browse](https://help.seafile.com/file_folder_managing/imgs/library_browse.png)

![seafile-server-config](https://manual.seafile.com/images/seafile-server-config.png)

## References
- [Seafile](https://github.com/haiwen/seafile)
- [seafile-docker](https://github.com/haiwen/seafile-docker)
- [Deploy Seafile with Docker](https://manual.seafile.com/docker/deploy_seafile_with_docker/)
- [Seafile User Manual](https://help.seafile.com/)