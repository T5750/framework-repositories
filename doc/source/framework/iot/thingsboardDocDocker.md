# ThingsBoard Doc Docker

## Docker
```
git clone https://github.com/thingsboard/thingsboard.github.io.git
docker run --rm \
  --volume="$PWD/jekyll:/srv/jekyll" \
  --name jekyll \
  -e JEKYLL_UID=1001 \
  -e JEKYLL_GID=1001 \
  -p 4000:4000 \
  -d jekyll/jekyll:4.0 \
  jekyll serve
```

## Jekyll
```
docker exec -it jekyll sh
cd thingsboard.github.io
bundle install
cp -r /srv/jekyll/thingsboard.github.io/_site/* /srv/jekyll/_site/
```
http://localhost:4000/

## References
- [thingsboard.github.io 3.1](https://github.com/thingsboard/thingsboard.github.io/tree/develop/3.1)