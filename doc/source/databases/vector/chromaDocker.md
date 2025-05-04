# Chroma Docker

Chroma is the open-source AI application database. Batteries included.

## Docker
```sh
docker run -d --name chroma -v ./chroma-data:/data -p 8000:8000 chromadb/chroma
```

## Python
```sh
pip install chromadb
chroma run
chroma run --host 0.0.0.0 --port 8000 --path /db_path --log-path /var/log/chroma.log
```

## Runtime Environment
- [Rust](https://www.rust-lang.org)
- [Python 3.x](https://www.python.org/downloads/)
- [Go](https://golang.org/)

## Screenshots
![](https://trychroma.com/_next/static/media/computer_no_holodeck.df6355b4.svg)

## References
- [Chroma](https://trychroma.com/)
- [Chroma GitHub](https://github.com/chroma-core/chroma)
- [Chroma Docker](https://docs.trychroma.com/production/containers/docker)
- [Chroma Getting Started](https://docs.trychroma.com/docs/overview/getting-started)