# Prompt Optimizer Docker

一款提示词优化器，助力于编写高质量的提示词

## Docker
```sh
# 运行容器（默认配置）
docker run -d -p 80:80 --restart unless-stopped --name prompt-optimizer linshen/prompt-optimizer

# 运行容器（配置API密钥和访问密码）
docker run -d -p 80:80 \
  -e VITE_OPENAI_API_KEY=your_key \
  -e ACCESS_USERNAME=your_username \  # 可选，默认为"admin"
  -e ACCESS_PASSWORD=your_password \  # 设置访问密码
  --restart unless-stopped \
  --name prompt-optimizer \
  linshen/prompt-optimizer
```
[http://localhost:80/](http://localhost:80/)

## Docker Compose
```
services:
  prompt-optimizer:
    image: linshen/prompt-optimizer:latest
    container_name: prompt-optimizer
    restart: unless-stopped
    ports:
      - "8081:80"  # 修改端口映射
    environment:
      - VITE_OPENAI_API_KEY=your_key_here  # 直接在配置中设置密钥
```

## Ollama
API地址: `http://localhost:11434/v1`

## Runtime Environment
- [TypeScript](https://www.typescriptlang.org/)
- [Vue.js](https://github.com/vuejs/vue)

## References
- [Prompt Optimizer](https://prompt.always200.com/)
- [Prompt Optimizer GitHub](https://github.com/linshenkx/prompt-optimizer)