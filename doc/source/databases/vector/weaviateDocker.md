# Weaviate Docker

The AI-native database for a new generation of software

## Docker
```sh
docker run -p 8080:8080 -p 50051:50051 cr.weaviate.io/semitechnologies/weaviate:1.30.2
docker run -d --name weaviate -p 8080:8080 -p 50051:50051 -e AUTHENTICATION_APIKEY_ENABLED=true -e PERSISTENCE_DATA_PATH=/var/lib/weaviate -e AUTHENTICATION_APIKEY_ALLOWED_KEYS=user-a-key -e AUTHENTICATION_APIKEY_USERS=user-a semitechnologies/weaviate:1.30.2
```

## Docker Compose
```
---
services:
  weaviate:
    command:
    - --host
    - 0.0.0.0
    - --port
    - '8080'
    - --scheme
    - http
    image: cr.weaviate.io/semitechnologies/weaviate:1.30.2
    ports:
    - 8080:8080
    - 50051:50051
    volumes:
    - weaviate_data:/var/lib/weaviate
    restart: on-failure:0
    environment:
      QUERY_DEFAULTS_LIMIT: 25
      PERSISTENCE_DATA_PATH: '/var/lib/weaviate'
      ENABLE_API_BASED_MODULES: 'true'
      CLUSTER_HOSTNAME: 'node1'
      AUTHENTICATION_ANONYMOUS_ACCESS_ENABLED: 'false'
      AUTHENTICATION_APIKEY_ENABLED: 'true'
      AUTHENTICATION_APIKEY_ALLOWED_KEYS: 'user-a-key,user-b-key'
      AUTHENTICATION_APIKEY_USERS: 'user-a,user-b'
      AUTHORIZATION_ENABLE_RBAC: 'true'
      AUTHORIZATION_RBAC_ROOT_USERS: 'user-a'
volumes:
  weaviate_data:
```

## Python
```sh
pip install -U weaviate-client
```

## Weaviate-UI
Weaviate-UI is a web client for interacting with the Weaviate.
```sh
docker run -d --name weaviate-ui -p 7777:7777 -e WEAVIATE_URL=http://localhost:8080 -e WEAVIATE_API_KEYS=user-a-key naaive/weaviate-ui
```
[http://localhost:7777](http://localhost:7777)

## Runtime Environment
- [Go](https://golang.org/)

## References
- [Weaviate](https://weaviate.io/)
- [Weaviate GitHub](https://github.com/weaviate/weaviate)
- [Weaviate Docker](https://weaviate.io/developers/weaviate/installation/docker-compose)
- [Weaviate Quickstart: locally hosted](https://weaviate.io/developers/weaviate/quickstart/local)
- [Weaviate-UI GitHub](https://github.com/naaive/weaviate-ui)