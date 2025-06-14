# n8n Docker

Flexible AI workflow automation for technical teams

## Docker
```sh
docker volume create n8n_data
docker run -it --rm --name n8n -p 5678:5678 -v n8n_data:/home/node/.n8n docker.n8n.io/n8nio/n8n
docker run -d --name n8n -p 5678:5678 -e GENERIC_TIMEZONE="Asia/Shanghai" -e TZ="Asia/Shanghai" -e N8N_DEFAULT_LOCALE=zh-CN ghcr.io/n8n-io/n8n
```
[http://localhost:5678/](http://localhost:5678/)

### Using with PostgreSQL
```sh
docker volume create n8n_data
docker run -it --rm \
 --name n8n \
 -p 5678:5678 \
 -e DB_TYPE=postgresdb \
 -e DB_POSTGRESDB_DATABASE=<POSTGRES_DATABASE> \
 -e DB_POSTGRESDB_HOST=<POSTGRES_HOST> \
 -e DB_POSTGRESDB_PORT=<POSTGRES_PORT> \
 -e DB_POSTGRESDB_USER=<POSTGRES_USER> \
 -e DB_POSTGRESDB_SCHEMA=<POSTGRES_SCHEMA> \
 -e DB_POSTGRESDB_PASSWORD=<POSTGRES_PASSWORD> \
 -v n8n_data:/home/node/.n8n \
 docker.n8n.io/n8nio/n8n
```

## Runtime Environment
- [TypeScript](https://www.typescriptlang.org/)
- [Vue.js](https://github.com/vuejs/vue)

## Screenshots
![](https://n8niostorageaccount.blob.core.windows.net/n8nio-strapi-blobs-prod/assets/Home_ITO_Ps_5a5aac3fda.webp)

![](https://n8niostorageaccount.blob.core.windows.net/n8nio-strapi-blobs-prod/assets/Home_Sec_O_Ps_1553ddb39b.webp)

![](https://n8niostorageaccount.blob.core.windows.net/n8nio-strapi-blobs-prod/assets/Home_Dev_O_Ps_43aa01a07b.webp)

![](https://n8niostorageaccount.blob.core.windows.net/n8nio-strapi-blobs-prod/assets/Home_Sales_d1992221c7.webp)

## References
- [n8n](https://n8n.io/)
- [n8n GitHub](https://github.com/n8n-io/n8n)
- [n8n Docker](https://docs.n8n.io/hosting/installation/docker/)
- [n8n Integrations](https://n8n.io/integrations)