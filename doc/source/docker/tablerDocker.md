# Tabler Docker

Develop beautiful web apps with Tabler

## Demo
- [Preview template](https://tabler.io/preview)
- [Dark mode](https://preview.tabler.io/?theme=dark)
- [With sidebar navigation](https://preview.tabler.io/layout-vertical.html?theme=light)
- [With overlap navbar](https://preview.tabler.io/layout-navbar-overlap.html?theme=light)

## Docker
```sh
git clone https://github.com/tabler/tabler.git
docker build -t tabler .
docker run -p 3000:3000 -p 3001:3001 -v $(pwd)/src:/app/src -v $(pwd)/_config.yml:/app/_config.yml tabler
```
[http://localhost:3000/](http://localhost:3000/)

## Screenshots
![](https://tabler.io/_next/image?url=%2Fimg%2Ftabler%2Fpreview.png&w=3840&q=85)

![](https://tabler.io/_next/image?url=%2Fimg%2Ftabler%2Ffeatures%2Fdark.png&w=1080&q=85)

## References
- [Tabler](https://tabler.io/)
- [Tabler GitHub](https://github.com/tabler/tabler)
- [Running with Docker](https://github.com/tabler/tabler#running-with-docker)