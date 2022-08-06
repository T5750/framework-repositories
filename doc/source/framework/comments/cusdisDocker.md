# Cusdis Docker

Lightweight, privacy-first, open-source comment system

## Docker
```sh
docker run \
  -d --name=cusdis \
  -e USERNAME=admin \
  -e PASSWORD=password \
  -e JWT_SECRET=ofcourseistillloveyou \
  -e DB_URL=file:$PWD/data/db.sqlite \
  -e NEXTAUTH_URL=http://cusdisIP:3000 \
  -p 3000:3000 \
  -v $PWD/data:/data \
  djyde/cusdis
```
- [http://localhost:3000/](http://localhost:3000/)
- [http://localhost:3000/doc](http://localhost:3000/doc)
- `vi /app/public/doc/index.html`
  + host
  + appId

### Env
`DB_TYPE`
- `sqlite` (default)
- `pgsql`
- `mysql`

### PostgreSQL
```sh
docker run \
  -d --name=cusdis \
  -e USERNAME=admin \
  -e PASSWORD=password \
  -e JWT_SECRET=ofcourseistillloveyou \
  -e DB_TYPE=pgsql \
  -e DB_URL=YOUR_PGSQL_URL \
  -e NEXTAUTH_URL=http://IP_ADDRESS_OR_DOMAIN \
  -p 3000:3000 \
  djyde/cusdis
```

## Docker Compose
```
version: "3.9"
services:
  cusdis:
    image: "djyde/cusdis"
    ports:
      - "3000:3000"
    environment:
      - USERNAME=admin
      - PASSWORD=password
      - JWT_SECRET=ofcourseistillloveyou
      - NEXTAUTH_URL=http://IP_ADDRESS_OR_DOMAIN
      - DB_TYPE=pgsql
      - DB_URL=postgresql://cusdis:password@pgsql:5432/cusdis
  pgsql:
    image: "postgres:13"
    volumes:
      - "./data:/var/lib/postgresql/data"
    environment:
      - POSTGRES_USER=cusdis
      - POSTGRES_DB=cusdis
      - POSTGRES_PASSWORD=password
```

## Screenshots
![](https://cusdis.com/images/intro-widget.png)

![](https://cusdis.com/images/intro-dashboard.png)

## References
- [Cusdis Docker](https://cusdis.com/doc#/self-host/docker)
- [Cusdis GitHub](https://github.com/djyde/cusdis)