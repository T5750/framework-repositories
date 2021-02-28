# Keycloak Docker

## Docker
```
docker run -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin -p 8080:8080 jboss/keycloak
```

- [http://localhost:8080/](http://localhost:8080/)
- User: admin / admin

## Docker Compose
- `keycloak-h2.yml`
- `keycloak-postgres.yml`

## References
- [jboss/keycloak](https://hub.docker.com/r/jboss/keycloak)
- [Keycloak With Docker Compose](https://gruchalski.com/posts/2020-09-03-keycloak-with-docker-compose/)