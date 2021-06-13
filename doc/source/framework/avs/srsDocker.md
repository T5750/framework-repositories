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

## References
- [SRS](https://github.com/ossrs/srs)
- [srs-docker](https://github.com/ossrs/srs-docker)
- [SRS Snapshot](https://github.com/ossrs/srs/wiki/v3_CN_Snapshot)