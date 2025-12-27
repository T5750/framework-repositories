# Label Tools

## X-AnyLabeling
X-AnyLabeling 是一款基于AI推理引擎和丰富功能特性于一体的强大辅助标注工具

### 特性
![](https://resouces.modelscope.cn/proxy-image/921d94da5a927f8562e62161513792e8750d0ad522269bcf93aab12fd2cae644.png)

### 安装
#### GUI 安装包
下载链接: [GitHub Releases](https://github.com/CVHub520/X-AnyLabeling/releases)

### 配置
```
#Linux
cd ~/.anylabelingrc
#Windows
cd C:\\Users\\xxx\\.anylabelingrc
vi .anylabelingrc
model_hub: modelscope
# 下载的模型默认保存位置
xanylabeling_data
```

### 自定义模型
- [ModelScope 官方下载链接](https://www.modelscope.cn/collections/X-AnyLabeling-7b0e1798bcda43)
- [yolo11s.yaml](https://github.com/CVHub520/X-AnyLabeling/blob/main/anylabeling/configs/auto_labeling/yolo11s.yaml)
- [yolo11s.onnx](https://github.com/CVHub520/X-AnyLabeling/releases/download/v2.4.4/yolo11s.onnx)

```
vi yolo11s.yaml
model_path: yolo11s.onnx
```

### Runtime Environment
- [Python 3.10.x](https://www.python.org/downloads/)

## LabelImg
LabelImg is a graphical image annotation tool.

### Installation
```sh
pip3 install labelImg
labelImg
labelImg [IMAGE_PATH] [PRE-DEFINED CLASS FILE]
```

### Tips
注：Python 3.9正常，Python 3.12闪退

### Runtime Environment
- [Python 3.x](https://www.python.org/downloads/)

## Labelme
Create your image dataset for vision AI.

### Installation
[Download app](https://labelme.io/downloads)

### Runtime Environment
- [Python 3.9.x](https://www.python.org/downloads/)

## CVAT
Computer Vision Annotation Tool (CVAT)：提供多种标注格式和可定制的工作流程，使其适用于复杂的项目。

### Runtime Environment
- [Python 3.10.x](https://www.python.org/downloads/)
- [TypeScript](https://www.typescriptlang.org/)

## VIA
VGG Image Annotator (VIA)：基于网页的轻量级注释工具

### Version 2
image annotator
- [via.html](https://www.robots.ox.ac.uk/~vgg/software/via/via.html)

### Version 3
audio and video annotator
- [via_image_annotator.html](https://www.robots.ox.ac.uk/~vgg/software/via/app/via_image_annotator.html)
- [via_video_annotator.html](https://www.robots.ox.ac.uk/~vgg/software/via/app/via_video_annotator.html)
- [via_audio_annotator.html](https://www.robots.ox.ac.uk/~vgg/software/via/app/via_audio_annotator.html)
- [via_subtitle_annotator.html](https://www.robots.ox.ac.uk/~vgg/software/via/app/via_subtitle_annotator.html)

## PPOCRLabel
PPOCRLabelv3是一款适用于OCR领域的半自动化图形标注工具

### 安装
```sh
pip install paddlepaddle -i https://www.paddlepaddle.org.cn/packages/stable/cpu/
pip install PPOCRLabel
```

### 运行
```sh
PPOCRLabel --lang ch  # 启动【普通模式】，用于打【检测+识别】场景的标签
PPOCRLabel --lang ch --kie True  # 启动 【KIE 模式】，用于打【检测+识别+关键字提取】场景的标签
```

### Runtime Environment
- [Python 3.9.x](https://www.python.org/downloads/)
- [PaddlePaddle 3.x](https://pypi.org/project/paddlepaddle/)
- [PaddleOCR 3.x](https://pypi.org/project/paddleocr/)
- [PPOCRLabel 3.x](https://pypi.org/project/PPOCRLabel/)

## References
- [X-AnyLabeling GitHub](https://github.com/CVHub520/X-AnyLabeling)
- [X-AnyLabeling 用户手册](https://github.com/CVHub520/X-AnyLabeling/blob/main/docs/zh_cn/user_guide.md)
- [X-AnyLabeling 模型库](https://github.com/CVHub520/X-AnyLabeling/blob/main/docs/zh_cn/model_zoo.md)
- [LabelImg GitHub](https://github.com/HumanSignal/labelImg)
- [Labelme](https://labelme.io/)
- [Labelme GitHub](https://github.com/wkentaro/labelme)
- [CVAT](https://www.cvat.ai/)
- [CVAT GitHub](https://github.com/cvat-ai/cvat)
- [CVAT Self-hosted Installation Guide](https://docs.cvat.ai/docs/administration/community/basics/installation/)
- [CVAT API](https://docs.cvat.ai/docs/api_sdk/api/)
- [VIA](https://www.robots.ox.ac.uk/~vgg/software/via/)
- [VIA GitHub](https://github.com/ox-vgg/via)
- [PPOCRLabel GitHub](https://github.com/PFCCLab/PPOCRLabel)