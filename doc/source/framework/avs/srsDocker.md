# SRS Docker

## Docker Compose
`srs.yml`

[http://localhost:8080/](http://localhost:8080/)

then publish stream by:
```
ffmpeg -re -i doc/source.200kbps.768x320.flv -c copy \
    -f flv rtmp://localhost/live/livestream

# Or by FFmpeg docker
docker run --rm --network=host registry.cn-hangzhou.aliyuncs.com/ossrs/srs:encoder \
  ffmpeg -re -i ./doc/source.200kbps.768x320.flv -c copy \
      -f flv -y rtmp://localhost/live/livestream
```

## Config
```
docker run --rm -p 1935:1935 -p 1985:1985 -p 8080:8080 \
    -v /path/of/yours.conf:/usr/local/srs/conf/srs.conf \
    ossrs/srs:3
```

## Snapshot
- HttpCallback: http://localhost:8085/live/livestream-best.png
- Transcoder

## Architecture
![](https://camo.githubusercontent.com/ad9f099e7d5f2faaa0564d7e37feb5eaac8b90b133b1a8c4fba9159cab662966/68747470733a2f2f67697465652e636f6d2f77696e6c696e7669702f7372732d77696b692f7261772f6d61737465722f696d616765732f7372732d61726368332d302e706e67)

## References
- [SRS](https://github.com/ossrs/srs)
- [srs-docker](https://github.com/ossrs/srs-docker)
- [SRS Snapshot](https://github.com/ossrs/srs/wiki/v3_CN_Snapshot)