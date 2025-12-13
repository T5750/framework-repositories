# PyTorch Docker

Tensors and Dynamic neural networks in Python with strong GPU acceleration

## Docker
```sh
docker run --gpus all --rm -ti --ipc=host pytorch/pytorch
```

### jupyter/pytorch-notebook
```sh
# CPU
docker run -d --name pytorch-notebook -p 8888:8888 -v $PWD/pytorch-notebook:/home/jovyan/work quay.io/jupyter/pytorch-notebook
# GPU
docker run -d --name pytorch-notebook -p 8888:8888 -v $PWD/pytorch-notebook:/home/jovyan/work quay.io/jupyter/pytorch-notebook:cuda12-latest
docker exec -it pytorch-notebook pip show torch
```
- [http://localhost:8888/](http://localhost:8888/)
- [Quay.io image tags](https://quay.io/repository/jupyter/pytorch-notebook?tab=tags)

## Poetry
Poetry 是一个用于 Python 中 **依赖项管理** 和 **打包** 的工具
```sh
pipx install poetry
poetry new poetry-demo
# 初始化现有项目
# poetry init
cd poetry-demo
vi pyproject.toml # 放在[tool.poetry]下方
```
```
[[tool.poetry.source]]
name = "pytorch-cpu"
url = "https://download.pytorch.org/whl/cpu"
priority = "supplemental"
```
```sh
poetry add torch==2.6.0+cpu torchvision==0.21.0+cpu torchaudio==2.6.0+cpu
# poetry add ultralytics fastapi pydantic uvicorn requests
# 安装插件
poetry self add poetry-plugin-shell
poetry shell
python -c "import torch; print(f'PyTorch: {torch.__version__}, GPU: {torch.cuda.is_available()}')"
```

## TensorBoard
TensorBoard 是一个用于 TensorFlow 的可视化工具包
```sh
pip install tensorboard
tensorboard --logdir=runs --host=0.0.0.0
```
- [http://localhost:6006/](http://localhost:6006/)
- 可视化类型: [torch.utils.tensorboard 教程](https://pytorch.ac.cn/docs/stable/tensorboard.html)

### Example
```python
import torch
from torch.utils.tensorboard import SummaryWriter
writer = SummaryWriter()
x = torch.arange(-5, 5, 0.1).view(-1, 1)
y = -5 * x + 0.1 * torch.randn(x.size())

model = torch.nn.Linear(1, 1)
criterion = torch.nn.MSELoss()
optimizer = torch.optim.SGD(model.parameters(), lr = 0.1)

def train_model(iter):
    for epoch in range(iter):
        y1 = model(x)
        loss = criterion(y1, y)
        writer.add_scalar("Loss/train", loss, epoch)
        optimizer.zero_grad()
        loss.backward()
        optimizer.step()

train_model(10)
writer.flush()
writer.close()
```

## Runtime Environment
- [PyTorch 2.x](https://pytorch.org/)
- [Python 3.12.x](https://www.python.org/downloads/)
- [Poetry 2.x](https://pypi.org/project/poetry/)
- [TensorBoard](https://pypi.org/project/tensorboard/)

## Screenshots
![](https://pytorch.org/tutorials/_static/img/thumbnails/tensorboard_scalars.png)

![](https://pytorch.org/tutorials/_images/mp_vs_rn_vs_pp.png)

![](https://pytorch.org/tutorials/_images/split_size_tradeoff.png)

## References
- [PyTorch](https://pytorch.org/)
- [PyTorch Docker](https://hub.docker.com/r/pytorch/pytorch)
- [PyTorch GitHub](https://github.com/pytorch/pytorch)
- [SINGLE-MACHINE MODEL PARALLEL BEST PRACTICES](https://pytorch.org/tutorials/intermediate/model_parallel_tutorial.html)
- [Poetry 文档](https://python-poetry.cn/docs/)
- [TensorBoard GitHub](https://github.com/tensorflow/tensorboard)
- [如何在 PyTorch 中使用 TensorBoard](https://docs.pytorch.ac.cn/tutorials/recipes/recipes/tensorboard_with_pytorch.html)