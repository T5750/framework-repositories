# FastGPT Docker

让 AI 更懂您的知识

基于 LLM 大模型的开源 AI 知识库构建平台。提供了开箱即用的数据处理、模型调用、RAG 检索、可视化 AI 工作流编排等能力，帮助您轻松构建复杂的 AI 应用。

[开始使用](https://cloud.fastgpt.cn/)

## 部署架构图
![](https://doc.fastgpt.cn/imgs/sealos-fastgpt.webp)

- PgVector版本: 非常轻量，适合数据量在 5000 万以下
- Milvus版本: 对于亿级以上向量性能更优秀
- zilliz cloud版本: Milvus 的全托管服务，性能优于 Milvus 并提供 SLA

## Docker Compose
### 1. 下载 docker-compose.yml
```sh
mkdir fastgpt
cd fastgpt
curl -O https://raw.githubusercontent.com/labring/FastGPT/main/projects/app/data/config.json

# pgvector 版本(测试推荐，简单快捷)
curl -o docker-compose.yml https://raw.githubusercontent.com/labring/FastGPT/main/files/docker/docker-compose-pgvector.yml
# milvus 版本
# curl -o docker-compose.yml https://raw.githubusercontent.com/labring/FastGPT/main/files/docker/docker-compose-milvus.yml
# zilliz 版本
# curl -o docker-compose.yml https://raw.githubusercontent.com/labring/FastGPT/main/files/docker/docker-compose-zilliz.yml
```

### 2. 修改环境变量
```
FE_DOMAIN=你的前端你访问地址,例如 http://192.168.0.1:3000;https://cloud.fastgpt.cn
```

### 3. 启动容器
```sh
# 启动容器
docker-compose up -d
# 等待10s，OneAPI第一次总是要重启几次才能连上Mysql
sleep 10
# 重启一次oneapi(由于OneAPI的默认Key有点问题，不重启的话会提示找不到渠道，临时手动重启一次解决，等待作者修复)
docker restart oneapi
```

### 4. 打开 OneAPI 添加模型
可以通过`ip:3001`访问OneAPI，默认账号为`root`密码为`123456`。[在OneApi中添加合适的AI模型渠道](https://doc.fastgpt.cn/docs/development/modelconfig/one-api/)

### 5. 访问 FastGPT
目前可以通过 `ip:3000` 直接访问(注意防火墙)。登录用户名为 `root`，密码为`docker-compose.yml`环境变量里设置的 `DEFAULT_ROOT_PSW`

### 6. 配置模型
[点击查看模型配置教程](https://doc.fastgpt.cn/docs/development/modelconfig/intro/)

## OpenAPI 接口文档
FastGPT 的 API Key 有 2 类，一类是全局通用的 key (无法直接调用应用对话)；一类是携带了 AppId 也就是有应用标记的 key (可直接调用应用对话)

发起应用对话示例
```sh
curl --location --request POST 'http://localhost:3000/api/v1/chat/completions' \
--header 'Authorization: Bearer fastgpt-xxxxxx' \
--header 'Content-Type: application/json' \
--data-raw '{
    "chatId": "111",
    "stream": false,
    "detail": false,
    "messages": [
        {
            "content": "导演是谁",
            "role": "user"
        }
    ]
}'
```

## Runtime Environment
- [Python 3.10.x](https://www.python.org/downloads/)
- [TypeScript](https://www.typescriptlang.org/)

## 知识库核心流程图
![](https://doc.fastgpt.cn/imgs/functional-arch.webp)

## Screenshots
![](https://fastgpt.cn/images/ability/zh/ai_assiatant.png)

![](https://fastgpt.cn/images/ability/zh/dataset_import.png)

![](https://fastgpt.cn/images/ability/zh/advanced_settings.png)

![](https://fastgpt.cn/images/ability/zh/openapi.png)

## References
- [FastGPT](https://fastgpt.cn/zh)
- [FastGPT GitHub](https://github.com/labring/FastGPT)
- [FastGPT Docker Compose 快速部署](https://doc.fastgpt.cn/docs/development/docker/)
- [FastGPT Api Key 使用与鉴权](https://doc.fastgpt.cn/docs/development/openapi/auth/)