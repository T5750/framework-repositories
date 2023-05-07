# Python Docker

## Dockerfile
```
FROM python:3
WORKDIR /usr/src/app
COPY requirements.txt ./
RUN pip install --no-cache-dir -r requirements.txt
COPY . .
CMD [ "python", "./your-daemon-or-script.py" ]
```
or (if you need to use Python 2):
```
FROM python:2
WORKDIR /usr/src/app
COPY requirements.txt ./
RUN pip install --no-cache-dir -r requirements.txt
COPY . .
CMD [ "python", "./your-daemon-or-script.py" ]
```
You can then build and run the Docker image:
```
$ docker build -t my-python-app .
$ docker run -it --rm --name my-running-app my-python-app
```

## Tests
```sh
mkdir myapp
vi myapp/helloworld.py
```
```python
#!/usr/bin/python
print("Hello, World!");
```
```sh
docker run --rm -v $PWD/myapp:/usr/src/myapp -w /usr/src/myapp python:3 python helloworld.py
```

## pip
### Upgrade pip
```sh
python -m pip install --upgrade pip
pip -V
```

### Config pip
```sh
pip config set global.index-url https://pypi.tuna.tsinghua.edu.cn/simple
# https://mirrors.aliyun.com/pypi/simple/
pip install ping3 -i https://pypi.tuna.tsinghua.edu.cn/simple
```

## Conda
```sh
conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/free/
conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud/conda-forge
conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud/msys2/
conda config --set show_channel_urls yes
conda config --show
```

## Tips
```sh
ps -ef | grep python
ps aux | grep python
```

## References
- [Python Docker](https://hub.docker.com/_/python?tab=description&page=1&ordering=last_updated)
- [Docker 安装 Python](https://www.runoob.com/docker/docker-install-python.html)
- [解决conda下载速度慢的问题](https://blog.csdn.net/Xiao_Spring/article/details/109130663)