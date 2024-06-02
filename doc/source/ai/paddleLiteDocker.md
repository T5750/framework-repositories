# Paddle Lite Docker

面向端侧场景的轻量化推理引擎Paddle Lite，可以实现飞桨模型在x86/ARM平台下多种OS内的高效部署，同时支持在10种以上的GPU/NPU异构后端上进行推理加速和混合调度；通过Paddle Lite，您在不同端侧场景下的模型部署需求都可以被完美支持。

## Demo
- [Paddle Lite 示例应用](https://www.paddlepaddle.org.cn/lite#demo)
- [Paddle-Lite-Demo](https://github.com/PaddlePaddle/Paddle-Lite-Demo)

## Docker
```sh
docker run -it \
  --name paddlelite_docker \
  -v $PWD/Paddle-Lite:/Paddle-Lite \
  --net=host \
  paddlepaddle/paddle-lite /bin/bash
```

## Architecture
![](https://paddlelite-demo.bj.bcebos.com/devices/generic/paddle_lite_with_nnadapter.jpg)

## References
- [Paddle Lite](https://www.paddlepaddle.org.cn/lite)
- [Paddle Lite GitHub](https://github.com/PaddlePaddle/Paddle-Lite)
- [Paddle Lite Docker](https://www.paddlepaddle.org.cn/lite/v2.12/source_compile/docker_env.html)