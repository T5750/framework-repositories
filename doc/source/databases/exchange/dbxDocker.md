# DBX Docker

DBX 将连接管理、SQL 编辑、数据表格、结构工具、AI 助手和自托管访问放进一个轻量产品里。

## Docker
```sh
docker run -d \
  --pull=always \
  --name dbx \
  -p 4224:4224 \
  -v dbx-data:/app/data \
  t8y2/dbx:latest
docker run -d --name dbx -p 4224:4224 docker.cnb.cool/dbxio.com/dbx
```
[http://localhost:4224/](http://localhost:4224/)

## Docker Compose
```
services:
  dbx:
    image: t8y2/dbx:latest
    # 中国大陆用户可改用 CNB 镜像，以加快拉取速度：
    # image: docker.cnb.cool/dbxio.com/dbx:latest
    pull_policy: always
    ports:
      - "4224:4224"
    volumes:
      - dbx-data:/app/data
    restart: unless-stopped

volumes:
  dbx-data:
```

## Screenshots
![](https://dbxio.com/screenshots/dbx-light-2560.webp)

![](https://dbxio.com/screenshots/dbx-er-2560.webp)

## References
- [DBX](https://dbxio.com/cn)
- [DBX GitHub](https://github.com/t8y2/dbx)
- [DBX 快速开始](https://dbxio.com/cn/docs/getting-started)