# Planka Docker

Elegant open source project tracking

## Demo
[Live Demo](https://plankanban.github.io/planka)

## Docker Compose
```sh
curl -L https://raw.githubusercontent.com/plankanban/planka/master/docker-compose.yml -o /opt/planka/docker-compose.yml
# Generate a secret key
openssl rand -hex 64
```
`vi docker-compose.yml`
```
- SECRET_KEY=notsecretkey
- DEFAULT_ADMIN_EMAIL=demo@demo.demo # Do not remove if you want to prevent this user from being edited/deleted
- DEFAULT_ADMIN_PASSWORD=demo
- DEFAULT_ADMIN_NAME=Demo Demo
- DEFAULT_ADMIN_USERNAME=demo
```
```sh
docker compose up -d
```
[http://localhost:3000/](http://localhost:3000/)

## Runtime Environment
- React, Redux, Redux-Saga, Redux-ORM, Semantic UI React, react-beautiful-dnd
- Sails.js, Knex.js
- [PostgreSQL](https://www.postgresql.org/download/)

## Screenshots
![](https://planka.app/cms-content/1/uploads/images/606395ea59a7c35fa8/demo28594da7dd7582c7f4c59bb263d1048e.gif)

## References
- [Planka](https://planka.app/)
- [Planka GitHub](https://github.com/plankanban/planka)
- [Planka Docker](https://docs.planka.cloud/docs/installation/docker/production_version)