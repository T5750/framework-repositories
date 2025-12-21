# Jupyter Docker

Free software, open standards, and web services for interactive computing across all programming languages

## Demo
[Try it in your browser](https://jupyter.org/try)

## JupyterLab
A Next-Generation Notebook Interface

```sh
pip install jupyterlab
jupyter-lab
# 启动服务器而不打开 Web 浏览器
jupyter-lab --no-browser &
```

### Upgrade
```sh
pip install --upgrade jupyterlab
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

## Jupyter Docker Stacks
```sh
docker run -p 10000:8888 jupyter/scipy-notebook
docker run -it --rm -p 10000:8888 -v "${PWD}":/home/jovyan/work jupyter/datascience-notebook
```
Visiting `http://<hostname>:10000/?token=<token>` in a browser loads JupyterLab.

## nbdime
Jupyter Notebook Diff and Merge tools
- `nbdiff` compare notebooks in a terminal-friendly way
- `nbmerge` three-way merge of notebooks with automatic conflict resolution
- `nbdiff-web` shows you a rich rendered diff of notebooks
- `nbmerge-web` gives you a web-based three-way merge tool for notebooks
- `nbshow` present a single notebook in a terminal-friendly way

### Installation
```sh
pip install nbdime
nbdiff notebook_1.ipynb notebook_2.ipynb
nbdiff-web notebook_1.ipynb notebook_2.ipynb
nbdime mergetool
```

### Screenshots
![](https://nbdime.readthedocs.io/en/latest/_images/nbdiff-web.png)

## nbconvert
Convert Notebooks to other formats

### Installation
```sh
pip install nbconvert
```

### Usage
```sh
jupyter nbconvert --to FORMAT notebook.ipynb
# Convert a notebook to HTML
jupyter nbconvert --to html notebook.ipynb
# .ipynb 转换为 .py
jupyter nbconvert --to script --no-prompt notebook.ipynb
```

## References
- [Jupyter](https://jupyter.org/)
- [Jupyter Docker Stacks GitHub](https://github.com/jupyter/docker-stacks)
- [Jupyter Docker Stacks](https://jupyter-docker-stacks.readthedocs.io/en/latest/index.html)
- [Jupyter Interactive Notebook GitHub](https://github.com/jupyter/notebook)
- [Selecting an Image](https://jupyter-docker-stacks.readthedocs.io/en/latest/using/selecting.html)
- [Architecture](https://docs.jupyter.org/en/latest/projects/architecture/content-architecture.html)
- [使用Docker安装配置Jupyter](https://www.voidking.com/dev-docker-jupyter/)
- [nbdime GitHub](https://github.com/jupyter/nbdime)
- [nbdime Documentation](https://nbdime.readthedocs.io/)
- [nbconvert](https://nbconvert.readthedocs.io/)
- [nbconvert GitHub](https://github.com/jupyter/nbconvert)