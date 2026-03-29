# GPT-Load Docker

高性能 AI 接口透明代理

## Docker
```sh
docker run -d --name gpt-load \
    -p 3001:3001 \
    -e AUTH_KEY=your-secure-key-here \
    -v "$(pwd)/data":/app/data \
    ghcr.io/tbphp/gpt-load:latest
docker run -d --name gpt-load -p 3001:3001 -e AUTH_KEY=your-secure-key-here ghcr.io/tbphp/gpt-load
```
[http://localhost:3001/](http://localhost:3001/)

## Docker Compose
1. 克隆项目
    ```sh
    git clone https://github.com/tbphp/gpt-load.git
    cd gpt-load
    ```
2. 配置环境
    ```sh
    # 复制环境配置文件
    cp .env.example .env
    
    # 编辑配置（可选）
    # vim .env
    
    # 主要配置项：
    # APP_PORT=3001
    # APP_SECRET=your-secret-key
    ```
3. 启动服务
    ```sh
    docker compose up -d
    ```

## Runtime Environment
- [Go](https://github.com/golang/go)
- [Vue.js](https://github.com/vuejs/vue)
- [TypeScript](https://www.typescriptlang.org/)

## Screenshots
![](https://raw.githubusercontent.com/tbphp/gpt-load/main/screenshot/dashboard.png)

![](https://github.com/tbphp/gpt-load/raw/main/screenshot/keys.png)

## References
- [GPT-Load](https://www.gpt-load.com/)
- [GPT-Load GitHub](https://github.com/tbphp/gpt-load)
- [GPT-Load Docker](https://www.gpt-load.com/docs/deployment/standalone)