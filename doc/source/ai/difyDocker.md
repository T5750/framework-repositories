# Dify Docker

生成式 AI 应用创新引擎

开源的 LLM 应用开发平台。提供从 Agent 构建到 AI workflow 编排、RAG 检索、模型管理等能力，轻松构建和运营生成式 AI 原生应用。
比 LangChain 更易用。

## Docker Compose
```sh
git clone https://github.com/langgenius/dify.git
cd dify/docker
cp .env.example .env
docker-compose up -d
```

## 访问 Dify
- [http://localhost/install](http://localhost/install)
- [http://localhost](http://localhost)

## 更新 Dify
```sh
cd dify/docker
docker compose down
git pull origin main
docker compose pull
docker compose up -d
```

### 同步环境变量配置 (重要！)
如果 `.env.example` 文件有更新，请务必同步修改你本地的 `.env` 文件。

## 自定义配置
编辑 `.env` 文件中的环境变量值。然后重新启动 Dify：
```sh
docker compose down
docker compose up -d
```
完整的环境变量集合可以在 `docker/.env.example` 中找到。

## Runtime Environment
- [Python 3.12.x](https://www.python.org/downloads/)
- [TypeScript](https://www.typescriptlang.org/)

## Screenshots
![](https://framerusercontent.com/images/WY0mSS5ACIDg7Vrq3NVKndOs5KM.png)

![](https://framerusercontent.com/images/cbqqxOjnHAZK0bXOSGWSwCZ4Lw.png)

![](https://framerusercontent.com/images/p3HloLvq35HTKP1Q3A3hSwyI.png)

## References
- [Dify](https://dify.ai/zh)
- [Dify GitHub](https://github.com/langgenius/dify)
- [Dify Docker Compose 部署](https://docs.dify.ai/zh-hans/getting-started/install-self-hosted/docker-compose)