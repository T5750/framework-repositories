# SRS Docker

SRS is a simple, high efficiency and realtime video server, supports RTMP, WebRTC, HLS, HTTP-FLV and SRT.

## Docker
### SRS 4.x
```sh
docker run --rm -it -p 1935:1935 -p 1985:1985 -p 8080:8080 \
    registry.cn-hangzhou.aliyuncs.com/ossrs/srs:4 ./objs/srs -c conf/docker.conf
```

then publish stream by:
```sh
ffmpeg -re -i ./doc/source.flv -c copy -f flv rtmp://localhost/live/livestream
# Or by FFmpeg docker
docker run --rm -it registry.cn-hangzhou.aliyuncs.com/ossrs/srs:encoder ffmpeg -stream_loop -1 -re -i doc/source.flv \
  -c copy -f flv rtmp://host.docker.internal/live/livestream
```
- [http://localhost:8080/](http://localhost:8080/)
- RTMP (by [VLC](https://www.videolan.org/)): rtmp://localhost/live/livestream
- H5(HTTP-FLV): http://localhost:8080/live/livestream.flv
- H5(HLS): http://localhost:8080/live/livestream.m3u8

### SRS 3.x
```sh
docker run -d --name srs -p 1935:1935 -p 1985:1985 -p 8080:8080 ossrs/srs:3
```

## Docker Compose
`srs.yml`

## Config
```sh
docker run --rm -p 1935:1935 -p 1985:1985 -p 8080:8080 \
    -v /path/of/yours.conf:/usr/local/srs/conf/srs.conf \
    ossrs/srs:3
```

## Snapshot
- HttpCallback: http://localhost:8085/live/livestream-best.png
- Transcoder

## RTSP
```
+-----------+               +----------------------+                +--------+  
| IP Camera +--->--RTSP-->--+ SRS Ingester(FFmpeg) +--->--RTMP-->---+  SRS   +
+-----------+               +----------------------+                +--------+ 
```
>注意：IPC(IP Camera)和SRS Ingester(FFmpeg)一般在内网，FFmpeg推流到内网或公网的SRS。

## GB28181: 反向网关
```
                          |
+-----------+             |     +---------------+  
| IP Camera +-->--GB28181-+->---+  SRS Server   +
+-----------+      (UDP)  |     +---------------+ 
                          |
   公网                反向网关         内网
```

## NVR
NVR可以用RTSP协议从IPC拉流，然后录制，或者转成RTMP后推给SRS

## WebRTC
```sh
CANDIDATE="192.168.1.10"
docker run --rm -it -p 1935:1935 -p 1985:1985 -p 8080:8080 -p 1990:1990 -p 8088:8088 \
    --env CANDIDATE=$CANDIDATE -p 8000:8000/udp \
    registry.cn-hangzhou.aliyuncs.com/ossrs/srs:4 ./objs/srs -c conf/docker.conf
```
>Note: 请将CANDIDATE设置为服务器的外网地址，详细请阅读[WebRTC: CANDIDATE](https://ossrs.net/lts/zh-cn/docs/v4/doc/webrtc#config-candidate)。

## WebRTC for Live Streaming
```sh
CANDIDATE="192.168.1.10"
docker run --rm -it -p 1935:1935 -p 1985:1985 -p 8080:8080 \
    --env CANDIDATE=$CANDIDATE -p 8000:8000/udp \
    registry.cn-hangzhou.aliyuncs.com/ossrs/srs:4 ./objs/srs -c conf/rtmp2rtc.conf
```

## Runtime Environment
- C++

## Architecture
![](https://camo.githubusercontent.com/ad9f099e7d5f2faaa0564d7e37feb5eaac8b90b133b1a8c4fba9159cab662966/68747470733a2f2f67697465652e636f6d2f77696e6c696e7669702f7372732d77696b692f7261772f6d61737465722f696d616765732f7372732d61726368332d302e706e67)

## References
- [SRS GitHub](https://github.com/ossrs/srs)
- [srs-docker GitHub](https://github.com/ossrs/srs-docker)
- [SRS Snapshot](https://github.com/ossrs/srs/wiki/v3_CN_Snapshot)
- [PUSH RTSP is removed，不支持RTSP推流](https://github.com/ossrs/srs/issues/2304)
- [SRS 4.0 Docker](https://ossrs.net/lts/zh-cn/docs/v4/doc/getting-started)