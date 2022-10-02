# HedgeDoc Docker

The best platform to write and share markdown.

## Demo
[Try the demo](https://demo.hedgedoc.org/)

## Docker Compose
- [http://localhost:3000/](http://localhost:3000/)
```
version: '3'
services:
  database:
    image: postgres:13.4-alpine
    environment:
      - POSTGRES_USER=hedgedoc
      - POSTGRES_PASSWORD=password
      - POSTGRES_DB=hedgedoc
    volumes:
      - database:/var/lib/postgresql/data
    restart: always
  app:
    # Make sure to use the latest release from https://hedgedoc.org/latest-release
    image: quay.io/hedgedoc/hedgedoc:1.9.4
    environment:
      - CMD_DB_URL=postgres://hedgedoc:password@database:5432/hedgedoc
      - CMD_DOMAIN=localhost
      - CMD_URL_ADDPORT=true
    volumes:
      - uploads:/hedgedoc/public/uploads
    ports:
      - "3000:3000"
    restart: always
    depends_on:
      - database
volumes:
  database:
  uploads:
```

## Backup
```sh
docker-compose exec database pg_dump hedgedoc -U hedgedoc > backup.sql
```

## Restore
```sh
docker-compose up -d database
cat backup.sql | docker exec -i $(docker-compose ps -q database) psql -U hedgedoc
```

## Screenshots
![](https://hedgedoc.org/images/screenshot.png)

## References
- [HedgeDoc Docker](https://docs.hedgedoc.org/setup/docker/)
- [HedgeDoc GitHub](https://github.com/hedgedoc/hedgedoc)
- [HedgeDoc](https://hedgedoc.org/)
- [HedgeDoc Configuration](https://docs.hedgedoc.org/configuration/)