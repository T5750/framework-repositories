# Jekyll Docker
Jekyll is a blog-aware static site generator in Ruby

## Image Types
* `jekyll/jekyll`: Default image.
* `jekyll/minimal`: Very minimal image.
* `jekyll/builder`: Includes tools.

### Standard
```
$ echo $UID
1001
```
```
docker run --rm \
  --volume="$PWD/jekyll:/srv/jekyll" \
  --name jekyll \
  -e JEKYLL_UID=1001 \
  -e JEKYLL_GID=1001 \
  -p 4000:4000 \
  -it jekyll/jekyll:4.0 \
  jekyll build
```

## Server
For local development, Jekyll can be run in server mode inside the container. It will watch for changes, rebuild the site, and provide access through its included web server. You can then check the results of changes by reloading http://localhost:4000/ in a browser.
```
docker run --rm \
  --volume="$PWD/jekyll:/srv/jekyll" \
  --name jekyll \
  -e JEKYLL_UID=1001 \
  -e JEKYLL_GID=1001 \
  -p 4000:4000 \
  -d jekyll/jekyll:4.0 \
  jekyll serve
```

## Configuration

| ENV Var | Default |
|---|---|
| `JEKYLL_UID` | `1000` |
| `JEKYLL_GID` | `1000` |
| `JEKYLL_DEBUG` | `""` |
| `VERBOSE` | `""` |
| `FORCE_POLLING` | `""` |

## References
- [Jekyll Docker](https://github.com/envygeeks/jekyll-docker/blob/master/README.md)
- [Jekyll Github](https://github.com/jekyll/jekyll)
- [Jekyll](https://jekyllrb.com/)