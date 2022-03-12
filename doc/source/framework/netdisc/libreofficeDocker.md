# LibreOffice Docker

LibreOffice is a free and powerful office suite, and a successor to OpenOffice.org (commonly known as OpenOffice).

Its clean interface and feature-rich tools help you unleash your creativity and enhance your productivity.

## Setup Nextcloud
```
docker run -d --name nextcloud -p 8080:80 nextcloud:stable
```
[http://localhost:8080/](http://localhost:8080/)

When nextcloud is set up, install the App "Collabora Online". Then go to Configuration->Collabora Online and enter the domain name of your other VM, e.g. http://libreoffice.yourhost:9980

## LibreOffice Online Docker
- `.env`
- `libreoffice.yml`

## Using it
- LibreOffice Online admin dashboard: [http://localhost:9980/loleaflet/dist/admin/admin.html](http://localhost:9980/loleaflet/dist/admin/admin.html)
- LibreOffice Online without using Nextcloud: [http://libreoffice.yourhost:9980/loleaflet/dist/loleaflet.html?file_path=file:///opt/libreoffice/share/template/common/internal/idxexample.odt](http://libreoffice.yourhost:9980/loleaflet/dist/loleaflet.html?file_path=file:///opt/libreoffice/share/template/common/internal/idxexample.odt)

## Screenshots
![](https://zh-cn.libreoffice.org/assets/Uploads/zh-cn/screenshots/writer/writer-main-sidebar.png)

![](https://zh-cn.libreoffice.org/assets/Uploads/zh-cn/screenshots/writer/writer-style-outline-gallary.png)

## References
- [LibreOffice Online Docker](https://hub.docker.com/r/libreoffice/online/)
- [Nextcloud with LibreOffice Online](https://github.com/smehrbrodt/nextcloud-libreoffice-online)
- [LibreOffice 软件截图](https://zh-cn.libreoffice.org/discover/page-826/)