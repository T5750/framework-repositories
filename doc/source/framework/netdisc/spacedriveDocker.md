# Spacedrive Docker

One Explorer. All Your Files.

## Docker
```sh
docker run -d --name spacedrive -p 8080:8080 -v /var/spacedrive:/var/spacedrive ghcr.io/spacedriveapp/spacedrive/server
docker run -d --name spacedrive -p 8080:8080 -v /var/spacedrive:/var/spacedrive --privileged=true ghcr.io/spacedriveapp/spacedrive/server
```
[http://localhost:8080/](http://localhost:8080/)

## Runtime Environment
- [Rust](https://www.rust-lang.org)
- [TypeScript](https://www.typescriptlang.org/)

## Screenshots
![](https://www.spacedrive.com/_next/image?url=%2Fimages%2Fapp%2F1.webp&w=3840&q=75)

![](https://www.spacedrive.com/_next/image?url=%2Fintroduction.webp&w=3840&q=75)

## References
- [Spacedrive](https://www.spacedrive.com/)
- [Spacedrive GitHub](https://github.com/spacedriveapp/spacedrive)
- [Spacedrive Setup](https://www.spacedrive.com/docs/product/getting-started/setup)