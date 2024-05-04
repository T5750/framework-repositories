# Message Pusher Docker

搭建专属于你的消息推送服务，支持多种消息推送方式，支持 Markdown，基于 Golang 仅单可执行文件，开箱即用

## Demo
- [在线演示](https://message-pusher.onrender.com/)
- User: root / 123456

## Docker
```sh
docker run -d --restart always --name message-pusher -p 3000:3000 -e TZ=Asia/Shanghai -v /home/ubuntu/data/message-pusher:/data justsong/message-pusher
```
- [http://localhost:3000/](http://localhost:3000/)
- User: root / 123456

## Runtime Environment
- [Go](https://golang.org/)

## References
- [Message Pusher](https://msgpusher.com/)
- [Message Pusher GitHub](https://github.com/songquanpeng/message-pusher)