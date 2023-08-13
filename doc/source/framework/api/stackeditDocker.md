# StackEdit Docker

In-browser Markdown editor

## Docker
```sh
docker run -d --name stackedit -p 8080:8080 benweet/stackedit
docker run -d --name stackedit -p 8080:8080 -e STACKEDIT_THEME="dark" benweet/stackedit
docker run -d --name stackedit -p 8080:8080 -v /path/to/data:/data benweet/stackedit
```
[http://localhost:8080/](http://localhost:8080/)

## Docker Compose
```
version: '3.3'
services:
  stackedi:
    image: benweet/stackedit
    restart: always
    ports:
      - 8080:8080
    volumes:
      - ./data:/data
```

## Screenshots
![](https://stackedit.io/static/landing/syntax-highlighting.gif)

![](https://stackedit.io/static/landing/scroll-sync.gif)

![](https://stackedit.io/static/landing/mermaid.gif)

## References
- [StackEdit](https://stackedit.io/)
- [StackEdit GitHub](https://github.com/benweet/stackedit)
- [使用Docker私有部署StackEdit，提升写作效率](https://zhuanlan.zhihu.com/p/639777623)