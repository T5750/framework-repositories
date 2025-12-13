# YOLO Python

A computer vision model architecture for detection, classification, segmentation, and more.

## 安装 Ultralytics
```sh
pip install -U ultralytics
```

## Quick Start
### CLI
```sh
# 语法
yolo TASK MODE ARGS
# 训练
yolo train data=coco8.yaml model=yolo11n.pt epochs=10 lr0=0.01
# 预测
yolo predict model=yolo11n.pt source='https://media.roboflow.com/inference/vehicles.png' imgsz=320
# 验证
yolo val model=yolo11n.pt data=coco8.yaml batch=1 imgsz=640
# 导出
yolo export model=yolo11n-cls.pt format=onnx imgsz=224,128
```

### Python
#### 基于jupyter/pytorch-notebook
```sh
from ultralytics import YOLO

# Create a new YOLO model from scratch
#model = YOLO("yolo11n.yaml")

# Load a pretrained YOLO model (recommended for training)
model = YOLO("yolo11n.pt")

# Train the model using the 'coco8.yaml' dataset for 3 epochs
results = model.train(data="coco8.yaml", epochs=3)

model = YOLO("runs/detect/train/weights/best.pt")
# Evaluate the model's performance on the validation set
results = model.val()

# Perform object detection on an image using the model
results = model("https://media.roboflow.com/inference/vehicles.png", save=True)

# Export the model to ONNX format
success = model.export(format="onnx")
```

## 训练
训练模式用于在自定义数据集上训练 YOLO 模型
```sh
from ultralytics import YOLO

model = YOLO("yolo11n.pt")  # pass any model type
results = model.train(epochs=5)
```

## 验证
Val 模式用于在 YOLO 模型训练完成后对其进行验证
```sh
from ultralytics import YOLO

# Load a YOLO model
model = YOLO("yolo11n.yaml")

# Train the model
model.train(data="coco8.yaml", epochs=5)

# Validate on training data
model.val()
```

## 预测
Predict mode 用于使用经过训练的 YOLO 模型对新图像或视频进行预测
```sh
import cv2
from PIL import Image

from ultralytics import YOLO

model = YOLO("model.pt")
# accepts all formats - image/dir/Path/URL/video/PIL/ndarray. 0 for webcam
results = model.predict(source="0")
results = model.predict(source="folder", show=True)  # Display preds. Accepts all YOLO predict arguments

# from PIL
im1 = Image.open("bus.jpg")
results = model.predict(source=im1, save=True)  # save plotted images

# from ndarray
im2 = cv2.imread("bus.jpg")
results = model.predict(source=im2, save=True, save_txt=True)  # save predictions as labels

# from list of PIL/ndarray
results = model.predict(source=[im1, im2])
```

## 导出
导出模式用于将 YOLO 模型导出为可用于部署的格式
```sh
from ultralytics import YOLO

model = YOLO("yolo11n.pt")
model.export(format="onnx", dynamic=True)
```

## 追踪
追踪模式用于使用 YOLO 模型实时追踪物体。此模式适用于监控系统或自动驾驶汽车等应用
```sh
from ultralytics import YOLO

# Load a model
model = YOLO("yolo11n.pt")  # load an official detection model
model = YOLO("yolo11n-seg.pt")  # load an official segmentation model
model = YOLO("path/to/best.pt")  # load a custom model

# Track with the model
results = model.track(source="https://youtu.be/LNwODJXcvt4", show=True)
results = model.track(source="https://youtu.be/LNwODJXcvt4", show=True, tracker="bytetrack.yaml")
```

## 基准测试
基准测试模式 用于分析 YOLO 各种导出格式的速度和准确性
```sh
from ultralytics.utils.benchmarks import benchmark

# Benchmark
benchmark(model="yolo11n.pt", data="coco8.yaml", imgsz=640, half=False, device=0)
```

## TensorBoard
### 用法
默认情况下，TensorBoard 日志记录处于禁用状态
```sh
# Enable TensorBoard logging
yolo settings tensorboard=True

# Disable TensorBoard logging
yolo settings tensorboard=False
```

## 性能指标
- **P (精确率)**：帮助识别并最大限度地减少假阳性
- **R (召回率)**：确保检测到所有相关对象
- **mAP (平均精度)**：提供整体性能快照，指导总体改进
- **IoU (交并比)**：帮助微调目标定位精度

## Docker
### 仅使用 CPU
```sh
docker run -it --ipc=host ultralytics/ultralytics
```

### 使用 GPU
```sh
# Set image name as a variable
t=ultralytics/ultralytics:latest

# Run with all GPUs
sudo docker run -it --ipc=host --runtime=nvidia --gpus all $t

# Run specifying which GPUs to use
sudo docker run -it --ipc=host --runtime=nvidia --gpus '"device=2,3"' $t
```

## Runtime Environment
- [Python 3.10.x](https://www.python.org/downloads/)

## Screenshots
![](https://yolov8.com/index_files/673f63f3b551697302c98211_cars-p-1080.png)

## References
- [YOLOv8](https://yolov8.com/)
- [YOLO11](https://yolo11.com/)
- [Ultralytics YOLO GitHub](https://github.com/ultralytics/ultralytics)
- [Ultralytics YOLO Python](https://docs.ultralytics.com/zh/usage/python/)
- [安装 Ultralytics](https://docs.ultralytics.com/zh/quickstart/)
- [Ultralytics YOLOv8](https://docs.ultralytics.com/zh/models/yolov8/)
- [Ultralytics YOLO11](https://docs.ultralytics.com/zh/models/yolo11/)
- [Ultralytics 命令行界面 (CLI)](https://docs.ultralytics.com/zh/usage/cli/)
- [YOLO11 与 TensorBoard 的集成](https://docs.ultralytics.com/zh/integrations/tensorboard/)
- [Ultralytics 性能指标深度解析](https://docs.ultralytics.com/zh/guides/yolo-performance-metrics/)
- [Ultralytics Docker 快速入门指南](https://docs.ultralytics.com/zh/guides/docker-quickstart/)