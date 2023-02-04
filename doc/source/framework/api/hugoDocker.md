# Hugo Docker

The world’s fastest framework for building websites

## Docker
Normal build:
```sh
docker run --rm -it -v $(pwd):/src klakegg/hugo new site quickstart
cd quickstart
git init
git submodule add https://github.com/theNewDynamic/gohugo-theme-ananke themes/ananke
echo "theme = 'ananke'" >> config.toml
```
Run server:
```sh
docker run --rm -it -v $(pwd):/src -p 1313:1313 klakegg/hugo server
```
[http://localhost:1313/](http://localhost:1313/)

## Docker Compose
Normal build:
```
  build:
    image: klakegg/hugo:0.101.0
    volumes:
      - ".:/src"
```
Run server:
```
  server:
    image: klakegg/hugo:0.101.0
    command: server
    volumes:
      - ".:/src"
    ports:
      - "1313:1313"
```

## Add content
```sh
docker run --rm -it -v $(pwd):/src klakegg/hugo new posts/my-first-post.md
vi posts/my-first-post.md
```
```
---
title: "My First Post"
date: 2022-11-20T09:03:20-08:00
draft: true
---
## Introduction

This is **bold** text, and this is *emphasized* text.

Visit the [Hugo](https://gohugo.io) website!
```
```sh
docker run --rm -it -v $(pwd):/src -p 1313:1313 klakegg/hugo server --buildDrafts
```

## Basic usage
```sh
hugo version
hugo help
hugo server --help
```

### Build your site
```sh
hugo
```

### Draft, future, and expired content
```sh
hugo --buildDrafts    # or -D
hugo --buildExpired   # or -E
hugo --buildFuture    # or -F
```

## Hugo shell
```sh
docker run --rm -it \
  -v $(pwd):/src \
  klakegg/hugo:0.101.0-alpine \
  shell
```

## Using Pandoc
```sh
docker run --rm -it \
  -v $(pwd):/src \
  -e HUGO_PANDOC="pandoc-default --strip-empty-paragraphs" \
  klakegg/hugo:0.101.0-pandoc
```

## Screenshots
![](https://d33wubrfki0l68.cloudfront.net/291588ca4ec28632c45707669f113eced92ea64d/f1a39/images/homepage-screenshot-hugo-themes.jpg)

## References
- [Hugo](https://gohugo.io/)
- [Hugo GitHub](https://github.com/gohugoio/hugo)
- [Hugo Docker](https://hub.docker.com/r/klakegg/hugo)
- [Quick Start](https://gohugo.io/getting-started/quick-start/)
- [Basic usage](https://gohugo.io/getting-started/usage/)