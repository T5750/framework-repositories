# FlareSolverr Docker

FlareSolverr is a proxy server to bypass Cloudflare and DDoS-GUARD protection.

## Docker
```sh
docker run -d \
  --name=flaresolverr \
  -p 8191:8191 \
  -e LOG_LEVEL=info \
  --restart unless-stopped \
  ghcr.io/flaresolverr/flaresolverr:latest
docker run -d --name=flaresolverr -p 8191:8191 ghcr.io/flaresolverr/flaresolverr
```
[http://localhost:8191/](http://localhost:8191/)

## Usage
Example Bash request:
```sh
curl -L -X POST 'http://localhost:8191/v1' \
-H 'Content-Type: application/json' \
--data-raw '{
  "cmd": "request.get",
  "url": "http://www.baidu.com/",
  "maxTimeout": 60000
}'
```

Example Python request:
```py
import requests

url = "http://localhost:8191/v1"
headers = {"Content-Type": "application/json"}
data = {
    "cmd": "request.get",
    "url": "http://www.baidu.com/",
    "maxTimeout": 60000
}
response = requests.post(url, headers=headers, json=data)
print(response.text)
```

Example PowerShell request:
```ps1
$body = @{
    cmd = "request.get"
    url = "http://www.baidu.com/"
    maxTimeout = 60000
} | ConvertTo-Json

irm -UseBasicParsing 'http://localhost:8191/v1' -Headers @{"Content-Type"="application/json"} -Method Post -Body $body
```

## Runtime Environment
- [Python 3.11.x](https://www.python.org/downloads/)

## References
- [FlareSolverr GitHub](https://github.com/FlareSolverr/FlareSolverr)