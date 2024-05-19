# Message Nest Docker

Message Nest - 打造个性化消息推送平台，整合邮件、钉钉、企业微信、自定义webhook等多种通知方式。定制你的消息，让通知方式更灵活多样。

## Demo
[Demo](https://message-nest-demo-site.qwapi.eu.org/)

## Docker
```sh

# 正式运行（mysql）
docker run -d  \
  -p 8000:8000 \
  -e MYSQL_HOST=192.168.64.133  \
  -e MYSQL_PORT=3308 \
  -e MYSQL_USER=root \
  -e MYSQL_PASSWORD=Aa123456 \
  -e MYSQL_DB=test_11 \
  -e MYSQL_TABLE_PREFIX=message_ \
  --name message-nest  \
  engigu/message-nest:latest 

# 正式运行（sqlite）
docker run -d  \
  -p 8000:8000 \
  -v you/path/database.db=conf/database.db  \
  --name message-nest  \
  engigu/message-nest:latest 
  ```
- [http://localhost:8000/](http://localhost:8000/)
- User: admin / 123456

## Docker Compose
```
version: "3.7"
services:
  message-nest:
    image: engigu/message-nest:latest
    container_name: message-nest
    restart: always
    ports:
      - "8000:8000"
    environment:
      - MYSQL_HOST=192.168.64.133
      - MYSQL_PORT=3308
      - MYSQL_USER=root
      - MYSQL_PASSWORD=Aa123456
      - MYSQL_DB=test_11
      - MYSQL_TABLE_PREFIX=message_
```

## Runtime Environment
- [Go](https://github.com/golang/go)
- [Vue.js](https://github.com/vuejs/vue)

## Screenshots
![](https://raw.githubusercontent.com/engigu/resources/images/2024/01/26/593a06ac4d1db666acb8a9fb8719e734.gif)

## References
- [Message Nest GitHub](https://github.com/engigu/Message-Push-Nest)