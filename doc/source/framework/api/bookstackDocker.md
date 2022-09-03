# BookStack Docker

BookStack is a simple, self-hosted, easy-to-use platform for organising and storing information.

## Demo
[Demo site](https://demo.bookstackapp.com/login?email=admin@example.com&password=password)

## Docker
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

## Docker Compose
`bookstack.yml`

## Screenshots
![](https://www.bookstackapp.com/images/bookstack-hero-screenshot.webp)

![](https://www.bookstackapp.com/images/screenshots/image-manager.png)

![](https://www.bookstackapp.com/images/screenshots/books-view.png)

## References
- [BookStack](https://www.bookstackapp.com/)
- [BookStack GitHub](https://github.com/BookStackApp/BookStack)
- [BookStack Docker](https://hub.docker.com/r/solidnerd/bookstack)