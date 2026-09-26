# LibreOffice Docker

LibreOffice is a free and powerful office suite, and a successor to OpenOffice.org (commonly known as OpenOffice).

Its clean interface and feature-rich tools help you unleash your creativity and enhance your productivity.

## Setup Nextcloud
```sh
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

## linuxserver/libreoffice Docker
```sh
docker run -d --name=libreoffice -p 3000:3000 -e username=admin -e password=123456 --restart always --cap-add MKNOD quay.io/linuxserver.io/libreoffice
```
<http://localhost:3000/>

## libreofficedocker/libreoffice-unoserver Docker
A packaged unoserver with REST APIs using Libreoffice in Docker
```sh
docker run -d --name=libreoffice -p 2004:2004 libreofficedocker/libreoffice-unoserver:3.23
```

### API
There is only one POST `/request` API.

**Default payload**
```sh
curl -s -v \
   --request POST \
   --url http://127.0.0.1:2004/request \
   --header 'Content-Type: multipart/form-data' \
   --form "file=@/path/to/your/file.xlsx" \
   --form 'convert-to=pdf' \
   --output 'file.pdf'
```
- `file`: Type of `File`, required
- `convert-to`: Type of `String`, required

**Advance payload**
```sh
curl -s -v \
   --request POST \
   --url http://127.0.0.1:2004/request \
   --header 'Content-Type: multipart/form-data' \
   --form "file=@/path/to/your/file.xlsx" \
   --form 'convert-to=pdf' \
   --form 'opts[]=--landscape' \
   --output 'file.pdf'
```
- `file`: Type of `File`, required
- `convert-to`: Type of `String`, required
- `opts`: Type of `String[]`

## Screenshots
![](https://zh-cn.libreoffice.org/assets/Uploads/zh-cn/screenshots/writer/writer-main-sidebar.png)

![](https://zh-cn.libreoffice.org/assets/Uploads/zh-cn/screenshots/writer/writer-style-outline-gallary.png)

## References
- [LibreOffice Online Docker](https://hub.docker.com/r/libreoffice/online/)
- [Nextcloud with LibreOffice Online](https://github.com/smehrbrodt/nextcloud-libreoffice-online)
- [LibreOffice 软件截图](https://zh-cn.libreoffice.org/discover/page-826/)
- [linuxserver/libreoffice Docker](https://docs.linuxserver.io/images/docker-libreoffice/)
- [libreofficedocker/libreoffice-unoserver Docker](https://github.com/libreofficedocker/libreoffice-unoserver)
- [libreofficedocker/unoserver-rest-api GitHub](https://github.com/libreofficedocker/unoserver-rest-api)