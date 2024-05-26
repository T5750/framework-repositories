# MaxKB Docker

MaxKB 是一款基于 LLM 大语言模型的知识库问答系统。MaxKB = Max Knowledge Base，旨在成为企业的最强大脑。

## Demo
[DataEase 小助手](https://dataease.io/docs/v2/)

## Docker
```sh
docker run -d --name=maxkb -p 8080:8080 -v ~/.maxkb:/var/lib/postgresql/data 1panel/maxkb
```
- [http://localhost:8080/](http://localhost:8080/)
- User: admin / MaxKB@123..

## Runtime Environment
- [Python 3.7.x](https://www.python.org/downloads/)
- [Vue.js](https://github.com/vuejs/vue)
- [Langchain](https://www.langchain.com/)

## References
- [MaxKB GitHub](https://github.com/1Panel-dev/MaxKB)