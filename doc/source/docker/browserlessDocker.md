# Browserless Docker

Deploy headless browsers in Docker. Run on our cloud or bring your own. Free for non-commercial uses.

## Debugger
[Debugger](https://chrome.browserless.io/)

## Docker
```sh
docker run \
  --rm \
  -p 3000:3000 \
  -e "CONCURRENT=10" \
  -e "TOKEN=6R0W53R135510" \
  ghcr.io/browserless/chromium
docker run -d --name browserless -p 3000:3000 -e "CONCURRENT=10" -e "TOKEN=6R0W53R135510" ghcr.io/browserless/chromium
```
- [OpenAPI](http://localhost:3000/docs)
- [Debbuger](http://localhost:3000/debugger/?token=6R0W53R135510)

## Runtime Environment
- [TypeScript](https://www.typescriptlang.org/)

## Screenshots
![](https://cdn.prod.website-files.com/65cb4923a3a6b08fe1124094/664c72a1d8ef1a9b43e78bf3_Scraping%20with%20Scrapy%20(1).svg)

## References
- [Browserless](https://browserless.io/)
- [Browserless GitHub](https://github.com/browserless/browserless)
- [Browserless Docker](https://docs.browserless.io/Docker/docker-quickstart)
- [Browserless Docker Configuration](https://docs.browserless.io/Docker/docker)