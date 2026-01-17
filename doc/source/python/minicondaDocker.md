# Miniconda Docker

Miniconda 是一个免费的、微型的 Anaconda Distribution 安装包

## Docker
```sh
docker run -i -t continuumio/miniconda3 /bin/bash
docker run -i -t -p 8888:8888 continuumio/miniconda3 /bin/bash -c "\
    conda install jupyter -y --quiet && \
    mkdir -p /opt/notebooks && \
    jupyter notebook \
    --notebook-dir=/opt/notebooks --ip='*' --port=8888 \
    --no-browser --allow-root"
```
[http://localhost:8888/](http://localhost:8888/)

## Tests
```sh
conda info
```

## References
- [Miniconda](https://www.anaconda.com/docs/getting-started/miniconda/main)
- [Miniconda Docker](https://anaconda.org.cn/anaconda/user-guide/tasks/docker/)
- [Conda GitHub](https://github.com/conda/conda)
- [Conda Documentation](https://docs.conda.io/projects/conda/)