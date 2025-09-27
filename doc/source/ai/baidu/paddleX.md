# PaddleX

PaddlePaddle End-to-End Development Toolkit（『飞桨』深度学习全流程开发工具）

## 安装
### 安装 PaddlePaddle
```sh
# CPU版本
python -m pip install paddlepaddle==3.0.0 -i https://www.paddlepaddle.org.cn/packages/stable/cpu/
```

### 安装PaddleX
```sh
# 安装 PaddleX “基础功能”需要的全部依赖
pip install "paddlex[base]"
# 仅安装某项功能所需依赖
pip install "paddlex[ocr]"
```

### 基于Docker获取PaddleX
```sh
# 对于 CPU 用户
docker run --name paddlex -v $PWD:/paddle --shm-size=8g --network=host -it ccr-2vdh3abv-pub.cnc.bj.baidubce.com/paddlex/paddlex:paddlex3.1.2-paddlepaddle3.0.0-cpu /bin/bash

# 对于 GPU 用户
# GPU 版本，需显卡驱动程序版本 ≥450.80.02（Linux）或 ≥452.39（Windows）
docker run --gpus all --name paddlex -v $PWD:/paddle --shm-size=8g --network=host -it ccr-2vdh3abv-pub.cnc.bj.baidubce.com/paddlex/paddlex:paddlex3.1.2-paddlepaddle3.0.0-gpu-cuda11.8-cudnn8.9-trt8.6 /bin/bash

# GPU 版本，需显卡驱动程序版本 ≥545.23.06（Linux）或 ≥545.84（Windows）
docker run --gpus all --name paddlex -v $PWD:/paddle --shm-size=8g --network=host -it ccr-2vdh3abv-pub.cnc.bj.baidubce.com/paddlex/paddlex:paddlex3.1.2-paddlepaddle3.0.0-gpu-cuda12.6-cudnn9.5-trt10.5 /bin/bash
```

## 命令行使用
```sh
paddlex --pipeline [产线名称] --input [输入图片] --device [运行设备]
```
- `pipeline`：产线名称或产线配置文件
- `input`：待处理的输入文件（如图片）的本地路径、目录或 URL
- `device`: 使用的硬件设备及序号（例如`gpu:0`表示使用第 0 块 GPU），也可选择使用 NPU(`npu:0`)、 XPU(`xpu:0`)、CPU(`cpu`)等

### OCR相关产线
```sh
# 通用OCR
paddlex --pipeline OCR \
        --input https://paddle-model-ecology.bj.bcebos.com/paddlex/imgs/demo_image/general_ocr_002.png \
        --use_doc_orientation_classify False \
        --use_doc_unwarping False \
        --use_textline_orientation False \
        --save_path ./output \
        --device cpu
```

### 计算机视觉相关产线
```sh
# 通用图像分类
paddlex --pipeline image_classification --input https://paddle-model-ecology.bj.bcebos.com/paddlex/imgs/demo_image/general_image_classification_001.jpg --device cpu
```

### 时序分析相关产线
```sh
# 时序预测
paddlex --pipeline ts_forecast --input https://paddle-model-ecology.bj.bcebos.com/paddlex/ts/demo_ts/ts_fc.csv --device gpu:0 --save_path ./output
```

### 语音相关产线
```sh
# 多语种语音识别
paddlex --pipeline multilingual_speech_recognition \
        --input https://paddlespeech.bj.bcebos.com/PaddleAudio/zh.wav \
        --save_path ./output \
        --device gpu:0
```

### 视频相关产线
```sh
# 通用视频分类
paddlex --pipeline video_classification \
        --input https://paddle-model-ecology.bj.bcebos.com/paddlex/videos/demo_video/general_video_classification_001.mp4 \
        --topk 5 \
        --save_path ./output \
        --device gpu:0
```

## Python 脚本使用
```
from paddlex import create_pipeline

pipeline = create_pipeline(pipeline=[产线名称])
output = pipeline.predict([输入图片名称])
for res in output:
    res.print()
    res.save_to_img("./output/")
    res.save_to_json("./output/")
```

## 安装与快速体验
PaddleX提供了图像化开发界面、本地API、Restful-API三种开发模式。用户可根据自己的需求选择任意一种开始体验
- [PadldeX GUI开发模式](https://github.com/PaddlePaddle/PaddleX/blob/release/2.1/docs/quick_start_GUI.md)
- [PaddleX Python API开发模式](https://github.com/PaddlePaddle/PaddleX/blob/release/2.1/docs/quick_start_API.md)
- [PaddleX Restful API开发模式](https://github.com/PaddlePaddle/PaddleX/blob/release/2.1/docs/Resful_API/docs/readme.md)
- [快速产业部署](https://github.com/PaddlePaddle/PaddleX/tree/release/2.1#4-%E6%A8%A1%E5%9E%8B%E9%83%A8%E7%BD%B2)

### PaddleX GUI开发模式
下载地址：[https://www.paddlepaddle.org.cn/paddlex](https://www.paddlepaddle.org.cn/paddlex)

## 产业级应用示例
- 安防
    - [安全帽检测](https://github.com/PaddlePaddle/PaddleX/blob/release/2.1/examples/helmet_detection)
- 工业视觉
    - [表计读数](https://github.com/PaddlePaddle/PaddleX/blob/release/2.1/examples/meter_reader)  |  [钢筋计数](https://github.com/PaddlePaddle/PaddleX/blob/release/2.1/examples/rebar_count)  |  [视觉辅助定位抓取](https://github.com/PaddlePaddle/PaddleX/blob/release/2.1/examples/robot_grab)

## Architecture
![](https://raw.githubusercontent.com/cuicheng01/PaddleX_doc_images/main/images/PaddleX_ch.png)

## Screenshots
![](https://ai.bdstatic.com/file/AB67EF95F14A4512952CB365B0A482C1)

## References
- [PaddleX](https://www.paddlepaddle.org.cn/paddle/paddlex)
- [PaddleX GitHub](https://github.com/PaddlePaddle/PaddleX)
- [PaddleX 文档](https://paddlepaddle.github.io/PaddleX/latest/index.html)
- [PaddleX 安装](https://paddlepaddle.github.io/PaddleX/latest/installation/installation.html)