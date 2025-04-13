# SearXNG Docker

SearXNG is a free internet metasearch engine which aggregates results from up to 238 [search services](https://docs.searxng.org/user/configured_engines.html#configured-engines). Users are neither tracked nor profiled. Additionally, SearXNG can be used over Tor for online anonymity.

## Docker
```sh
mkdir my-instance
cd my-instance
docker run -d --name searxng \
             -d -p 8001:8080 \
             -v "${PWD}/searxng:/etc/searxng" \
             -e "BASE_URL=http://localhost:8001/" \
             -e "INSTANCE_NAME=my-instance" \
             searxng/searxng
```
[http://localhost:8001/](http://localhost:8001/)

## Dify SearXNG
### Modify the SearXNG Configuration
`sudo vi my-instance/searxng/settings.yml`
```
search:
  formats:
    - html
    - json
server:
  limiter: false
```

### Authenticate in Dify
Tools > SearXNG > To authorize

## Tips
`sudo vi my-instance/searxng/settings.yml`
```
server:
  bind_address: "127.0.0.1"
  base_url: http://localhost:8001/
ui:
  default_locale: "zh-Hans-CN"
  results_on_new_tab: true
engines:
  - name: bing
    disabled: false
    base_url: "https://cn.bing.com/search"
```

## Runtime Environment
- [Python 3.12.x](https://www.python.org/downloads/)

## Architecture
![](https://docs.searxng.org/_images/arch_public.svg)

## References
- [SearXNG](https://docs.searxng.org)
- [SearXNG GitHub](https://github.com/searxng/searxng)
- [searxng-docker GitHub](https://github.com/searxng/searxng-docker)
- [SearXNG Docker](https://docs.searxng.org/admin/installation-docker.html)
- [SearXNG Administration API](https://docs.searxng.org/admin/api.html)
- [SearXNG Architecture](https://docs.searxng.org/admin/architecture.html)
- [Dify SearXNG](https://marketplace.dify.ai/plugins/langgenius/searxng?language=zh-Hans)