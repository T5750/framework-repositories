# InsightFace

2D and 3D Face Analysis Project

## Quick Start
### Install
```sh
pip install -i https://pypi.tuna.tsinghua.edu.cn/simple insightface==0.7.3
```

### Quick Example
```
import cv2
import numpy as np
import insightface
from insightface.app import FaceAnalysis
from insightface.data import get_image as ins_get_image

app = FaceAnalysis(providers=['CUDAExecutionProvider', 'CPUExecutionProvider'])
app.prepare(ctx_id=0, det_size=(640, 640))
img = ins_get_image('t1')
faces = app.get(img)
rimg = app.draw_on(img, faces)
cv2.imwrite("./t1_output.jpg", rimg)
```

### Model Zoo

| Name          | Detection Model | Recognition Model    | Alignment    | Attributes | Model-Size | Link                                                         | Auto |
| ------------- | --------------- | -------------------- | ------------ | ---------- | ---------- | ------------------------------------------------------------ | ------------- |
| antelopev2    | SCRFD-10GF      | ResNet100@Glint360K  | 2d106 & 3d68 | Gender&Age | 407MB      | [link](https://drive.google.com/file/d/18wEUfMNohBJ4K3Ly5wpTejPfDzp-8fI8/view?usp=sharing) | N             |
| **buffalo_l** | SCRFD-10GF      | ResNet50@WebFace600K | 2d106 & 3d68 | Gender&Age | 326MB      | [link](https://drive.google.com/file/d/1qXsQJ8ZT42_xSmWIYy85IcidpiZudOCB/view?usp=sharing) | Y             |
| buffalo_m     | SCRFD-2.5GF     | ResNet50@WebFace600K | 2d106 & 3d68 | Gender&Age | 313MB      | [link](https://drive.google.com/file/d/1net68yNxF33NNV6WP7k56FS6V53tq-64/view?usp=sharing) | N             |
| buffalo_s     | SCRFD-500MF     | MBF@WebFace600K      | 2d106 & 3d68 | Gender&Age | 159MB      | [link](https://drive.google.com/file/d/1pKIusApEfoHKDjeBTXYB3yOQ0EtTonNE/view?usp=sharing) | N             |
| buffalo_sc    | SCRFD-500MF     | MBF@WebFace600K      | -            | -          | 16MB       | [link](https://drive.google.com/file/d/19I-MZdctYKmVf3nu5Da3HS6KH5LBfdzG/view?usp=sharing) | N             |

## Buffalo
人脸检测 → 关键点 → 人脸识别 → 属性分析
- `det_10g.onnx`: 人脸检测
- `2d106det.onnx`: 2D关键点
- `1k3d68.onnx`: 3D关键点
- `w600k_r50.onnx`: 人脸识别（核心）
- `genderage.onnx`: 属性分析

### Tips
- WebFace600K (600K identities) is the same as WebFace12M (12 million images). It is the first 3 parts of the 42M compressed package.
- WebFace42M datasets cannot access! CANNOT open https://www.face-benchmark.org/download.html

## Examples
```sh
# 人体检测
examples/person_detection/scrfd_person.py
# 人脸检测
examples/face_detection/detect.py
# 对齐2D人脸关键点
alignment/coordinate_reg/image_infer.py
# 性别年龄判断
attribute/gender_age/test.py
# 调用人脸识别模型
examples/face_recognition/insightface_app.py # 记得更换成要对比的两张人脸的路径
```

## Screenshots
![](https://raw.githubusercontent.com/nttstar/insightface-resources/refs/heads/master/images/facerecognitionfromvideo.PNG)

## Runtime Environment
- [InsightFace 0.7.3](https://pypi.org/project/insightface/)

## References
- [InsightFace](https://www.insightface.ai/zh)
- [InsightFace GitHub](https://github.com/deepinsight/insightface)
- [InsightFace Quick Start](https://github.com/deepinsight/insightface/tree/master/python-package)
- [InsightFace Datasets](https://github.com/deepinsight/insightface/tree/master/recognition/_datasets_)
- [What is WebFace600k dataset?](https://github.com/deepinsight/insightface/issues/2677)
- [prepare_webface42m](https://github.com/deepinsight/insightface/blob/master/recognition/arcface_torch/docs/prepare_webface42m.md)