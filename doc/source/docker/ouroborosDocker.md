# Ouroboros Docker

Automatically update running docker containers with newest available image

## Docker
```sh
docker run -d --name ouroboros \
  -v /var/run/docker.sock:/var/run/docker.sock \
  pyouroboros/ouroboros
docker run -d --name ouroboros \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -p 8000:8000 pyouroboros/ouroboros --data-export prometheus --prometheus-addr 0.0.0.0
```
[http://localhost:8000/](http://localhost:8000/)

## Usage
```sh
docker run --rm pyouroboros/ouroboros --help
```

## Screenshots
![](https://camo.githubusercontent.com/6ac695177faa74ecc33d3382abc8db6b1fff4bd3d1f047e1d250659513b341fc/68747470733a2f2f67726166616e612e636f6d2f6170692f64617368626f617264732f393732382f696d616765732f363130312f696d616765)

## References
- [Ouroboros Docker](https://hub.docker.com/r/pyouroboros/ouroboros/)
- [Ouroboros GitHub](https://github.com/pyouroboros/ouroboros)
- [Ouroboros Grafana](https://github.com/pyouroboros/ouroboros/wiki/Grafana)