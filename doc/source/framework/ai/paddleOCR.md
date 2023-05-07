# PaddleOCR Docker

PaddleOCR旨在打造一套丰富、领先、且实用的OCR工具库，助力开发者训练出更好的模型，并应用落地。

## Demo
- [PP-OCR mobile](https://www.paddlepaddle.org.cn/hub/scene/ocr)
- [移动端demo](https://ai.baidu.com/easyedge/app/openSource?from=paddlelite)

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
- [PaddleOCR 快速开始](https://github.com/PaddlePaddle/PaddleOCR/blob/release/2.6/doc/doc_ch/quickstart.md)
- [PP-Structure 快速开始](https://github.com/PaddlePaddle/PaddleOCR/blob/release/2.6/ppstructure/docs/quickstart.md)
- [PaddleOCR Docker](https://hub.docker.com/r/paddlecloud/paddleocr)