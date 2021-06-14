# Alpine Docker

## Usage
Use like you would any other base image:
```
FROM alpine:3.7
RUN apk add --no-cache mysql-client
ENTRYPOINT ["mysql"]
```
This example has a virtual image size of only 36.8MB. Compare that to our good friend Ubuntu:
```
FROM ubuntu:18.04
RUN apt-get update \
    && apt-get install -y --no-install-recommends mysql-client \
    && rm -rf /var/lib/apt/lists/*
ENTRYPOINT ["mysql"]
```
This yields us a virtual image size of about 145MB image.

## Docker Compose
`alpine.yml`

## References
- [Alpine](https://hub.docker.com/_/alpine)