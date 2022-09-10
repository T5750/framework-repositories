# Isso Docker

a Disqus alternative

## Demo
[Demo](https://isso-comments.de/)

## Docker
`vi config/isso.cfg`
```
[general]
dbpath = comments.db
host =
    http://localhost/
public-endpoint = http://localhost:8080

[server]
listen = http://0.0.0.0:8080

[admin]
enabled = true
password = 123456
```
```sh
docker run -d --rm --name isso -p 8080:8080 \
    -v $PWD/config:/config -v $PWD/db:/db \
    ghcr.io/isso-comments/isso
```
[http://localhost:8080/admin](http://localhost:8080/admin)

### nginx
`vi html/isso.html`
```html
<html>
  <body>
    <h1>Isso</h1>
    <script
      data-isso="//localhost:8080/"
      src="//localhost:8080/js/embed.min.js"
    ></script>
    <section id="isso-thread"></section>
  </body>
</html>
```
`vi conf.d/default.conf`
```
location = /isso.html {
    root   /etc/nginx/html;
}
```

## Screenshots
![](https://user-images.githubusercontent.com/10212877/167268553-3f30b448-25ff-4850-afef-df2f2e599c93.png)

## References
- [Isso Docker](https://isso-comments.de/docs/reference/installation/#using-docker)
- [Isso GitHub](https://github.com/posativ/isso)
- [开源评论系统 isso 部署方法](https://kejiweixun.com/blog/how-to-deploy-isso-comment-system/)