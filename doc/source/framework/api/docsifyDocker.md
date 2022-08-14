# Docsify Docker

A magical documentation site generator.

## Examples
[awesome-docsify](https://github.com/docsifyjs/awesome-docsify)

## Quick start
`vi index.html`
```html
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <meta charset="UTF-8">
  <link rel="stylesheet" href="//cdn.jsdelivr.net/npm/docsify/themes/vue.css">
</head>
<body>
  <div id="app"></div>
  <script>
    window.$docsify = {
      //...
    }
  </script>
  <script src="//cdn.jsdelivr.net/npm/docsify/lib/docsify.min.js"></script>
</body>
</html>
```

## Dockerfile
```
FROM node:latest
LABEL description="A demo Dockerfile for build Docsify."
WORKDIR /docs
RUN npm install -g docsify-cli@latest
EXPOSE 3000/tcp
ENTRYPOINT docsify serve .
```

## Docker
The current directory structure should be this:
```
index.html
README.md
Dockerfile
```
```sh
docker build -f Dockerfile -t docsify/demo .
docker run -itp 3000:3000 --name=docsify -v $(pwd):/docs docsify/demo
```
[http://localhost:3000/](http://localhost:3000/)

## References
- [Docsify](https://docsify.js.org/#/zh-cn/deploy?id=docker)
- [Docsify GitHub](https://github.com/docsifyjs/docsify)