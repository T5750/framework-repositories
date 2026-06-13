# HivisionIDPhotos Docker

HivisionIDPhoto 旨在开发一种实用、系统性的证件照智能制作算法。

## Demo
[HivisionIDPhoto：轻量级的AI证件照生成算法](https://www.modelscope.cn/studios/ModelBulider/HivisionIDPhoto/summary)

## Docker
启动 Gradio Demo 服务
```sh
docker run -d -p 7860:7860 linzeyi/hivision_idphotos
```
[http://127.0.0.1:7860/](http://127.0.0.1:7860/)

启动 API 后端服务
```sh
docker run -d -p 8080:8080 linzeyi/hivision_idphotos python3 deploy_api.py
```

## Runtime Environment
- [Python 3.10.x](https://www.python.org/downloads/)

## Screenshots
![](https://github.com/Zeyi-Lin/HivisionIDPhotos/raw/master/assets/demo.png)

## References
- [HivisionIDPhotos GitHub](https://github.com/Zeyi-Lin/HivisionIDPhotos)
- [HivisionIDPhotos API Docs](https://github.com/Zeyi-Lin/HivisionIDPhotos/blob/master/docs/api_CN.md)