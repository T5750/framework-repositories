# Node.js Docker

## 创建 Node.js 应用
`vi package.json`
```json
{
  "name": "docker_web_app",
  "version": "1.0.0",
  "description": "Node.js on Docker",
  "author": "First Last <first.last@example.com>",
  "main": "server.js",
  "scripts": {
    "start": "node server.js"
  },
  "dependencies": {
    "express": "^4.16.1"
  }
}
```
`vi server.js`
```js
'use strict';
const express = require('express');
// Constants
const PORT = 8080;
const HOST = '0.0.0.0';
// App
const app = express();
app.get('/', (req, res) => {
  res.send('Hello World');
});
app.listen(PORT, HOST);
console.log(`Running on http://${HOST}:${PORT}`);
```

## 创建一个名称为 Dockerfile 的文件
`touch Dockerfile`
```
FROM node:16
# Create app directory
WORKDIR /usr/src/app
# Install app dependencies
# A wildcard is used to ensure both package.json AND package-lock.json are copied
# where available (npm@5+)
COPY package*.json ./
RUN npm install
# If you are building your code for production
# RUN npm ci --only=production
# Bundle app source
COPY . .
EXPOSE 8080
CMD [ "node", "server.js" ]
```

## .dockerignore 文件
`vi .dockerignore`
```
node_modules
npm-debug.log
```

## 构建你的镜像
```sh
docker build . -t t5750/node-web-app
```

## 运行镜像
```sh
docker run -p 49160:8080 -d --rm --name node-web-app t5750/node-web-app
```
- [http://localhost:49160](http://localhost:49160)
- `curl -i localhost:49160`

## Tips
```sh
npm config get registry
npm config set registry https://registry.npm.taobao.org
```

## References
- [把一个 Node.js web 应用程序给 Docker 化](https://nodejs.org/zh-cn/docs/guides/nodejs-docker-webapp/)
- [Node.js Docker](https://hub.docker.com/_/node/)