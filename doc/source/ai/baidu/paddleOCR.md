# PaddleOCR Docker

PaddleOCR旨在打造一套丰富、领先、且实用的OCR工具库，助力开发者训练出更好的模型，并应用落地。

## Demo
- [PP-OCR mobile](https://www.paddlepaddle.org.cn/hub/scene/ocr)
- [移动端demo](https://ai.baidu.com/easyedge/app/openSource?from=paddlelite)

## PaddleOCR 3.x
### 安装飞桨框架
#### 基于 Docker 安装飞桨
```sh
# 对于 cpu 用户:
docker run --name paddleocr -v $PWD:/paddle --shm-size=8G --network=host -it ccr-2vdh3abv-pub.cnc.bj.baidubce.com/paddlepaddle/paddle:3.0.0 /bin/bash
docker run -d -p 8080:8080 --name paddleocr --shm-size=8G -it ccr-2vdh3abv-pub.cnc.bj.baidubce.com/paddlepaddle/paddle:3.0.0 /bin/bash

# 对于 gpu 用户:
# GPU 版本，需显卡驱动程序版本 ≥450.80.02（Linux）或 ≥452.39（Windows）
docker run --gpus all --name paddleocr -v $PWD:/paddle --shm-size=8G --network=host -it ccr-2vdh3abv-pub.cnc.bj.baidubce.com/paddlepaddle/paddle:3.0.0-gpu-cuda11.8-cudnn8.9-trt8.6 /bin/bash

# GPU 版本，需显卡驱动程序版本 ≥545.23.06（Linux）或 ≥545.84（Windows）
docker run --gpus all --name paddleocr -v $PWD:/paddle  --shm-size=8G --network=host -it ccr-2vdh3abv-pub.cnc.bj.baidubce.com/paddlepaddle/paddle:3.0.0-gpu-cuda12.6-cudnn9.5-trt10.5 /bin/bash
```

#### 基于 pip 安装飞桨
```sh
# CPU 版本
python -m pip install paddlepaddle==3.0.0 -i https://www.paddlepaddle.org.cn/packages/stable/cpu/

# GPU 版本，需显卡驱动程序版本 ≥450.80.02（Linux）或 ≥452.39（Windows）
python -m pip install paddlepaddle-gpu==3.0.0 -i https://www.paddlepaddle.org.cn/packages/stable/cu118/

# GPU 版本，需显卡驱动程序版本 ≥550.54.14（Linux）或 ≥550.54.14（Windows）
 python -m pip install paddlepaddle-gpu==3.0.0 -i https://www.paddlepaddle.org.cn/packages/stable/cu126/
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

# Get the Qianfan API Key at first, and then run PP-ChatOCRv4 inference
paddleocr pp_chatocrv4_doc -i https://paddle-model-ecology.bj.bcebos.com/paddlex/imgs/demo_image/vehicle_certificate-1.png -k 驾驶室准乘人数 --qianfan_api_key your_api_key --use_doc_orientation_classify False --use_doc_unwarping False

# Get more information about "paddleocr ocr"
paddleocr ocr --help
```

## 推理部署
### 服务化部署
```sh
paddlex --install serving
paddlex --serve --pipeline {PaddleX 产线注册名或产线配置文件路径} [{其他命令行选项}]
paddlex --serve --pipeline OCR
python -m pip install "paddleocr[doc-parser]"
paddlex --serve --pipeline PP-StructureV3
```

### PaddleOCR 与 PaddleX 产线对应关系

| PaddleOCR 产线       | PaddleX 产线注册名   |
|----------------------|----------------------|
| 通用 OCR             | `OCR`                  |
| PP-StructureV3       | `PP-StructureV3`       |
| PP-ChatOCRv4         | `PP-ChatOCRv4-doc`     |
| 通用表格识别 v2      | `table_recognition_v2` |
| 公式识别             | `formula_recognition`  |
| 印章文本识别         | `seal_recognition`     |
| 文档图像预处理       | `doc_preprocessor`     |
| 文档理解             | `doc_understanding`    |
| PP-DocTranslation    | `PP-DocTranslation`    |

## PaddleOCR 2.x
### PP-OCR文本检测识别
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

#### 便捷使用
```sh
cd /path/to/ppocr_img
paddleocr --image_dir ./imgs/11.jpg --use_angle_cls true --use_gpu false
paddleocr --image_dir ./xxx.pdf --use_angle_cls true --use_gpu false --page_num 2
paddleocr --image_dir ./imgs/11.jpg --rec false
paddleocr --image_dir ./imgs_words/ch/word_1.jpg --det false
paddleocr --image_dir ./imgs_en/254.jpg --lang=en
```

### PP-Structure文档分析
#### 安装PaddleOCR whl包
```sh
pip3 install "paddleocr>=2.6.0.3"
# 安装 图像方向分类依赖包paddleclas（如不需要图像方向分类功能，可跳过）
pip3 install paddleclas>=2.4.3
```

#### 便捷使用
```sh
paddleocr --image_dir=./table/1.png --type=structure --image_orientation=true
paddleocr --image_dir=./table/1.png --type=structure
paddleocr --image_dir=./table/1.png --type=structure --table=false --ocr=false
paddleocr --image_dir=./table/table.jpg --type=structure --layout=false
```

### Docker
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

### Tests
[测试图片](https://paddleocr.bj.bcebos.com/dygraph_v2.1/ppocr_img.zip)
```sh
! pip install paddleocr --user
# 命令行使用
! wget https://raw.githubusercontent.com/PaddlePaddle/PaddleOCR/dygraph/doc/imgs/11.jpg
! paddleocr --image_dir 11.jpg --use_angle_cls true
```

## KIE
关键信息抽取 (Key Information Extraction, KIE)指从文本或者图像中，抽取出关键的信息
- 语义实体识别 (Semantic Entity Recognition, SER) ：可以完成对图像中的文本识别与分类
- 关系抽取 (Relation Extraction, RE)：可以完成对图象中的文本内容的关系提取，如判断问题对(pair)

KIE 的流程：OCR 检测（定位文本区域）→ OCR 识别（转文本内容）→ KIE 信息抽取

### 数据准备
数据下载：[XFUND](https://github.com/doc-analysis/XFUND)或者[FUNSD](https://guillaumejaume.github.io/FUNSD/)

### 开始训练
```sh
mkdir train_data
cd train_data
wget https://paddleocr.bj.bcebos.com/ppstructure/dataset/XFUND.tar && tar -xf XFUND.tar

# SER单卡训练
python3 tools/train.py -c configs/kie/vi_layoutxlm/ser_vi_layoutxlm_xfund_zh.yml
# SER多卡训练，通过--gpus参数指定卡号
python3 -m paddle.distributed.launch --gpus '0,1,2,3'  tools/train.py -c configs/kie/vi_layoutxlm/ser_vi_layoutxlm_xfund_zh.yml
# RE任务单卡训练
python3 tools/train.py -c configs/kie/vi_layoutxlm/re_vi_layoutxlm_xfund_zh.yml
```

### 模型评估与预测
#### 指标评估
```sh
python3 tools/eval.py -c configs/kie/vi_layoutxlm/ser_vi_layoutxlm_xfund_zh.yml -o Architecture.Backbone.checkpoints=./output/ser_vi_layoutxlm_xfund_zh/best_accuracy
```

#### 测试信息抽取结果
```sh
python3 tools/infer_kie_token_ser.py -c configs/kie/vi_layoutxlm/ser_vi_layoutxlm_xfund_zh.yml -o Architecture.Backbone.checkpoints=./output/ser_vi_layoutxlm_xfund_zh/best_accuracy Global.infer_img=./ppstructure/docs/kie/input/zh_val_42.jpg
python3 ./tools/infer_kie_token_ser_re.py -c configs/kie/vi_layoutxlm/re_vi_layoutxlm_xfund_zh.yml -o Architecture.Backbone.checkpoints=./pretrain_models/re_vi_layoutxlm_udml_xfund_zh/re_layoutxlm_xfund_zh_v4_udml/best_accuracy/ Global.infer_img=./train_data/XFUND/zh_val/val.json Global.infer_mode=False -c_ser configs/kie/vi_layoutxlm/ser_vi_layoutxlm_xfund_zh.yml -o_ser Architecture.Backbone.checkpoints=pretrain_models/ser_vi_layoutxlm_udml_xfund_zh/best_accuracy/
```

### 模型导出与预测
#### 模型导出
```sh
# SER任务转inference模型
python3 tools/export_model.py -c configs/kie/vi_layoutxlm/ser_vi_layoutxlm_xfund_zh.yml -o Architecture.Backbone.checkpoints=./output/ser_vi_layoutxlm_xfund_zh/best_accuracy Global.save_inference_dir=./inference/ser_vi_layoutxlm
# RE任务转inference模型
python3 tools/export_model.py -c configs/kie/vi_layoutxlm/re_vi_layoutxlm_xfund_zh.yml -o Architecture.Backbone.checkpoints=./output/re_vi_layoutxlm_xfund_zh/best_accuracy Global.save_inference_dir=./inference/re_vi_layoutxlm
```

#### 模型推理
```sh
cd ppstructure
python3 kie/predict_kie_token_ser.py \
  --kie_algorithm=LayoutXLM \
  --ser_model_dir=../inference/ser_vi_layoutxlm \
  --image_dir=./docs/kie/input/zh_val_42.jpg \
  --ser_dict_path=../train_data/XFUND/class_list_xfun.txt \
  --vis_font_path=../doc/fonts/simfang.ttf \
  --ocr_order_method="tb-yx"
```

## 特性
![](https://user-images.githubusercontent.com/25809855/186170862-b8f80f6c-fee7-4b26-badc-de9c327c76ce.png)

## Architecture
![](https://raw.githubusercontent.com/cuicheng01/PaddleX_doc_images/main/images/paddleocr/README/Arch_cn.jpg)

## References
- [PaddleOCR](https://www.paddleocr.ai/)
- [PaddleOCR GitHub](https://github.com/PaddlePaddle/PaddleOCR)
- [PaddleOCR 安装](https://www.paddleocr.ai/main/version3.x/installation.html)
- [PaddleOCR 服务化部署](https://www.paddleocr.ai/main/version3.x/deployment/serving.html)
- [PaddleOCR 与 PaddleX](https://www.paddleocr.ai/main/version3.x/paddleocr_and_paddlex.html)
- [PaddleOCR 快速开始](https://github.com/PaddlePaddle/PaddleOCR/blob/release/2.6/doc/doc_ch/quickstart.md)
- [PP-Structure 快速开始](https://github.com/PaddlePaddle/PaddleOCR/blob/release/2.6/ppstructure/docs/quickstart.md)
- [PaddleOCR Docker](https://hub.docker.com/r/paddlecloud/paddleocr)
- [PaddleOCR 关键信息抽取全流程指南](https://www.paddleocr.ai/v2.9.1/ppocr/model_train/kie.html)
- [PaddleOCR 关键信息抽取](https://www.paddleocr.ai/v2.9.1/ppstructure/model_train/train_kie.html)
- [基于VI-LayoutXLM的发票关键信息抽取](https://www.paddleocr.ai/v2.9.1/applications/%E5%8F%91%E7%A5%A8%E5%85%B3%E9%94%AE%E4%BF%A1%E6%81%AF%E6%8A%BD%E5%8F%96.html)
- [金融智能核验：扫描合同关键信息抽取](https://www.paddleocr.ai/v2.9.1/applications/%E6%89%AB%E6%8F%8F%E5%90%88%E5%90%8C%E5%85%B3%E9%94%AE%E4%BF%A1%E6%81%AF%E6%8F%90%E5%8F%96.html)