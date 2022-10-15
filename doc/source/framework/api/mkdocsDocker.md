# MkDocs Docker

Documentation that simply works

## Demo
[Demo](https://squidfunk.github.io/mkdocs-material/)

## Docker
```sh
docker run -it --rm -v $PWD:/docs squidfunk/mkdocs-material new docs
docker run --rm -it -p 8000:8000 -v $PWD/docs:/docs squidfunk/mkdocs-material
```
[http://localhost:8000/](http://localhost:8000/)

### Build documentation
```sh
docker run --rm -it -v $PWD:/docs squidfunk/mkdocs-material build
```

### Deploy documentation to GitHub Pages
```sh
docker run --rm -it -v ~/.ssh:/root/.ssh -v $PWD:/docs squidfunk/mkdocs-material gh-deploy
```

## Screenshots

### More than just a static site
![](https://squidfunk.github.io/mkdocs-material/overrides/assets/images/spotlight/built-in-search.png)

![](https://squidfunk.github.io/mkdocs-material/overrides/assets/images/spotlight/code-annotations.png)

![](https://squidfunk.github.io/mkdocs-material/overrides/assets/images/spotlight/icons-emojis.png)

## References
- [MkDocs Docker](https://hub.docker.com/r/squidfunk/mkdocs-material)
- [MkDocs GitHub](https://github.com/squidfunk/mkdocs-material)