# Home Assistant Docker

Awaken your home

Open source home automation that puts local control and privacy first. Powered by a worldwide community of tinkerers and DIY enthusiasts. Perfect to run on a Raspberry Pi or a local server.

## DEMO
[VIEW DEMO](https://demo.home-assistant.io/)

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

## Screenshots
![](https://www.home-assistant.io/images/getting-started/lovelace.png)

![](https://www.home-assistant.io/images/getting-started/integrations.png)

![](https://www.home-assistant.io/images/getting-started/automation-editor.png)

![](https://www.home-assistant.io/images/getting-started/new-automation.png)

## References
- [Home Assistant](https://www.home-assistant.io/)
- [Install Home Assistant Operating System](https://www.home-assistant.io/installation/linux)
- [Home Assistant GitHub](https://github.com/home-assistant)