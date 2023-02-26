# Jupyter Docker

Free software, open standards, and web services for interactive computing across all programming languages

## Demo
[Try it in your browser](https://jupyter.org/try)

## JupyterLab
A Next-Generation Notebook Interface

```sh
pip install jupyterlab
jupyter-lab
```

### Screenshots
![](https://jupyter.org/assets/homepage/labpreview.webp)

## Jupyter Notebook
The Classic Notebook Interface

```sh
pip install notebook
jupyter notebook
```

### Docker
```sh
docker run -d --name jupyter -p 8888:8888 jupyter/base-notebook
docker run -d --name jupyter -p 8888:8888 -e DOCKER_STACKS_JUPYTER_CMD=notebook jupyter/base-notebook
docker exec -it jupyter jupyter notebook password
docker restart jupyter
```
[http://localhost:8888/](http://localhost:8888/)

### Alternative Commands
```sh
# Run Jupyter Notebook on Jupyter Server
docker run -it --rm \
    -p 8888:8888 \
    -e DOCKER_STACKS_JUPYTER_CMD=notebook \
    jupyter/base-notebook
# Executing the command: jupyter notebook ...

# Run Jupyter Notebook classic
docker run -it --rm \
    -p 8888:8888 \
    -e DOCKER_STACKS_JUPYTER_CMD=nbclassic \
    jupyter/base-notebook
# Executing the command: jupyter nbclassic ...
```

### Image Relationships
![](https://jupyter-docker-stacks.readthedocs.io/en/latest/_images/inherit.svg)

### Architecture
![](https://docs.jupyter.org/en/latest/_images/repos_map.png)

### Screenshots
![](https://jupyter.org/assets/homepage/jupyterpreview.webp)

## Voilà
Share your results

```sh
pip install voila
voila
```

### Screenshots
![](https://jupyter.org/assets/homepage/voilapreview.webp)

## References
- [Jupyter](https://jupyter.org/)
- [Jupyter Docker Stacks GitHub](https://github.com/jupyter/docker-stacks)
- [Jupyter Interactive Notebook GitHub](https://github.com/jupyter/notebook)
- [Selecting an Image](https://jupyter-docker-stacks.readthedocs.io/en/latest/using/selecting.html)
- [Architecture](https://docs.jupyter.org/en/latest/projects/architecture/content-architecture.html)
- [使用Docker安装配置Jupyter](https://www.voidking.com/dev-docker-jupyter/)