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
```
mkdir myapp
vi myapp/helloworld.py
```
```python
#!/usr/bin/python
print("Hello, World!");
```
``` 
docker run --rm -v $PWD/myapp:/usr/src/myapp -w /usr/src/myapp python:3 python helloworld.py
```

## Tips
```
ps -ef | grep python
ps aux | grep python
```

## References
- [Python](https://hub.docker.com/_/python?tab=description&page=1&ordering=last_updated)
- [Docker 安装 Python](https://www.runoob.com/docker/docker-install-python.html)