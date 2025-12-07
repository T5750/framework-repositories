# FastAPI Docker

FastAPI 框架，高性能，易于学习，高效编码，生产可用

## Docker
### 依赖项
`vi requirements.txt`
```
fastapi>=0.68.0,<0.69.0
pydantic>=1.8.0,<2.0.0
uvicorn>=0.15.0,<0.16.0
```

### FastAPI 代码
`vi api/main.py`
```py
from typing import Union
from fastapi import FastAPI
app = FastAPI()

@app.get("/")
def read_root():
    return {"Hello": "World"}

@app.get("/items/{item_id}")
def read_item(item_id: int, q: Union[str, None] = None):
    return {"item_id": item_id, "q": q}
```

### Dockerfile
`vi Dockerfile`
```
FROM python:3.12-slim-bookworm
WORKDIR /app
COPY ./requirements.txt /app/requirements.txt
RUN pip install --no-cache-dir --upgrade -r /app/requirements.txt
COPY ./api /app/api
CMD ["uvicorn", "api.main:app", "--host", "0.0.0.0", "--port", "80"]
```

目录结构：
```
.
├── api
│   └── main.py
├── Dockerfile
└── requirements.txt
```

### 构建 Docker 镜像
```sh
docker build -t myimage .
```

### 启动 Docker 容器
```sh
docker run -d --name mycontainer -p 8000:80 myimage
```
- Swagger UI: [http://localhost:8000/docs](http://localhost:8000/docs)
- ReDoc: [http://127.0.0.1:8000/redoc](http://127.0.0.1:8000/redoc)
- 检查一下: [http://127.0.0.1:8000/items/5?q=somequery](http://127.0.0.1:8000/items/5?q=somequery)

## pip
### 安装
```sh
pip install fastapi
# ASGI 服务器
pip install "uvicorn[standard]"
```

### 运行
```sh
uvicorn main:app --reload
```

## fastapi_production_template
FastAPI Template with Docker, Postgres

### Local Development
```sh
git clone https://github.com/zhanymkanov/fastapi_production_template.git
# Windows
winget install --id Casey.Just --exact
pip install --upgrade build
pip install poetry
cp .env.example .env
just run
```

### Deployment
```sh
docker compose -f docker-compose.prod.yml up -d --build
```

## Tips
### Swagger UI加载缓慢
`vi api/main.py`放置在`app = FastAPI()`代码之前
```py
from fastapi import applications
from fastapi.openapi.docs import get_swagger_ui_html
def swagger_monkey_patch(*args, **kwargs):
    return get_swagger_ui_html(
        *args, **kwargs,
        swagger_js_url="https://cdn.staticfile.net/swagger-ui/5.1.0/swagger-ui-bundle.min.js",
        swagger_css_url="https://cdn.staticfile.net/swagger-ui/5.1.0/swagger-ui.min.css"
    )
applications.get_swagger_ui_html = swagger_monkey_patch
```

## Runtime Environment
- [Python 3.12.x](https://www.python.org/downloads/)
- [FastAPI](https://pypi.org/project/fastapi/)

## Screenshots
![](https://fastapi.tiangolo.com/img/index/index-01-swagger-ui-simple.png)

![](https://fastapi.tiangolo.com/img/index/index-02-redoc-simple.png)

## References
- [FastAPI](https://fastapi.tiangolo.com/)
- [FastAPI GitHub](https://github.com/fastapi/fastapi)
- [FastAPI Docker](https://fastapi.tiangolo.com/zh/deployment/docker/)
- [Fastapi中Swagger UI加载缓慢的解决方案](https://www.cnblogs.com/bokemoqi/p/18080637)
- [zhanymkanov/fastapi-best-practices](https://github.com/zhanymkanov/fastapi-best-practices)
- [zhanymkanov/fastapi_production_template](https://github.com/zhanymkanov/fastapi_production_template)
- [just GitHub](https://github.com/casey/just)