# Ollama Docker

Get up and running with Llama 3, Mistral, Gemma, and other large language models.

让更多人以最简单快速的方式在本地把大模型跑起来

## Docker
### CPU only
```sh
docker run -d -v ollama:/root/.ollama -p 11434:11434 --name ollama ollama/ollama
```

### Nvidia GPU
Install the [NVIDIA Container Toolkit](https://docs.nvidia.com/datacenter/cloud-native/container-toolkit/latest/install-guide.html#installation).
```sh
# Configure Docker to use Nvidia driver
sudo nvidia-ctk runtime configure --runtime=docker
sudo systemctl restart docker
docker run -d --gpus=all -v ollama:/root/.ollama -p 11434:11434 --name ollama ollama/ollama
```

### AMD GPU
```sh
docker run -d --device /dev/kfd --device /dev/dri -v ollama:/root/.ollama -p 11434:11434 --name ollama ollama/ollama:rocm
```

### Run model locally
```sh
docker exec -it ollama ollama run llama3
```

## Try different models
More models can be found on the [Ollama library](https://ollama.com/library).

### Qwen
Qwen 1.5 is a series of large language models by Alibaba
```sh
docker exec -it ollama ollama run qwen:0.5b
ollama pull qwen:0.5b-chat
ollama pull qwen2.5:0.5b
```

### DeepSeek
DeepSeek's first-generation of reasoning models with comparable performance to OpenAI-o1, including six dense models distilled from DeepSeek-R1 based on Llama and Qwen.
```sh
docker exec -it ollama ollama run deepseek-r1:1.5b
ollama pull deepseek-r1:32b
```

测试功能
1. 智能客服，例如：如何学习人工智能？
2. 内容创作，例如：请为我撰写一篇介绍沙县小吃的宣传文案
3. 编程辅助，例如：用Python绘制一个柱状图
4. 教育辅助，例如：解释牛顿第二定律

导出模型
```sh
ollama list
ollama show --modelfile deepseek-r1:1.5b
# FROM /root/.ollama/models/blobs/sha256-aabd4debf0c8f08881923f2c25fc0fdeed24435271c2b3e92c4af36704040dbc
docker cp ollama:/root/.ollama/models/blobs/sha256-aabd4debf0c8f08881923f2c25fc0fdeed24435271c2b3e92c4af36704040dbc ./deepseek-r1-1.5b.gguf
```

### nomic-embed-text
A high-performing open embedding model with a large token context window.
```sh
ollama pull nomic-embed-text
```
REST API
```sh
curl http://localhost:11434/api/embeddings -d '{
  "model": "nomic-embed-text",
  "prompt": "The sky is blue because of Rayleigh scattering"
}'
```
Python library
```sh
ollama.embeddings(model='nomic-embed-text', prompt='The sky is blue because of rayleigh scattering')
```
Javascript library
```sh
ollama.embeddings({ model: 'nomic-embed-text', prompt: 'The sky is blue because of rayleigh scattering' })
```

## REST API
### Generate a completion
```
POST /api/generate
# Examples
curl http://localhost:11434/api/generate -d '{
  "model": "llama3.2",
  "prompt": "Why is the sky blue?"
}'
```

### Generate a chat completion
```
POST /api/chat
```

### List Local Models
```
GET /api/tags
# Examples
curl http://localhost:11434/api/tags
```

### Generate Embeddings
```
POST /api/embed
```

### Version
```
GET /api/version
# Examples
curl http://localhost:11434/api/version
```

## Customize a model
### Import from GGUF
1. Create a file named `Modelfile`, with a `FROM` instruction with the local filepath to the model you want to import.
   ```
   FROM ./vicuna-33b.Q4_0.gguf
   ```
2. Create the model in Ollama
   ```
   ollama create example -f Modelfile
   ```
3. Run the model
   ```
   ollama run example
   ```

### Import from PyTorch or Safetensors
See the [guide](https://github.com/ollama/ollama/blob/main/docs/import.md) on importing models for more information.

### Customize a prompt
Models from the Ollama library can be customized with a prompt. For example, to customize the `llama3` model:
```
ollama pull llama3
```

Create a `Modelfile`:
```
FROM llama3

# set the temperature to 1 [higher is more creative, lower is more coherent]
PARAMETER temperature 1

# set the system message
SYSTEM """
You are Mario from Super Mario Bros. Answer as Mario, the assistant, only.
"""
```

Next, create and run the model:
```
ollama create mario -f ./Modelfile
ollama run mario
>>> hi
Hello! It's your friend Mario.
```

For more examples, see the [examples](https://github.com/ollama/ollama/blob/main/examples) directory. For more information on working with a Modelfile, see the [Modelfile](https://github.com/ollama/ollama/blob/main/docs/modelfile.md) documentation.

## Runtime Environment
- [Go](https://golang.org/)

## References
- [Ollama](https://ollama.com/)
- [Ollama GitHub](https://github.com/ollama/ollama)
- [Ollama Docker](https://hub.docker.com/r/ollama/ollama)
- [Ollama API](https://github.com/ollama/ollama/blob/main/docs/api.md)
- [Qwen Ollama](https://qwen.readthedocs.io/zh-cn/latest/run_locally/ollama.html)
- [Qwen1.5 GitHub](https://github.com/QwenLM/Qwen1.5)