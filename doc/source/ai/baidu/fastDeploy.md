# FastDeploy

FastDeploy面向AI模型产业落地，帮助开发者简单几步即可完成AI模型在对应硬件上的部署，降低部署难度、压缩部署时间成本。支持40多个主流的AI模型在8大类常见硬件上的部署能力。FastDeploy借助了EasyEdge模型部署能力提升企业开发者模型部署的效率，开发者也可前往EasyEdge平台图形界面体验，或Github命令行，快速体验AI模型落地应用。

## Demo
- [体验飞桨开源模型](https://ai.baidu.com/easyedge/app/openSource)

## EasyEdge
EasyEdge是基于百度飞桨轻量化推理框架Paddle Lite研发的端与边缘AI服务平台，能够帮助深度学习开发者将自建模型快速部署到设备端。

### Windows
```sh
EasyEdge.exe
```

### 模型替换说明
`data\model`
- model: 模型网络结构文件，对应Paddle1.x的`__model__`，Paddle2.x的`model.pdmodel`
- params: 模型网络参数文件，对应Paddle1.x的`__params__`，Paddle2.x的`model.pdiparams`
- label_list.txt: label文件
- infer_cfg.json: 模型推理的预处理、后处理配置文件

### Screenshots
![](https://bce.bdstatic.com/doc/ai-doc/EASYEDGE/image_809866c.png)

![](https://bce.bdstatic.com/doc/ai-doc/EASYEDGE/image_2642bb9.png)

## References
- [FastDeploy](https://www.paddlepaddle.org.cn/fastdeploy)
- [FastDeploy GitHub](https://github.com/PaddlePaddle/FastDeploy)
- [EasyEdge Windows-SDK](https://ai.baidu.com/ai-doc/EASYEDGE/9l473iarh)