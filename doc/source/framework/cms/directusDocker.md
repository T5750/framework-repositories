# Directus Docker

Directus is a real-time API and App dashboard for managing SQL database content.

## Docker
```sh
docker run -d --name directus -p 8055:8055 -e SECRET=replace-with-secure-random-value -e ADMIN_EMAIL=admin@example.com -e ADMIN_PASSWORD=d1r3ctu5 directus/directus
```
- [http://localhost:8055/](http://localhost:8055/)

## Docker Compose
```
version: "3"
services:
  directus:
    image: directus/directus:11.5.1
    ports:
      - 8055:8055
    volumes:
      - ./database:/directus/database
      - ./uploads:/directus/uploads
      - ./extensions:/directus/extensions
    environment:
      SECRET: "replace-with-secure-random-value"
      ADMIN_EMAIL: "admin@example.com"
      ADMIN_PASSWORD: "d1r3ctu5"
      DB_CLIENT: "sqlite3"
      DB_FILENAME: "/directus/database/data.db"
      WEBSOCKETS_ENABLED: "true"
```

## Runtime Environment
- [TypeScript](https://www.typescriptlang.org/)
- [Vue.js](https://github.com/vuejs/vue)

## Architecture
![](https://cdn.directus.io/docs/v9/getting-started/architecture/architecture-20220512/directus-architecture-20220512A.webp)

## Screenshots
![](https://cdn.directus.io/docs/v9/app-guide/content/content/content-20220415A/collection-page-20220415A.webp)

![](https://cdn.directus.io/docs/v9/app-guide/content/content/content-20220415A/item-page-20220215A.webp)

## References
- [Directus](https://directus.io/)
- [Directus GitHub](https://github.com/directus/directus)
- [Directus Self-Hosting Quickstart](https://docs.directus.io/self-hosted/quickstart.html)
- [Directus Docker Guide](https://docs.directus.io/self-hosted/docker-guide.html)
- [Directus Architecture](https://docs.directus.io/getting-started/architecture.html)