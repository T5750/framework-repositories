# CodiMD Docker

Realtime collaborative markdown notes on all platforms.

## Docker Compose
- `codimd.yml`
- [http://localhost:3000/](http://localhost:3000/)
```
version: "3"
services:
  database:
    image: postgres:11.6-alpine
    environment:
      - POSTGRES_USER=codimd
      - POSTGRES_PASSWORD=change_password
      - POSTGRES_DB=codimd
    volumes:
      - "database-data:/var/lib/postgresql/data"
    restart: always
  codimd:
    image: hackmdio/hackmd:2.4.2
    environment:
      - CMD_DB_URL=postgres://codimd:change_password@database/codimd
      - CMD_USECDN=false
    depends_on:
      - database
    ports:
      - "3000:3000"
    volumes:
      - upload-data:/home/hackmd/app/public/uploads
    restart: always
volumes:
  database-data: {}
  upload-data: {}
```

## Screenshots
![](https://raw.githubusercontent.com/hackmdio/codimd/develop/public/screenshot.png)

## References
- [CodiMD Docker](https://hackmd.io/c/codimd-documentation/%2Fs%2Fcodimd-docker-deployment)
- [CodiMD GitHub](https://github.com/CodiMDci/CodiMD)