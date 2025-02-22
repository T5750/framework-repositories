# MaxKB Docker

基于大模型和 RAG 的知识库问答系统

MaxKB = Max Knowledge Base，是一款基于大语言模型和 RAG 的开源知识库问答系统，广泛应用于智能客服、企业内部知识库、学术研究与教育等场景。

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

## Screenshots
![](https://maxkb.cn/images/mk-overview.png)

## References
- [MaxKB](https://maxkb.cn/)
- [MaxKB GitHub](https://github.com/1Panel-dev/MaxKB)