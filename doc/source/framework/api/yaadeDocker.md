# Yaade Docker

Yaade is an open-source, self-hosted, collaborative API development environment.

## Docker
```sh
docker volume create yaade
docker run -d --restart=always -p 9339:9339 -e YAADE_ADMIN_USERNAME=admin -v yaade:/app/data --name yaade esperotech/yaade:latest
docker run -d --restart=always -p 9339:9339 -e YAADE_ADMIN_USERNAME=admin --name yaade esperotech/yaade
```
- [http://localhost:9339/](http://localhost:9339/)
- User: admin / password

## Extension
Yaade uses a browser extension as a proxy to enable CORS requests. Install the extension using your browsers extension store. Currently only a chrome extension is available. You can find it <a href="https://chrome.google.com/webstore/detail/yaade-extension/mddoackclclnbkmofficmmepfnadolfa">here</a> (Chrome) and <a href="https://addons.mozilla.org/en-US/firefox/addon/yaade-extension/">here</a> (Firefox). Then open it and input your server URL, eg. `https://yaade.example.com/`. From that point all requests originating from your Yaade browser tabs will be proxied through the extension.

## Upgrade
```sh
docker rm -f yaade
docker pull esperotech/yaade:latest
docker run -d --restart=always -p 9339:9339 -e YAADE_ADMIN_USERNAME=admin -v yaade:/app/data --name yaade esperotech/yaade:latest
```

## Runtime Environment
- [Java 17](https://github.com/openjdk/jdk)
- [Kotlin](https://kotlinlang.org/)

## Screenshots
![](https://github.com/EsperoTech/yaade/raw/main/assets/dark-mode.png)

## References
- [Yaade Documentation](https://docs.yaade.io/)
- [Yaade GitHub](https://github.com/EsperoTech/yaade)
- [Yaade Docker](https://docs.yaade.io/getting-started.html)