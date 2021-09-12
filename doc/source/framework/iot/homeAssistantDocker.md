# Home Assistant Docker

## Docker
```
docker run -d \
  --name homeassistant \
  --privileged \
  --restart=unless-stopped \
  -e TZ=MY_TIME_ZONE \
  -v /PATH_TO_YOUR_CONFIG:/config \
  --network=host \
  ghcr.io/home-assistant/home-assistant:stable
```

## Docker Compose
`homeassistant.yml`

[http://localhost:8123/](http://localhost:8123/)

## References
- [Install Home Assistant Operating System](https://www.home-assistant.io/installation/linux)