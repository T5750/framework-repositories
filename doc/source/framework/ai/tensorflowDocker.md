# TensorFlow Docker

使用 TensorFlow 创建生产级机器学习模型

## Docker
```sh
docker run [-it] [--rm] [-p hostPort:containerPort] tensorflow/tensorflow[:tag] [command]
```

### 使用仅支持 CPU 的映像的示例
```sh
docker run -it --rm tensorflow/tensorflow \
   python -c "import tensorflow as tf; print(tf.reduce_sum(tf.random.normal([1000, 1000])))"
docker run -it --rm tensorflow/tensorflow bash
docker run -it -p 8888:8888 tensorflow/tensorflow:nightly-jupyter
```
[http://localhost:8888/](http://localhost:8888/)

## GPU 支持
```sh
lspci | grep -i nvidia
docker run --gpus all --rm nvidia/cuda nvidia-smi
```

### 使用支持 GPU 的映像的示例
```sh
docker run --gpus all -it --rm tensorflow/tensorflow:latest-gpu \
   python -c "import tensorflow as tf; print(tf.reduce_sum(tf.random.normal([1000, 1000])))"
docker run --gpus all -it tensorflow/tensorflow:latest-gpu bash
```

### jupyter/tensorflow-notebook
#### Single Machine Mode
```sh
import tensorflow as tf
hello = tf.Variable("Hello World!")
sess = tf.Session()
init = tf.global_variables_initializer()
sess.run(init)
sess.run(hello)
```

#### Distributed Mode
```sh
import tensorflow as tf
hello = tf.Variable("Hello Distributed World!")
server = tf.train.Server.create_local_server()
sess = tf.Session(server.target)
init = tf.global_variables_initializer()
sess.run(init)
sess.run(hello)
```

## References
- [TensorFlow](https://tensorflow.google.cn/)
- [TensorFlow GitHub](https://github.com/tensorflow/tensorflow)
- [TensorFlow Docker](https://tensorflow.google.cn/install/docker?hl=zh-cn)
- [jupyter/tensorflow-notebook Docker](https://hub.docker.com/r/jupyter/tensorflow-notebook)
- [Jupyter Docker Stacks Image Specifics](https://jupyter-docker-stacks.readthedocs.io/en/latest/using/specifics.html)