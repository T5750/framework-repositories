# Gotify Docker

a simple server for sending and receiving messages

## Docker
```sh
docker run -p 80:80 -v /var/gotify/data:/app/data gotify/server
# or via GitHub registry
docker run -p 80:80 -v /var/gotify/data:/app/data ghcr.io/gotify/server
docker run -d --name=gotify -p 8080:80 ghcr.io/gotify/server
```
- [http://localhost:8080/](http://localhost:8080/)
- User: admin / admin
- [Swagger Documentation](http://localhost:8080/docs)

## Docker Compose
```
version: '3'
services:
  gotify:
    image: gotify/server
    ports:
      - 8080:80
    environment:
      GOTIFY_DEFAULTUSER_PASS: 'admin'
    volumes:
      - './gotify_data:/app/data'
    # to run gotify as a dedicated user:
    # sudo chown -R 1234:1234 ./gotify_data
    # user: "1234:1234"
```

## Push messages
Now you can simply use [curl](https://curl.haxx.se/), [HTTPie](https://httpie.org/) or any other installed http-client to push messages.
```sh
$ curl "https://push.example.de/message?token=<apptoken>" -F "title=my title" -F "message=my message" -F "priority=5"
$ http -f POST "https://push.example.de/message?token=<apptoken>" title="my title" message="my message" priority="5"
```
On Microsoft PowerShell, you could alternatively use the built-in `Invoke-RestMethod` or `Invoke-WebRequest` cmdlets.
```
PS> Invoke-RestMethod -Uri "https://push.example.de/message?token=<apptoken>" -Method POST -Body @{title="my title"; message="my message"; priority=5} # return is automatically parsed into a PowerShell object
PS> Invoke-WebRequest -Uri "https://push.example.de/message?token=<apptoken>" -Method POST -Body @{title="my title"; message="my message"; priority=5} # return is as raw response
```

## Runtime Environment
- [Go](https://golang.org/)

## Architecture
- A client is a device or application that can manage clients, messages and applications. However a client is not allowed to send messages.
- An application is a device or application that only can send messages.
- Users are only able to manage (view/edit/delete) clients and applications (includes messages sent by the app) that they've created.
- A message has the following attributes: content, title, creation date, application id and priority.

![](https://gotify.net/img/intro.png)

## Screenshots
![](https://gotify.net/img/ui.png)

![](https://gotify.net/img/androidv2.png)

## References
- [Gotify](https://gotify.net/)
- [Gotify GitHub](https://github.com/gotify)
- [Gotify Docker](https://gotify.net/docs/install)
- [Gotify Push messages](https://gotify.net/docs/pushmsg)
- [Gotify Push message examples](https://gotify.net/docs/more-pushmsg)