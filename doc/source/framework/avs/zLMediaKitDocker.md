# ZLMediaKit Docker

一个基于C++11的高性能运营级流媒体服务框架

WebRTC/RTSP/RTMP/HTTP/HLS/HTTP-FLV/WebSocket-FLV/HTTP-TS/HTTP-fMP4/WebSocket-TS/WebSocket-fMP4/GB28181/SRT server and client framework based on C++11

## Docker
```sh
docker run -id -p 1935:1935 -p 8080:80 -p 8443:443 -p 8554:554 -p 10000:10000 -p 10000:10000/udp -p 8000:8000/udp -p 9000:9000/udp zlmediakit/zlmediakit:master
docker run -id --name zlmediakit -p 1935:1935 -p 8080:80 -p 8443:443 -p 554:554 -p 10000:10000 -p 10000:10000/udp -p 8000:8000/udp -p 9000:9000/udp zlmediakit/zlmediakit:master
```
- [http://localhost:8080/](http://localhost:8080/)
- [https://localhost:8443/](https://localhost:8443/)

## 推流测试
### 使用rtsp方式推流
```sh
# h264推流
ffmpeg -re -i "/test.mp4" -vcodec h264 -acodec aac -f rtsp -rtsp_transport tcp rtsp://127.0.0.1/live/0
# h265推流
ffmpeg -re -i "/test.mp4" -vcodec h265 -acodec aac -f rtsp -rtsp_transport tcp rtsp://127.0.0.1/live/0
```

### 使用rtmp方式推流
```sh
ffmpeg -re -i "/test.mp4" -vcodec h264 -acodec aac -f flv rtmp://127.0.0.1/live/0
# RTMP标准不支持H265,但是国内有自行扩展的，如果你想让FFmpeg支持RTMP-H265,请按照此文章编译：https://github.com/ksvc/FFmpeg/wiki/hevcpush
```

### 使用rtp方式推流
```sh
# h264推流
ffmpeg -re -i "/test.mp4" -vcodec h264 -acodec aac -f rtp_mpegts rtp://127.0.0.1:10000
# h265推流
ffmpeg -re -i "/test.mp4" -vcodec h265 -acodec aac -f rtp_mpegts rtp://127.0.0.1:10000
```

## 播放测试
`RtspMediaSource`支持 rtsp播放、rtsp推流、webrtc播放、webrtc推流
- rtsp://somedomain.com/live/0
- rtsps://somedomain.com/live/0
- rtsp://127.0.0.1/live/0?vhost=somedomain.com
- rtsps://127.0.0.1/live/0?vhost=somedomain.com

`RtmpMediaSource`支持 rtmp推流/播放、http-flv播放、ws-flv播放
- rtmp://somedomain.com/live/0
- rtmps://somedomain.com/live/0
- rtmp://127.0.0.1/live/0?vhost=somedomain.com
- rtmps://127.0.0.1/live/0?vhost=somedomain.com

`HlsMediaSource`支持 hls播放

`TSMediaSource`支持 http-ts播放、ws-ts播放

`FMP4MediaSource`支持 http-fmp4播放、ws-fmp4播放

rtmp类型的流媒体源也支持http-flv、websocket直播，对应的url如下：
- http://somedomain.com/live/0.live.flv
- https://somedomain.com/live/0.live.flv
- http://127.0.0.1/live/0.live.flv?vhost=somedomain.com
- https://127.0.0.1/live/0.live.flv?vhost=somedomain.com
- ws://somedomain.com/live/0.live.flv
- wss://somedomain.com/live/0.live.flv
- ws://127.0.0.1/live/0.live.flv?vhost=somedomain.com
- wss://127.0.0.1/live/0.live.flv?vhost=somedomain.com

ZLMediaKit一般会把rtsp、rtmp流媒体源互相转换，也会转换成hls/http-ts/ws-ts/http-fmp4/ws-fmp4，播放的url如下：
- HLS
    + http://somedomain.com/live/0/hls.m3u8
    + https://somedomain.com/live/0/hls.m3u8
    + http://127.0.0.1/live/0/hls.m3u8?vhost=somedomain.com
    + https://127.0.0.1/live/0/hls.m3u8?vhost=somedomain.com
- HTTP-TS/WS-TS(后缀为`.live.ts`,目的是为了解决与hls的冲突)
    + http://somedomain.com/live/0.live.ts
    + https://somedomain.com/live/0.live.ts
    + http://127.0.0.1/live/0.live.ts?vhost=somedomain.com
    + https://127.0.0.1/live/0.live.ts?vhost=somedomain.com
    + ws://somedomain.com/live/0.live.ts
    + wss://somedomain.com/live/0.live.ts
    + ws://127.0.0.1/live/0.live.ts?vhost=somedomain.com
    + wss://127.0.0.1/live/0.live.ts?vhost=somedomain.com
- HTTP-fMP4/WS-fMP4(后缀为`.live.mp4`,目的是为了解决与mp4点播的冲突)
    + http://somedomain.com/live/0.live.mp4
    + https://somedomain.com/live/0.live.mp4
    + http://127.0.0.1/live/0.live.mp4?vhost=somedomain.com
    + https://127.0.0.1/live/0.live.mp4?vhost=somedomain.com
    + ws://somedomain.com/live/0.live.mp4
    + wss://somedomain.com/live/0.live.mp4
    + ws://127.0.0.1/live/0.live.mp4?vhost=somedomain.com
    + wss://127.0.0.1/live/0.live.mp4?vhost=somedomain.com

## 合作项目
- 可视化管理网站
    + [最新的前后端分离web项目,支持webrtc播放](https://github.com/langmansh/AKStreamNVR)
- 流媒体管理平台
    + [GB28181完整解决方案,自带web管理网站,支持webrtc、h265播放](https://github.com/648540858/wvp-GB28181-pro)
- 播放器
    + [基于wasm支持H265的播放器](https://github.com/numberwolf/h265web.js)

## Architecture
![](https://user-images.githubusercontent.com/11495632/114176523-d50fce80-996d-11eb-81f8-0a2e2715ba7b.png)

![](https://camo.githubusercontent.com/52731b20aeb4c9b288124600f3c8066263123cd49f493697aa9d77452f119ff9/687474703a2f2f7777772e706c616e74756d6c2e636f6d2f706c616e74756d6c2f706e672f584c39424a6d386e344278744c717071655a36346738323938494938384e584f4f2d316545394a6a30307454546a4473356e5f6e6c7a694d4d375250354a6269436a7a795a76616650694462495a4c4457346b425561625a583679326941336e50674c4c396a623832514c583848705753514b5077587363614a426f477557664636414c663467596a43355a6841495479492d3473746a4e464c4b542d5f7258496b437233574f6d48555278482d544978743879786c4d7774527452417171474859684a37374c4c4b576e7534574368395270784b366b7242496e55346b546c335f324555346d676b466c36475f5a764a512d6b754d396f722d6b71745153424f78536f4e57307275423747373076373954584c584657556a35653355375f36564249634b67414f34712d6957554c597275716f4f4e63345a4445507370683641466a30464f54377a72666650633766657958566845306d6a4b6451503033514456774a63615166546c5876764b494f4153525854526e5742476f664f6537624f35586f575076595138775830416b3053524376376e327a54536531495465584c496f6d37376d6b654661714a4c5342364d57666576523270567671705554666b6270385073624772654b6270476f44754b65555a576d365455782d77544c554f415532566650566570506d694b5235436f62784459616372664f4c517255736a78576d416a77574252474c726961794e6a3951596c6d79675f33534b496b3372585071725f7535)

## References
- [ZLMediaKit GitHub](https://github.com/ZLMediaKit/ZLMediaKit)
- [ZLMediaKit推流测试](https://github.com/ZLMediaKit/ZLMediaKit/wiki/ZLMediaKit%E6%8E%A8%E6%B5%81%E6%B5%8B%E8%AF%95)
- [播放url规则](https://github.com/ZLMediaKit/ZLMediaKit/wiki/%E6%92%AD%E6%94%BEurl%E8%A7%84%E5%88%99)
- [怎样创建直播流](https://github.com/ZLMediaKit/ZLMediaKit/wiki/How-to-create-a-live-steam)
- [MediaServer支持的HTTP API](https://github.com/ZLMediaKit/ZLMediaKit/wiki/MediaServer%E6%94%AF%E6%8C%81%E7%9A%84HTTP-API)