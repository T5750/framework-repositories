# PaddlePaddle Docker

源于产业实践的开源深度学习平台

飞桨致力于让深度学习技术的创新与应用更简单

## pip
```sh
python -m pip install paddlepaddle==2.6.2 -i https://pypi.tuna.tsinghua.edu.cn/simple
pip install jupyterlab
jupyter-lab
```

## Docker
CPU版的PaddlePaddle：
```sh
docker run --name paddle -it -v $PWD:/paddle registry.baidubce.com/paddlepaddle/paddle:2.4.2 /bin/bash
```
CPU版的PaddlePaddle，且镜像中预装好了 jupyter：
```sh
docker run -p 8080:80 --rm --env USER_PASSWD=123456 -v $PWD:/home/paddle registry.baidubce.com/paddlepaddle/paddle:2.4.2-jupyter
```
- [http://localhost:8080/](http://localhost:8080/)
- User: jovyan / 123456

## References
- [PaddlePaddle](https://www.paddlepaddle.org.cn/)
- [PaddlePaddle GitHub](https://github.com/paddlepaddle/paddle)