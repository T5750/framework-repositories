# Memos Docker

A lightweight, self-hosted memo hub. Open Source and Free forever.

## Demo
[Live Demo](https://demo.usememos.com/)

## Docker
```sh
docker run -d --name memos -p 5230:5230 -v ~/.memos/:/var/opt/memos neosmemo/memos
```
Memos should be running at [http://localhost:5230](http://localhost:5230). If the `~/.memos/` does not have a `memos_prod.db` file, then memos will auto generate it.

## Docker Compose
```
version: "3.0"
services:
  memos:
    image: neosmemo/memos:latest
    container_name: memos
    volumes:
      - ~/.memos/:/var/opt/memos
    ports:
      - 5230:5230
```

## Screenshots
![](https://usememos.com/demo.webp)

## References
- [Memos](https://usememos.com/)
- [Memos GitHub](https://github.com/usememos/memos)
- [Memos Docker](https://hub.docker.com/r/neosmemo/memos)
- [Memos Installation](https://usememos.com/docs/install)