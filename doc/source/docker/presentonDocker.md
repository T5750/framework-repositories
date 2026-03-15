# Presentation Docker

Open-Source AI Presentation Generator

## Docker
```sh
docker run -it --name presenton -p 5000:80 -v "./app_data:/app/app_data" ghcr.io/presenton/presenton:latest
docker run -d --name presenton -p 5000:80 -v $PWD/app_data:/app/app_data ghcr.io/presenton/presenton
```
[http://localhost:5000/](http://localhost:5000/)

## Using Ollama Models
Example: Run Presenton with Ollama
```sh
docker run -it --name presenton -p 5000:80 \
  -e LLM="ollama" \
  -e OLLAMA_MODEL="llama3.2:3b" \
  -e IMAGE_PROVIDER="pexels" \
  -e PEXELS_API_KEY="your_pexels_api_key" \
  -e CAN_CHANGE_KEYS="false" \
  -v "./app_data:/app_data" \
  ghcr.io/presenton/presenton:latest
```

Example: Run Presenton with your own Ollama server
```sh
docker run -it --name presenton -p 5000:80 \
  -e LLM="ollama" \
  -e OLLAMA_MODEL="llama3.2:3b" \
  -e OLLAMA_URL="http://XXXXXXXXXXXXX" \
  -e IMAGE_PROVIDER="pexels" \
  -e PEXELS_API_KEY="your_pexels_api_key" \
  -e CAN_CHANGE_KEYS="false" \
  -v "./app_data:/app_data" \
  ghcr.io/presenton/presenton:latest
```

## Runtime Environment
- [Python 3.11.x](https://www.python.org/downloads/)
- [TypeScript](https://www.typescriptlang.org/)

## Screenshots
![](https://presenton.ai/download-cta.png)

## References
- [Presentation](https://presenton.ai/)
- [Presentation GitHub](https://github.com/presenton/presenton)
- [Presentation Docker](https://docs.presenton.ai/quickstart)
- [Presentation Ollama](https://docs.presenton.ai/configurations/using-ollama-models)