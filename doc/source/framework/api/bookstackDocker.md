# BookStack Docker

BookStack is a simple, self-hosted, easy-to-use platform for organising and storing information.

## Demo
[Demo site](https://demo.bookstackapp.com/login?email=admin@example.com&password=password)

## Docker
### solidnerd/bookstack
```sh
docker run -d \
-e DB_HOST=mysqlIP:3306 \
-e DB_DATABASE=bookstack \
-e DB_USERNAME=root \
-e DB_PASSWORD=123456 \
-e APP_URL=http://example.com \
-p 8080:8080 \
--name=bookstack \
 solidnerd/bookstack
```
- [http://localhost:8080/](http://localhost:8080/)
- User: admin@admin.com / password
- API Documentation: [http://localhost:8080/api/docs](http://localhost:8080/api/docs)

### linuxserver/bookstack
```sh
docker run -d \
  --name=bookstack \
  -e APP_URL= \
  -e DB_HOST=<yourdbhost> \
  -e DB_USER=<yourdbuser> \
  -e DB_PASS=<yourdbpass> \
  -e DB_DATABASE=bookstackapp \
  -p 6875:80 \
  -v /path/to/data:/config \
  --restart unless-stopped \
  lscr.io/linuxserver/bookstack
```

## Docker Compose
### solidnerd/bookstack
`bookstack.yml`

## Screenshots
![](https://www.bookstackapp.com/images/bookstack-hero-screenshot.webp)

![](https://www.bookstackapp.com/images/screenshots/image-manager.png)

![](https://www.bookstackapp.com/images/screenshots/books-view.png)

## References
- [BookStack](https://www.bookstackapp.com/)
- [BookStack GitHub](https://github.com/BookStackApp/BookStack)
- [solidnerd/bookstack Docker](https://hub.docker.com/r/solidnerd/bookstack)
- [linuxserver/bookstack Docker](https://hub.docker.com/r/linuxserver/bookstack)