# EasyDarwin Docker

EasyDarwin拥有完整的源代码，帮助开发者更快更简单实现流媒体音视频产品功能

## Demo
[EasyNVR在线演示](http://demo.easynvr.com:10800/)

## Docker Compose
- `easydarwin.yml`
- [http://localhost:10008/](http://localhost:10008/)
- User: admin / admin

```sh
# 测试推流
ffmpeg -re -i C:\Users\Administrator\Videos\test.mkv -rtsp_transport tcp -vcodec h264 -f rtsp rtsp://localhost/test
ffmpeg -re -i C:\Users\Administrator\Videos\test.mkv -rtsp_transport udp -vcodec h264 -f rtsp rtsp://localhost/test
# 测试播放
ffplay -rtsp_transport tcp rtsp://localhost/test
ffplay rtsp://localhost/test
```

## Screenshots
![](http://ww1.sinaimg.cn/large/79414a05ly1fwzqdbi8efj20w00mrn0c.jpg)

## References
- [EasyDarwin](http://www.easydarwin.org/)
- [justtin/easydarwin Docker](https://hub.docker.com/r/justtin/easydarwin)
- [EasyDarwin GitHub](https://github.com/EasyDarwin/EasyDarwin)