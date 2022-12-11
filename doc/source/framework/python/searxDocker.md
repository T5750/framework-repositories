# Searx Docker

Privacy-respecting metasearch engine

## Demo
- [Searx Demo](https://searx.semipvt.com/)
- [SearXNG and SearX instances](https://searx.space/)

## Docker
### Searx
```sh
export PORT=8080
docker run --rm -d -v ${PWD}/searx:/etc/searx -p $PORT:8080 -e BASE_URL=http://localhost:$PORT/ searx/searx
docker run -d --name searx -p 8080:8080 searx/searx
```
[http://localhost:8080/](http://localhost:8080/)

Inside `${PWD}/searx`, you will find `settings.yml` and `uwsgi.ini`. You can modify these files according to your needs and restart the Docker image.

### SearxNG
SearXNG is a free internet metasearch engine which aggregates results from various search services and databases. Users are neither tracked nor profiled.

```sh
export PORT=8080
docker run --rm \
             -d -p ${PORT}:8080 \
             -v "${PWD}/searxng:/etc/searxng" \
             -e "BASE_URL=http://localhost:$PORT/" \
             -e "INSTANCE_NAME=my-instance" \
             searxng/searxng
```

### What is the difference between searx and SearxNG?
If you want to run a public instance, go with SearxNG. If you want to self host your own instance, choose searx.

## Architecture
### Searx
![](https://camo.githubusercontent.com/58e655dc7b1ef437110df029c72281dbf208c8ee0d624b097006bf7ea18b9dfa/68747470733a2f2f7777772e706c616e74746578742e636f6d2f6170692f706c616e74756d6c2f696d672f5a5039444a79436d3338526c2d484c4d78706b6d744e5030716f594332775a476d5a48443742424c685139736f5049616a305a6a6c76454654636c37585a5834696a5f375a6b717558436f4b6662394e6b7641477a4c6f414d67377930416657757372327a64485271316d585935352d65577057636d32694e7a374f32593649656c626f325366586f6e764f67514e574a69737952554b6438524b46744c6442495a3549734b3163305a47574a7473367a4f59585247536d49614e444878546a574961793961614b362d787a38737568554d766d776e2d764174674c6b67656a465649364e5766506334377a72345572546b67444c6d363258483176594e334653794f6e36365a6c42344c6e614a6e5a684f50573951676173673176596175795f41757778676632343552526d66773577414e71474f575075716d70432d576d4f4361416a69554b71476a77495a4e7a6b754e39674f4b76755f645037496c414b7370456e3955516b58735652684c4253386c6c4c6a302d77584a556d5859345845476b6b5838705f6156435031315a547065704679702d796e7930)

### SearxNG
![](https://docs.searxng.org/_images/arch_public.svg)

## Screenshots
### Searx
![](https://user-images.githubusercontent.com/42685606/50028748-ed45cd80-ffad-11e8-8d4a-e265deb75955.png)

## References
- [Searx GitHub](https://github.com/searx/searx)
- [Searx Docker](https://searx.github.io/searx/admin/installation-docker.html)
- [Searx New architecture proposal](https://github.com/searx/searx/wiki/New-architecture-proposal)
- [SearXNG GitHub](https://github.com/searxng/searxng)
- [SearXNG Docker](https://docs.searxng.org/admin/installation-docker.html)
- [SearXNG Architecture](https://docs.searxng.org/admin/architecture.html)