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

## Screenshots
![](https://raw.githubusercontent.com/nttstar/insightface-resources/refs/heads/master/images/facerecognitionfromvideo.PNG)

## Runtime Environment
- [InsightFace 0.7.3](https://github.com/deepinsight/insightface)

## References
- [InsightFace](https://insightface.ai/)
- [InsightFace GitHub](https://github.com/deepinsight/insightface)
- [InsightFace Quick Start](https://github.com/deepinsight/insightface/tree/master/python-package)
- [InsightFace Datasets](https://github.com/deepinsight/insightface/tree/master/recognition/_datasets_)