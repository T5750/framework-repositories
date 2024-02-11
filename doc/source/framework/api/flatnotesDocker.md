# flatnotes Docker

A self-hosted, database-less note taking web app that utilises a flat folder of markdown files for storage.

## Demo
- [Demo site](https://demo.flatnotes.io)
- [Demo](https://flatnotes.dujun.tk/)

## Docker
```sh
docker run -d \
  -e "PUID=1000" \
  -e "PGID=1000" \
  -e "FLATNOTES_AUTH_TYPE=password" \
  -e "FLATNOTES_USERNAME=user" \
  -e "FLATNOTES_PASSWORD=changeMe!" \
  -e "FLATNOTES_SECRET_KEY=aLongRandomSeriesOfCharacters" \
  -v "$(pwd)/data:/data" \
  -p "8080:8080" \
  dullage/flatnotes:latest
```
[http://localhost:8080/](http://localhost:8080/)

## Docker Compose
```
version: "3"

services:
  flatnotes:
    container_name: flatnotes
    image: dullage/flatnotes:latest
    environment:
      PUID: 1000
      PGID: 1000
      FLATNOTES_AUTH_TYPE: "password"
      FLATNOTES_USERNAME: "user"
      FLATNOTES_PASSWORD: "changeMe!"
      FLATNOTES_SECRET_KEY: "aLongRandomSeriesOfCharacters"
    volumes:
      - "./data:/data"
      # Optional. Allows you to save the search index in a different location: 
      # - "./index:/data/.flatnotes"
    ports:
      - "8080:8080"
    restart: unless-stopped
```

## Runtime Environment
- [Python 3.11.x](https://www.python.org/downloads/)
- [Vue.js](https://github.com/vuejs/vue)

## References
- [flatnotes GitHub](https://github.com/Dullage/flatnotes)