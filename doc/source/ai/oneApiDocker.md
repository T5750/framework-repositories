# One API Docker

通过标准的 OpenAI API 格式访问所有的大模型，开箱即用

## Demo
[在线演示](https://openai.justsong.cn/)

## Docker
```sh
# 使用 SQLite 的部署命令：
docker run --name one-api -d --restart always -p 3000:3000 -e TZ=Asia/Shanghai -v /home/ubuntu/data/one-api:/data justsong/one-api
# 使用 MySQL 的部署命令，在上面的基础上添加 `-e SQL_DSN="root:123456@tcp(localhost:3306)/oneapi"`，请自行修改数据库连接参数，不清楚如何修改请参见下面环境变量一节。
# 例如：
docker run --name one-api -d --restart always -p 3000:3000 -e SQL_DSN="root:123456@tcp(localhost:3306)/oneapi" -e TZ=Asia/Shanghai -v /home/ubuntu/data/one-api:/data justsong/one-api
```
- [http://localhost:3000/](http://localhost:3000/)
- User: root / 123456

## Runtime Environment
- [Go](https://golang.org/)

## Screenshots
![](https://user-images.githubusercontent.com/39998050/233837954-ae6683aa-5c4f-429f-a949-6645a83c9490.png)

## References
- [One API GitHub](https://github.com/songquanpeng/one-api)
- [使用 API 操控 & 扩展 One API](https://github.com/songquanpeng/one-api/blob/main/docs/API.md)