# VisualDL Docker

飞桨可视化分析工具，以丰富的图表呈现训练参数变化趋势、数据样本、模型结构、PR曲线、ROC曲线、高维数据分布等。帮助用户清晰直观地理解深度学习模型训练过程及模型结构，进而实现高效的模型调优。

## Demo
[Online Demo](https://www.paddlepaddle.org.cn/paddle/visualdl/demo)

## pip
```sh
python -m pip install visualdl -i https://mirror.baidu.com/pypi/simple
visualdl --logdir <dir_1, dir_2, ... , dir_n> --model <model_file> --host <host> --port <port> --cache-timeout <cache_timeout> --language <language> --public-path <public_path> --api-only --component_tabs <tab_name1, tab_name2, ...>
visualdl --logdir ./log
```
[http://localhost:8040/](http://localhost:8040/)

## Docker
```sh
docker compose up
```

## Screenshots
![](https://ai.bdstatic.com/file/D2BCCB4EC1A84C49AE705E5E205E3288)

![](https://ai.bdstatic.com/file/8B3E07BC9CA04241A3B435E743802F36)

![](https://ai.bdstatic.com/file/1013BD681B5548A5B866AFE25444A471)

![](https://ai.bdstatic.com/file/2571D4131B884010983E1DF3A3F69AFF)

![](https://ai.bdstatic.com/file/DF85C2F7F8BD4A2E8CECF557392898D0)

## References
- [VisualDL](https://www.paddlepaddle.org.cn/paddle/visualdl)
- [VisualDL GitHub](https://github.com/PaddlePaddle/VisualDL)
- [VisualDL 2.0.0 文档](https://visualdl.readthedocs.io/zh/latest/README_CN.html)