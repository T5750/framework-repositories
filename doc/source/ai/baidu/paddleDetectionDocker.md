# PaddleDetection Docker

一个基于PaddlePaddle的目标检测端到端开发套件，在提供丰富的模型组件和测试基准的同时，注重端到端的产业落地应用，通过打造产业级特色模型|工具、建设产业应用范例等手段，帮助开发者实现数据准备、模型选型、模型训练、模型部署的全流程打通，快速进行落地应用。

## Demo
[模型在线体验](https://www.paddlepaddle.org.cn/models)

## pip
```sh
git clone https://github.com/PaddlePaddle/PaddleDetection.git
cd PaddleDetection
pip install -r requirements.txt
python setup.py install
```
```sh
pip install pycocotools -i https://pypi.tuna.tsinghua.edu.cn/simple
pip install git+https://github.com/philferriere/cocoapi.git#subdirectory=PythonAPI
```

## Docker
```sh
docker run --name paddledetection -v $PWD:/mnt -p 8888:8888 -it paddlecloud/paddledetection:2.4-cpu-latest /bin/bash
docker run --name paddledetection -v $PWD:/mnt -p 8888:8888 -p 8040:8040 -it paddlecloud/paddledetection:2.4-cpu-latest /bin/bash
jupyter-lab --ip=0.0.0.0 --allow-root
```
[http://localhost:8888/](http://localhost:8888/)

## Tests
```sh
# 用PP-YOLO算法在COCO数据集上预训练模型预测一张图片
python tools/infer.py -c configs/ppyolo/ppyolo_r50vd_dcn_1x_coco.yml -o use_gpu=false weights=https://paddledet.bj.bcebos.com/models/ppyolo_r50vd_dcn_1x_coco.pdparams --infer_img=demo/000000014439.jpg
```

## Quick started
### 训练
```sh
# 边训练边测试 CPU需要约1小时(use_gpu=false)，1080Ti GPU需要约10分钟
# -c 参数表示指定使用哪个配置文件
# -o 参数表示指定配置文件中的全局变量（覆盖配置文件中的设置），这里设置使用gpu
# --eval 参数表示边训练边评估，最后会自动保存一个名为model_final.pdparams的模型
python tools/train.py -c configs/yolov3/yolov3_mobilenet_v1_roadsign.yml --eval -o use_gpu=true
python -u tools/train.py -c configs/yolov3/yolov3_mobilenet_v1_roadsign.yml \
                        --use_vdl=true \
                        --vdl_log_dir=vdl_dir/scalar \
                        --eval -o use_gpu=false
visualdl --logdir vdl_dir/scalar/ --host 0.0.0.0
```

### 评估
```sh
# 评估 默认使用训练过程中保存的model_final.pdparams
# -c 参数表示指定使用哪个配置文件
# -o 参数表示指定配置文件中的全局变量（覆盖配置文件中的设置）
# 目前只支持单卡评估
python tools/eval.py -c configs/yolov3/yolov3_mobilenet_v1_roadsign.yml -o use_gpu=false
```

### 预测
```sh
# -c 参数表示指定使用哪个配置文件
# -o 参数表示指定配置文件中的全局变量（覆盖配置文件中的设置）
# --infer_img 参数指定预测图像路径
# 预测结束后会在output文件夹中生成一张画有预测结果的同名图像
python tools/infer.py -c configs/yolov3/yolov3_mobilenet_v1_roadsign.yml -o use_gpu=false --infer_img=demo/road554.png
```

## Screenshots
![](https://github.com/PaddlePaddle/PaddleDetection/raw/release/2.6/docs/images/000000014439.jpg)

## References
- [PaddleDetection GitHub](https://github.com/PaddlePaddle/PaddleDetection)
- [PaddleDetection Docker](https://hub.docker.com/r/paddlecloud/paddledetection)
- [PaddleDetection 安装文档](https://github.com/PaddlePaddle/PaddleDetection/blob/release/2.6/docs/tutorials/INSTALL_cn.md)
- [PaddleDetection 快速开始](https://github.com/PaddlePaddle/PaddleDetection/blob/release/2.6/docs/tutorials/QUICK_STARTED_cn.md)
- [PaddleCloud GitHub](https://github.com/PaddlePaddle/PaddleCloud/tree/main/tekton)