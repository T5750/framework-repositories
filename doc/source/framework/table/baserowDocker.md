# Baserow Docker

Baserow is an open-source no-code database tool and an Airtable alternative.

## Docker
```sh
docker run \
  -d \
  --name baserow \
  -e BASEROW_PUBLIC_URL=http://localhost \
  -v baserow_data:/baserow/data \
  -p 80:80 \
  -p 443:443 \
  --restart unless-stopped \
  baserow/baserow:1.35.3
docker run -d --name baserow -e BASEROW_PUBLIC_URL=http://localhost -v baserow_data:/baserow/data -p 80:80 -p 443:443 baserow/baserow:1.35.3
```
[http://localhost:80/](http://localhost:80/)

## Docker Compose
```
services:
  baserow:
    container_name: baserow
    image: baserow/baserow:1.35.3
    environment:
      BASEROW_PUBLIC_URL: 'http://localhost'
    ports:
      - "80:80"
      - "443:443"
    volumes:
      - baserow_data:/baserow/data
volumes:
  baserow_data:
```

## Runtime Environment
- [Django](https://www.djangoproject.com/)
- [Vue.js](https://github.com/vuejs/vue)
- [PostgreSQL](https://www.postgresql.org/download/)

## Screenshots
![](https://baserow-backend-production20240528124524339000000001.s3.amazonaws.com/images/slider_database_survey.2e16d0ba.fill-2148x1520.webp)

![](https://baserow-backend-production20240528124524339000000001.s3.amazonaws.com/images/slider_application_manufacturin.2e16d0ba.fill-2148x1520.webp)

## References
- [Baserow](https://baserow.io/)
- [Baserow GitHub](https://github.com/bram2w/baserow)
- [Baserow Docker](https://baserow.io/docs/installation%2Finstall-with-docker)
- [Baserow Templates](https://baserow.io/templates)