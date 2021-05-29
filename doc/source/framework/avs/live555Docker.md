# LIVE555 Docker

## Docker Compose
`live555.yml`

[rtsp://localhost/mystream-2.ts](rtsp://localhost/mystream-2.ts)

### Tips
mp4 为常见格式，但无法被 LIVE555 Media Server 使用，可使用 [Convertio](https://convertio.co/mp4-ts/) 将 mp4 转成 ts，放在`/data`目录下

## References
- [vimagick/LIVE555](https://hub.docker.com/r/vimagick/live555)
- [使用 Docker 建立 LIVE555 Media Server](https://fpjs.fun/rtsp/live555-docker/)