# Speed Test Docker

Free and Open Source Speed Test. No Flash, No Java, No Websocket, No Bullshit.

## Docker
```sh
docker run -e MODE=standalone -p 80:80 -it ghcr.io/librespeed/speedtest
docker run -e MODE=standalone -e TELEMETRY=true -e ENABLE_ID_OBFUSCATION=true -e PASSWORD="yourPasswordHere" -e WEBPORT=86 -p 86:86 -v $PWD/db-dir/:/database -it ghcr.io/librespeed/speedtest
docker run -d --name speedtest -e MODE=standalone -p 8080:80 ghcr.io/librespeed/speedtest
```
[http://localhost:8080/](http://localhost:8080/)

## Docker Compose
```
version: '3.7'
services:
  speedtest:
    container_name: speedtest
    image: ghcr.io/librespeed/speedtest:latest
    restart: always
    environment:
      MODE: standalone
      #TITLE: "LibreSpeed"
      #TELEMETRY: "false"
      #ENABLE_ID_OBFUSCATION: "false"
      #REDACT_IP_ADDRESSES: "false"
      #PASSWORD:
      #EMAIL:
      #DISABLE_IPINFO: "false"
      #DISTANCE: "km"
      #WEBPORT: 80
    ports:
      - "80:80" # webport mapping (host:container)
```

## Runtime Environment
- [PHP](https://www.php.net/downloads)

## Screenshots
![Screenrecording of a running Speedtest](https://speedtest.fdossena.com/mpot_v6.gif)

## References
- [Speed Test](https://librespeed.org/)
- [Speed Test GitHub](https://github.com/librespeed/speedtest)
- [Speed Test Docker](https://github.com/librespeed/speedtest/blob/master/doc_docker.md)