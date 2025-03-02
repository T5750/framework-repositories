# vLLM Docker

Easy, fast, and cheap LLM serving for everyone

vLLM 是一个快速且易于使用的库，专为大型语言模型 (LLM) 的推理和部署而设计

## Docker
### GPU
```sh
docker run --runtime nvidia --gpus all \
    -v ~/.cache/huggingface:/root/.cache/huggingface \
    --env "HUGGING_FACE_HUB_TOKEN=<secret>" \
    -p 8000:8000 \
    --ipc=host \
    vllm/vllm-openai:latest \
    --model mistralai/Mistral-7B-v0.1
```

### CPU
```sh
docker build -f Dockerfile.cpu -t vllm-cpu-env --shm-size=4g .
docker run -it \
             --rm \
             --network=host \
             --cpuset-cpus=<cpu-id-list, optional> \
             --cpuset-mems=<memory-node, optional> \
             vllm-cpu-env
```

### Windows
```sh
docker run --runtime nvidia --gpus all -p 8000:8000 --ipc=host -v /d/models:/models vllm/vllm-openai --model /models/Qwen/Qwen2.5-0.5B --served-model-name Qwen/Qwen2.5-0.5B
```

## pip
```sh
python -m venv venv
# macOS/Linux
source venv/bin/activate
# Windows
venv\Scripts\activate
pip install vllm
```

## API
FastAPI:
- [http://localhost:8000/docs](http://localhost:8000/docs)
- [http://localhost:8000/v1/chat/completions](http://localhost:8000/v1/chat/completions)

## Runtime Environment
- [Python 3.12.x](https://www.python.org/downloads/)

## Architecture
![](https://docs.vllm.ai/en/latest/_images/llm_engine.excalidraw.png)

## References
- [vLLM](https://docs.vllm.ai/en/stable/)
- [vLLM GitHub](https://github.com/vllm-project/vllm)
- [vLLM 中文站](https://vllm.hyper.ai/)
- [vLLM 使用 pip 安装](https://vllm.hyper.ai/docs/getting-started/installation)
- [vLLM 使用 Docker 进行部署](https://vllm.hyper.ai/docs/serving/deploying-with-docker)
- [vLLM Architecture Overview](https://docs.vllm.ai/en/stable/design/arch_overview.html)
- [vLLM 引擎参数](https://vllm.hyper.ai/docs/models/engine-arguments)