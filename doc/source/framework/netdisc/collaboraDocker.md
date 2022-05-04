# Collabora Docker

Collabora Online is a collaborative online office suite based on LibreOffice technology. This is also the source for the Collabora Office apps for iOS and Android.

## Docker
```
docker run -t -d --name collabora -p 9980:9980 -e "username=admin" -e "password=123456" --restart always collabora/code
```

- [https://localhost:9980/loleaflet/dist/admin/admin.html](https://localhost:9980/loleaflet/dist/admin/admin.html)
- User: admin / 123456

## Screenshots
![](https://collaboraonline.github.io/images/homepage-image.jpg)

![](https://sdk.collaboraonline.com/_images/theme-cssvars-exported2.png)

## References
- [CODE Docker image](https://sdk.collaboraonline.com/docs/installation/CODE_Docker_image.html)
- [Collabora Online Development Edition](https://collaboraonline.github.io/)
- [CollaboraOnline GitHub](https://github.com/CollaboraOnline/online)
- [collabora/code Docker](https://hub.docker.com/r/collabora/code)