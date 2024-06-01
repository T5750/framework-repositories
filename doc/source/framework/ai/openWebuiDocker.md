# Open WebUI Docker

**Open WebUI is an extensible, feature-rich, and user-friendly self-hosted WebUI designed to operate entirely offline.** It supports various LLM runners, including Ollama and OpenAI-compatible APIs.

## Docker
### Installation with Default Configuration
- **If Ollama is on your computer**, use this command:
  ```sh
  docker run -d -p 3000:8080 --add-host=host.docker.internal:host-gateway -v open-webui:/app/backend/data --name open-webui --restart always ghcr.io/open-webui/open-webui:main
  ```
- **If Ollama is on a Different Server**, use this command:
  ```sh
  docker run -d -p 3000:8080 -e OLLAMA_BASE_URL=https://example.com -v open-webui:/app/backend/data --name open-webui --restart always ghcr.io/open-webui/open-webui:main
  ```
  - **To run Open WebUI with Nvidia GPU support**, use this command:
  ```sh
  docker run -d -p 3000:8080 --gpus all --add-host=host.docker.internal:host-gateway -v open-webui:/app/backend/data --name open-webui --restart always ghcr.io/open-webui/open-webui:cuda
  ```

### Installation for OpenAI API Usage Only
- **If you're only using OpenAI API**, use this command:
  ```sh
  docker run -d -p 3000:8080 -e OPENAI_API_KEY=your_secret_key -v open-webui:/app/backend/data --name open-webui --restart always ghcr.io/open-webui/open-webui:main
  ```

### Installing Open WebUI with Bundled Ollama Support
- **With GPU Support**:
  Utilize GPU resources by running the following command:
  ```sh
  docker run -d -p 3000:8080 --gpus=all -v ollama:/root/.ollama -v open-webui:/app/backend/data --name open-webui --restart always ghcr.io/open-webui/open-webui:ollama
  ```
- **For CPU Only**:
  If you're not using a GPU, use this command instead:
  ```sh
  docker run -d -p 3000:8080 -v ollama:/root/.ollama -v open-webui:/app/backend/data --name open-webui --restart always ghcr.io/open-webui/open-webui:ollama
  ```
[http://localhost:3000](http://localhost:3000)

## Docker Compose
- If you don't have Ollama yet, use Docker Compose for easy installation
  ```sh
  docker compose up -d --build
  ```
- For Nvidia GPU Support
  ```sh
  docker compose -f docker-compose.yaml -f docker-compose.gpu.yaml up -d --build
  ```
- For AMD GPU Support
  ```sh
  HSA_OVERRIDE_GFX_VERSION=11.0.0 docker compose -f docker-compose.yaml -f docker-compose.amdgpu.yaml up -d --build
  ```
- To Expose Ollama API
  ```sh
  docker compose -f docker-compose.yaml -f docker-compose.api.yaml up -d --build
  ```

## Ollama Load Balancing Setup
### Docker
```sh
docker run -d -p 3000:8080 \
  -v open-webui:/app/backend/data \
  -e OLLAMA_BASE_URLS="http://ollama-one:11434;http://ollama-two:11434" \
  --name open-webui \
  --restart always \
  ghcr.io/open-webui/open-webui:main
```

### Docker Compose
```
services:
  open-webui:
    environment:
      - OLLAMA_BASE_URLS=http://ollama-one:11434;http://ollama-two:11434
```

## Image Generation
### AUTOMATIC1111
1. Ensure that you have [AUTOMATIC1111](https://github.com/AUTOMATIC1111/stable-diffusion-webui) installed.
2. Launch AUTOMATIC1111 with additional flags to enable API access:
    ```sh
    ./webui.sh --api --listen
    ```
3. For Docker installation of WebUI with the environment variables preset, use the following command:
    ```sh
    docker run -d -p 3000:8080 --add-host=host.docker.internal:host-gateway -e AUTOMATIC1111_BASE_URL=http://host.docker.internal:7860/ -e ENABLE_IMAGE_GENERATION=True -v open-webui:/app/backend/data --name open-webui --restart always ghcr.io/open-webui/open-webui:main
    ```

### ComfyUI
1. Download and extract the ComfyUI software package from GitHub to your desired directory.
2. To start ComfyUI, run the following command:
    ```sh
    python main.py
    # For systems with low VRAM, launch ComfyUI with additional flags to reduce memory usage:
    python main.py --lowvram
    ```
3. For Docker installation of WebUI with the environment variables preset, use the following command:
    ```sh
    docker run -d -p 3000:8080 --add-host=host.docker.internal:host-gateway -e COMFYUI_BASE_URL=http://host.docker.internal:7860/ -e ENABLE_IMAGE_GENERATION=True -v open-webui:/app/backend/data --name open-webui --restart always ghcr.io/open-webui/open-webui:main
    ```

## Runtime Environment
- [Python 3.11.x](https://www.python.org/downloads/)
- [TypeScript](https://www.typescriptlang.org/)

## Screenshots
![](https://docs.openwebui.com/assets/images/demo-6793d95448aa180bca8dafbd21aa91b5.gif)

## References
- [Open WebUI](https://openwebui.com/)
- [Open WebUI GitHub](https://github.com/open-webui/open-webui)
- [Open WebUI Docker](https://docs.openwebui.com/getting-started/)
- [Ollama Load Balancing Setup](https://docs.openwebui.com/tutorial/ollama)
- [Open WebUI Image Generation](https://docs.openwebui.com/tutorial/images/)