# Langflow Docker

Langflow 是一款面向开发者的低代码工具，可以更轻松地构建强大的 AI 智能体和工作流程，它们可以使用任何 API、模型或数据库。

## pip
```sh
pip install langflow
python -m langflow run
```
[http://localhost:7860/](http://localhost:7860/)

## Docker
```sh
docker run -p 7860:7860 langflowai/langflow:latest
docker run -d --name langflow -p 7860:7860 langflowai/langflow
```

## Runtime Environment
- [Python 3.10.x](https://www.python.org/downloads/)
- [TypeScript](https://www.typescriptlang.org/)

## Screenshots
![](https://framerusercontent.com/images/7W9HZcs8jxmteW0ABHNBXqRLwsQ.svg)

## References
- [Langflow](https://langflow.org.cn/)
- [Langflow GitHub](https://github.com/langflow-ai/langflow)
- [Langflow 安装](https://docs.langflow.org.cn/get-started-installation)
- [Langflow Docker](https://docs.langflow.org.cn/deployment-docker)
- [Langflow 快速入门](https://docs.langflow.org.cn/get-started-quickstart)
- [Langflow Ollama](https://docs.langflow.org/bundles-ollama)