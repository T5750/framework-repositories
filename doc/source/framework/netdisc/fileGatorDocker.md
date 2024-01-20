# FileGator Docker

FileGator is a free, open-source, self-hosted web application for managing files and folders.

## Demo
[Live Preview](https://demo.filegator.io/)

## Docker
```sh
docker run -d --name filegator -p 8080:8080 filegator/filegator
```
- [http://localhost:8080/](http://localhost:8080/)
- User: admin / admin123

## Docker Compose
```
version: '3'
services:
    filegator:
        image: filegator/filegator
        ports:
            - '8080:8080'
```

## Runtime Environment
- [PHP](https://www.php.net/downloads)

## Screenshots
![](https://filegator.io/img/animated.gif)

## References
- [FileGator](https://filegator.io/)
- [FileGator GitHub](https://github.com/filegator/filegator)