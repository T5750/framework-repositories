# PyTorch Docker

Tensors and Dynamic neural networks in Python with strong GPU acceleration

## Docker
```sh
docker run --gpus all --rm -ti --ipc=host pytorch/pytorch
docker run -d --name pytorch-notebook -p 8888:8888 -v $PWD/pytorch-notebook:/home/jovyan/work quay.io/jupyter/pytorch-notebook
```
[http://localhost:8888/](http://localhost:8888/)

## Screenshots
![](https://pytorch.org/tutorials/_static/img/thumbnails/tensorboard_scalars.png)

![](https://pytorch.org/tutorials/_images/mp_vs_rn_vs_pp.png)

![](https://pytorch.org/tutorials/_images/split_size_tradeoff.png)

## References
- [PyTorch Docker](https://hub.docker.com/r/pytorch/pytorch)
- [PyTorch GitHub](https://github.com/pytorch/pytorch)
- [PyTorch](https://pytorch.org/)
- [SINGLE-MACHINE MODEL PARALLEL BEST PRACTICES](https://pytorch.org/tutorials/intermediate/model_parallel_tutorial.html)