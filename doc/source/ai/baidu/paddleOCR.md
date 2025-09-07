# PaddleOCR Docker

PaddleOCR旨在打造一套丰富、领先、且实用的OCR工具库，助力开发者训练出更好的模型，并应用落地。

## Demo
- [PP-OCR mobile](https://www.paddlepaddle.org.cn/hub/scene/ocr)
- [移动端demo](https://ai.baidu.com/easyedge/app/openSource?from=paddlelite)

## PaddleOCR 3.x
### 安装飞桨框架
```sh
# 对于 cpu 用户:
docker run --name paddleocr -v $PWD:/paddle --shm-size=8G --network=host -it ccr-2vdh3abv-pub.cnc.bj.baidubce.com/paddlepaddle/paddle:3.0.0 /bin/bash

# 对于 gpu 用户:
# GPU 版本，需显卡驱动程序版本 ≥450.80.02（Linux）或 ≥452.39（Windows）
docker run --gpus all --name paddleocr -v $PWD:/paddle --shm-size=8G --network=host -it ccr-2vdh3abv-pub.cnc.bj.baidubce.com/paddlepaddle/paddle:3.0.0-gpu-cuda11.8-cudnn8.9-trt8.6 /bin/bash

# GPU 版本，需显卡驱动程序版本 ≥545.23.06（Linux）或 ≥545.84（Windows）
docker run --gpus all --name paddleocr -v $PWD:/paddle  --shm-size=8G --network=host -it ccr-2vdh3abv-pub.cnc.bj.baidubce.com/paddlepaddle/paddle:3.0.0-gpu-cuda12.6-cudnn9.5-trt10.5 /bin/bash
```
安装完成后，使用以下命令可以验证 PaddlePaddle 是否安装成功：
```sh
python -c "import paddle; print(paddle.__version__)"
```

### 安装 PaddleOCR
```sh
# 只希望使用基础文字识别功能（返回文字位置坐标和文本内容）
python -m pip install paddleocr
# 希望使用文档解析、文档理解、文档翻译、关键信息抽取等全部功能
# python -m pip install "paddleocr[all]"
```

### Run inference by CLI
```sh
# Run PP-OCRv5 inference
paddleocr ocr -i https://paddle-model-ecology.bj.bcebos.com/paddlex/imgs/demo_image/general_ocr_002.png --use_doc_orientation_classify False --use_doc_unwarping False --use_textline_orientation False

# Run PP-StructureV3 inference
paddleocr pp_structurev3 -i https://paddle-model-ecology.bj.bcebos.com/paddlex/imgs/demo_image/pp_structure_v3_demo.png --use_doc_orientation_classify False --use_doc_unwarping False
```

## PP-OCR文本检测识别
### 安装
#### 安装PaddlePaddle
```sh
python3 -m pip install paddlepaddle-gpu -i https://mirror.baidu.com/pypi/simple
```
```sh
python3 -m pip install paddlepaddle -i https://mirror.baidu.com/pypi/simple
```

#### 安装PaddleOCR whl包
```sh
pip install "paddleocr>=2.6.0"
```

### 便捷使用
```sh
cd /path/to/ppocr_img
paddleocr --image_dir ./imgs/11.jpg --use_angle_cls true --use_gpu false
paddleocr --image_dir ./xxx.pdf --use_angle_cls true --use_gpu false --page_num 2
paddleocr --image_dir ./imgs/11.jpg --rec false
paddleocr --image_dir ./imgs_words/ch/word_1.jpg --det false
paddleocr --image_dir ./imgs_en/254.jpg --lang=en
```

## PP-Structure文档分析
### 安装PaddleOCR whl包
```sh
pip3 install "paddleocr>=2.6.0.3"
# 安装 图像方向分类依赖包paddleclas（如不需要图像方向分类功能，可跳过）
pip3 install paddleclas>=2.4.3
```

### 便捷使用
```sh
paddleocr --image_dir=./table/1.png --type=structure --image_orientation=true
paddleocr --image_dir=./table/1.png --type=structure
paddleocr --image_dir=./table/1.png --type=structure --table=false --ocr=false
paddleocr --image_dir=./table/table.jpg --type=structure --layout=false
```

## Docker
使用CPU版本的Docker镜像

以 jupyter notebook 模式创建容器
```sh
docker run --name ppocr -v $PWD:/mnt -p 8888:8888 --shm-size=32g paddlecloud/paddleocr:2.6-cpu-latest
```
以 bash 模式创建容器
```sh
docker run --name ppocr -v $PWD:/mnt -p 8888:8888 -it --shm-size=32g paddlecloud/paddleocr:2.6-cpu-latest /bin/bash
```
使用GPU版本的Docker镜像

以 jupyter 模式创建容器
```sh
docker run --name ppocr --runtime=nvidia -v $PWD:/mnt -p 8888:8888 --shm-size=32g paddlecloud/paddleocr:2.6-gpu-cuda10.2-cudnn7-latest
```
以 bash 模式创建容器
```sh
docker run --name ppocr --runtime=nvidia -v $PWD:/mnt -p 8888:8888 -it --shm-size=32g paddlecloud/paddleocr:2.6-gpu-cuda10.2-cudnn7-latest /bin/bash
```

## Tests
```sh
! pip install paddleocr --user
# 命令行使用
! wget https://raw.githubusercontent.com/PaddlePaddle/PaddleOCR/dygraph/doc/imgs/11.jpg
! paddleocr --image_dir 11.jpg --use_angle_cls true
```

## 特性
![](https://user-images.githubusercontent.com/25809855/186170862-b8f80f6c-fee7-4b26-badc-de9c327c76ce.png)

## References
- [PaddleOCR GitHub](https://github.com/PaddlePaddle/PaddleOCR)
- [PaddleOCR 安装](https://www.paddleocr.ai/main/version3.x/installation.html)
- [PaddleOCR 快速开始](https://github.com/PaddlePaddle/PaddleOCR/blob/release/2.6/doc/doc_ch/quickstart.md)
- [PP-Structure 快速开始](https://github.com/PaddlePaddle/PaddleOCR/blob/release/2.6/ppstructure/docs/quickstart.md)
- [PaddleOCR Docker](https://hub.docker.com/r/paddlecloud/paddleocr)