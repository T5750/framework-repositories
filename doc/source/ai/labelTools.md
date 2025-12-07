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

## References
- [X-AnyLabeling GitHub](https://github.com/CVHub520/X-AnyLabeling)
- [X-AnyLabeling 用户手册](https://github.com/CVHub520/X-AnyLabeling/blob/main/docs/zh_cn/user_guide.md)
- [X-AnyLabeling 模型库](https://github.com/CVHub520/X-AnyLabeling/blob/main/docs/zh_cn/model_zoo.md)
- [LabelImg GitHub](https://github.com/HumanSignal/labelImg)