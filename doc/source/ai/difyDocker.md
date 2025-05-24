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
# 1. Back up your customized docker-compose YAML file (optional)
cd dify/docker
cp docker-compose.yaml docker-compose.yaml.$(date +%s).bak
# 2. Get the latest code from the main branch
git checkout main
git pull origin main
# 3. Stop the service, Command, please execute in the docker directory
docker compose down
# 4. Back up data
tar -cvf volumes-$(date +%s).tgz volumes
# 5. Upgrade services
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

## 单独启动前端 Docker 容器
当单独开发后端时，可能只需要源码启动后端服务，而不需要本地构建前端代码并启动
```sh
docker run -it -p 3000:3000 -e CONSOLE_API_URL=http://127.0.0.1:5001 -e APP_API_URL=http://127.0.0.1:5001 langgenius/dify-web:latest
```

## MCP
MCP 为 LLM 与外部应用之间构建了双向通信通道，就像是 AI 的“USB-C”接口，帮助模型发现、理解并安全调用各种外部工具或 API

## Plugins
### hjlarry/database
可以用来连接现有数据库，并执行SQL语句和自然语言转SQL
Usage
```
mysql+pymysql://root:123456@localhost:3306/test
postgresql+psycopg2://postgres:123456@localhost:5432/test
sqlite:///test.db
mssql+pymssql://<username>:<password>@<freetds_name>/?charset=utf8
oracle+oracledb://user:pass@hostname:port[/dbname][?service_name=<service>[&key=value&key=value...]]
```
密码中有 `@` 怎么办？`@` 是URL中的保留字符，需要使用 `%40` 代替，例如 `123%40456`

### bowenliang123/md_exporter
导出 Markdown 为 DOCX, PPTX, XLSX, PDF, HTML, MD, CSV, JSON, XML, LaTex 文件, 并将代码块导出为各类脚本文件(Python, JS, Bash等)

## Tips
### 环境变量配置
`vi .env`
```
NGINX_CLIENT_MAX_BODY_SIZE=500M
UPLOAD_FILE_SIZE_LIMIT=500
TOP_K_MAX_VALUE=20
PIP_MIRROR_URL=https://pypi.tuna.tsinghua.edu.cn/simple
```

### 备份
需要备份数据库、配置的存储以及向量数据库数据，若为 docker compose 方式部署，可直接备份 `dify/docker/volumes` 目录下所有数据内容。

### dify-sandbox config error
```sh
cd volumes/sandbox/conf
wget https://github.com/langgenius/dify/blob/main/docker/volumes/sandbox/conf/config.yaml
```

### dify-sandbox ModuleNotFoundError
```sh
cd volumes/sandbox/dependencies
vi python-requirements.txt
```

### weaviate组件缺失问题
```sh
docker-compose up weaviate -d
```

## Runtime Environment
- [Python 3.12.x](https://www.python.org/downloads/)
- [TypeScript](https://www.typescriptlang.org/)

## Architecture
[下载架构图](https://assets.dify.ai/files/dify_llms_app_stack_cn.pdf)

![MCP](https://mmecoa.qpic.cn/mmecoa_png/ftnoqhiaHUy8f94noxxab794HsRlhWpyCT25WePXzy1LIvoXgCFYrkRnc6XKosV95GAcMOydMVr1kwibMiaiajbctQ/640?wx_fmt=png&tp=webp&wxfrom=10005&wx_lazy=1&wx_co=1)

## Screenshots
![](https://framerusercontent.com/images/WY0mSS5ACIDg7Vrq3NVKndOs5KM.png)

![](https://framerusercontent.com/images/cbqqxOjnHAZK0bXOSGWSwCZ4Lw.png)

![](https://framerusercontent.com/images/p3HloLvq35HTKP1Q3A3hSwyI.png)

## References
- [Dify](https://dify.ai/zh)
- [Dify GitHub](https://github.com/langgenius/dify)
- [Dify Docker Compose 部署](https://docs.dify.ai/zh-hans/getting-started/install-self-hosted/docker-compose)
- [Dify 单独启动前端 Docker 容器](https://docs.dify.ai/zh-hans/getting-started/install-self-hosted/start-the-frontend-docker-container)
- [Dify 环境变量说明](https://docs.dify.ai/zh-hans/getting-started/install-self-hosted/environments)
- [Dify Marketplace](https://marketplace.dify.ai/)
- [Dify-Sandbox GitHub](https://github.com/langgenius/dify-sandbox)
- [dify-sandbox:0.2.10](https://github.com/langgenius/dify/issues/15675)
- [weaviate组件缺失问题](https://github.com/langgenius/dify/issues/12872)
- [Dify MCP 插件指南：一键连接 Zapier，轻松调用 7000+ App 工具](https://mp.weixin.qq.com/s/CDhqmLO1JXSB__aUMqoGoQ)
- [hjlarry/database](https://marketplace.dify.ai/plugins/hjlarry/database)
- [hjlarry/database FAQ](https://github.com/hjlarry/dify-plugin-database/blob/main/FAQ.md)
- [bowenliang123/md_exporter](https://marketplace.dify.ai/plugins/bowenliang123/md_exporter)