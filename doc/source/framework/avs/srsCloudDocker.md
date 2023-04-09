# SRS Cloud Docker

SRS Cloud is a lightweight open-source video cloud based on Go, Reactjs, SRS, FFmpeg, WebRTC, etc.

## Docker
```sh
docker run --rm -it -p 2022:2022 -p 1935:1935/tcp -p 1985:1985/tcp \
  -p 8080:8080/tcp -p 8000:8000/udp -p 10080:10080/udp \
  ossrs/srs-cloud:platform-1
```
- [http://localhost:2022/mgmt](http://localhost:2022/mgmt)
- [http://localhost:2022/console/ng_index.html#/summaries?port=2022&http=2022](http://localhost:2022/console/ng_index.html#/summaries?port=2022&http=2022)

All data will be reset when restarting, so please mount volumes if want to save data to local disk:
```sh
docker run --rm -it -p 2022:2022 -p 1935:1935/tcp -p 1985:1985/tcp \
  -p 8080:8080/tcp -p 8000:8000/udp -p 10080:10080/udp \
  -v $HOME/db:/data ossrs/srs-cloud:platform-1
```

## References
- [SRS Cloud GitHub](https://github.com/ossrs/srs-cloud)
- [SRS云服务器合集](https://ossrs.net/lts/zh-cn/docs/v4/tutorial/srs-cloud-server)