# Teable Docker

企业级高性能多维表格

## Demo
[立即体验](https://app.teable.cn/)

## Docker Compose
### 不包括 Redis
```
version: '3.9'

services:
  teable:
    image: registry.cn-shenzhen.aliyuncs.com/teable/teable:latest
    restart: always
    ports:
      - '3000:3000'
    volumes:
      - teable-data:/app/.assets:rw
    env_file:
      - .env
    environment:
      - NEXT_ENV_IMAGES_ALL_REMOTE=true
    networks:
      - teable
    depends_on:
      teable-db-migrate:
        condition: service_completed_successfully
    healthcheck:
      test: ['CMD', 'curl', '-f', 'http://localhost:3000/health']
      start_period: 5s
      interval: 5s
      timeout: 3s
      retries: 3

  teable-db:
    image: postgres:15.4
    restart: always
    ports:
      - '42345:5432'
    volumes:
      - teable-db:/var/lib/postgresql/data:rw
    environment:
      - POSTGRES_DB=${POSTGRES_DB}
      - POSTGRES_USER=${POSTGRES_USER}
      - POSTGRES_PASSWORD=${POSTGRES_PASSWORD}
    networks:
      - teable
    healthcheck:
      test: ['CMD-SHELL', "sh -c 'pg_isready -U ${POSTGRES_USER} -d ${POSTGRES_DB}'"]
      interval: 10s
      timeout: 3s
      retries: 3

  teable-db-migrate:
    image: registry.cn-shenzhen.aliyuncs.com/teable/teable-db-migrate:latest
    environment:
      - PRISMA_DATABASE_URL=postgresql://${POSTGRES_USER}:${POSTGRES_PASSWORD}@${POSTGRES_HOST}:${POSTGRES_PORT}/${POSTGRES_DB}
    networks:
      - teable
    depends_on:
      teable-db:
        condition: service_healthy

networks:
  teable:
    name: teable-network

volumes:
  teable-db: {}
  teable-data: {}
```
.env:
```
# 替换下面默认密码, 推荐使用 8 位以上的强密码。
POSTGRES_PASSWORD=replace_this_password
SECRET_KEY=replace_this_secret_key

# 请将下面替换为可公开访问的地址
PUBLIC_ORIGIN=http://127.0.0.1:3000

# ---------------------

# Postgres
POSTGRES_HOST=teable-db
POSTGRES_PORT=5432
POSTGRES_DB=teable
POSTGRES_USER=teable

# App
PRISMA_DATABASE_URL=postgresql://${POSTGRES_USER}:${POSTGRES_PASSWORD}@${POSTGRES_HOST}:${POSTGRES_PORT}/${POSTGRES_DB}
```
[http://localhost:3000/](http://localhost:3000/)

### 包括 Redis
```
version: '3.9'

services:
  teable:
    image: registry.cn-shenzhen.aliyuncs.com/teable/teable:latest
    restart: always
    ports:
      - '3000:3000'
    volumes:
      - teable-data:/app/.assets:rw
    env_file:
      - .env
    environment:
      - NEXT_ENV_IMAGES_ALL_REMOTE=true
    networks:
      - teable
    depends_on:
      teable-db-migrate:
        condition: service_completed_successfully
      teable-cache:
        condition: service_healthy
    healthcheck:
      test: ['CMD', 'curl', '-f', 'http://localhost:3000/health']
      start_period: 5s
      interval: 5s
      timeout: 3s
      retries: 3

  teable-db:
    image: registry.cn-shenzhen.aliyuncs.com/teable/postgres:15.4
    restart: always
    ports:
      - '42345:5432'
    volumes:
      - teable-db:/var/lib/postgresql/data:rw
    environment:
      - POSTGRES_DB=${POSTGRES_DB}
      - POSTGRES_USER=${POSTGRES_USER}
      - POSTGRES_PASSWORD=${POSTGRES_PASSWORD}
    networks:
      - teable
    healthcheck:
      test: ['CMD-SHELL', "sh -c 'pg_isready -U ${POSTGRES_USER} -d ${POSTGRES_DB}'"]
      interval: 10s
      timeout: 3s
      retries: 3

  teable-db-migrate:
    image: registry.cn-shenzhen.aliyuncs.com/teable/teable-db-migrate:latest
    environment:
      - PRISMA_DATABASE_URL=postgresql://${POSTGRES_USER}:${POSTGRES_PASSWORD}@${POSTGRES_HOST}:${POSTGRES_PORT}/${POSTGRES_DB}
    networks:
      - teable
    depends_on:
      teable-db:
        condition: service_healthy

  teable-cache:
    image: registry.cn-shenzhen.aliyuncs.com/teable/redis:7.2.4
    restart: always
    expose:
      - '6379'
    volumes:
      - teable-cache:/data:rw
    networks:
      - teable
    command: redis-server --appendonly yes --requirepass ${REDIS_PASSWORD}
    healthcheck:
      test: ['CMD', 'redis-cli', '--raw', 'incr', 'ping']
      interval: 10s
      timeout: 3s
      retries: 3

networks:
  teable:
    name: teable-network

volumes:
  teable-db: {}
  teable-data: {}
  teable-cache: {}
```
.env:
```
# 替换下面默认密码, 推荐使用 8 位以上的强密码。
POSTGRES_PASSWORD=replace_this_password
REDIS_PASSWORD=replace_this_password
SECRET_KEY=replace_this_secret_key

# 请将下面替换为可公开访问的地址
PUBLIC_ORIGIN=http://127.0.0.1:3000

# ---------------------

# Postgres
POSTGRES_HOST=teable-db
POSTGRES_PORT=5432
POSTGRES_DB=teable
POSTGRES_USER=teable

# App
PRISMA_DATABASE_URL=postgresql://${POSTGRES_USER}:${POSTGRES_PASSWORD}@${POSTGRES_HOST}:${POSTGRES_PORT}/${POSTGRES_DB}
BACKEND_CACHE_PROVIDER=redis
BACKEND_CACHE_REDIS_URI=redis://default:${REDIS_PASSWORD}@${REDIS_HOST}:${REDIS_PORT}/${REDIS_DB}
```

## Runtime Environment
- [TypeScript](https://www.typescriptlang.org/)

## Architecture
![](https://help.teable.cn/~gitbook/image?url=https%3A%2F%2F2932697955-files.gitbook.io%2F%7E%2Ffiles%2Fv0%2Fb%2Fgitbook-x-prod.appspot.com%2Fo%2Fspaces%252F3UAxYOALe4vISZRKICva%252Fuploads%252F8nsAIToscmFfr4rXVloy%252Fimg_v3_02dh_056f254e-37ea-4e8f-94ee-b9fa941be57g.png%3Falt%3Dmedia%26token%3D41a9e4fa-c6e8-4365-87c8-fa4701e00358&width=400&dpr=3&quality=100&sign=6317875f&sv=1)

![](https://help.teable.cn/~gitbook/image?url=https%3A%2F%2F2932697955-files.gitbook.io%2F%7E%2Ffiles%2Fv0%2Fb%2Fgitbook-x-prod.appspot.com%2Fo%2Fspaces%252F3UAxYOALe4vISZRKICva%252Fuploads%252Fz2BvTAuExzXa8vfX2t7I%252Fimg_v3_02dh_bfaed8d7-618f-4496-98e9-8f0e6d2c6edg.png%3Falt%3Dmedia%26token%3D620a04e3-bbee-4ec2-9443-95dc042ca776&width=400&dpr=3&quality=100&sign=87795fea&sv=1)

## Screenshots
![](https://framerusercontent.com/images/lAAbFOWOTkdl2ZZl0aVNPSDsc.png?scale-down-to=2048)

![](https://framerusercontent.com/images/AmLJvR4YVkLuuObTZJyaCPEhN3c.png?scale-down-to=1024)

## References
- [Teable](https://teable.cn/)
- [Teable GitHub](https://github.com/teableio/teable)
- [Teable Docker](https://help.teable.cn/si-you-hua-bu-shu/kai-shi-bu-shu)
- [Teable 技术架构](https://help.teable.cn/si-you-hua-bu-shu/ji-shu-jia-gou)