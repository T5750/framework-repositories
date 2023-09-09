# CloudBeaver Docker

Cloud Database Manager

## Demo
[Demo server](https://demo.cloudbeaver.io/)

## Docker
```sh
docker run -d --name cloudbeaver -p 8978:8978 -v /var/cloudbeaver/workspace:/opt/cloudbeaver/workspace dbeaver/cloudbeaver
```
[http://localhost:8978/](http://localhost:8978/)

## Screenshots
![](https://github.com/dbeaver/cloudbeaver/wiki/images/demo_screenshot_1.png)

## References
- [CloudBeaver](https://dbeaver.com/)
- [CloudBeaver GitHub](https://github.com/dbeaver/cloudbeaver)
- [CloudBeaver Docker](https://github.com/dbeaver/cloudbeaver/wiki/Run-Docker-Container)