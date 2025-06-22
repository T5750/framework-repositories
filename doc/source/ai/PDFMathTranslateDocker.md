# PDFMathTranslate Docker

基于 AI 完整保留排版的 PDF 文档全文双语翻译

## Demo
- [公共免费服务](https://pdf2zh.com/) 在线使用，无需安装 _(推荐)_
- [沉浸式翻译 - BabelDOC](https://app.immersivetranslate.com/babel-doc/) 每月免费 1000 页 _(推荐)_
- [在 HuggingFace 上托管的演示](https://huggingface.co/spaces/reycn/PDFMathTranslate-Docker)
- [在 ModelScope 上托管的演示](https://www.modelscope.cn/studios/AI-ModelScope/PDFMathTranslate) 无需安装

## Docker
```sh
docker run -d -p 7860:7860 byaidu/pdf2zh
```
[http://localhost:7860/](http://localhost:7860/)

## Runtime Environment
- [Python 3.12.x](https://www.python.org/downloads/)

## Screenshots
![](https://github.com/Byaidu/PDFMathTranslate/raw/main/docs/images/preview.gif)

## References
- [PDFMathTranslate](https://pdf2zh.com/)
- [PDFMathTranslate GitHub](https://github.com/Byaidu/PDFMathTranslate)
- [PDFMathTranslate API](https://github.com/Byaidu/PDFMathTranslate/blob/main/docs/APIS.md)