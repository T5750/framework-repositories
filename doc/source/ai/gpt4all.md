# GPT4All

A free-to-use, locally running, privacy-aware chatbot. No GPU or internet required.

## Download Desktop Chat Client
- [Windows Installer](https://gpt4all.io/installers/gpt4all-installer-win64.exe)
- [OSX Installer](https://gpt4all.io/installers/gpt4all-installer-darwin.dmg)
- [Ubuntu Installer](https://gpt4all.io/installers/gpt4all-installer-linux.run)

## Models
- Llama 3 Instruct
- [hfl/llama-3-chinese-8b-instruct-v3-gguf](https://hf-mirror.com/hfl/llama-3-chinese-8b-instruct-v3-gguf)
- [shenzhi-wang/Llama3-8B-Chinese-Chat-GGUF-8bit](https://hf-mirror.com/shenzhi-wang/Llama3-8B-Chinese-Chat-GGUF-8bit)

## GPT4All Python SDK
### Installation
```sh
pip install gpt4all
```

### Load LLM
```sh
from gpt4all import GPT4All
model = GPT4All("Meta-Llama-3-8B-Instruct.Q4_0.gguf") # downloads / loads a 4.66GB LLM
with model.chat_session():
    print(model.generate("How can I run LLMs efficiently on my laptop?", max_tokens=1024))
```

### Chat Session Generation
```sh
from gpt4all import GPT4All
model = GPT4All("Meta-Llama-3-8B-Instruct.Q4_0.gguf")
with model.chat_session():
    print(model.generate("quadratic formula"))
```

### Direct Generation
```sh
from gpt4all import GPT4All
model = GPT4All("Meta-Llama-3-8B-Instruct.Q4_0.gguf")
print(model.generate("quadratic formula"))
```

### Embeddings
```sh
from nomic import embed
embeddings = embed.text(["String 1", "String 2"], inference_mode="local")['embeddings']
print("Number of embeddings created:", len(embeddings))
print("Number of dimensions per embedding:", len(embeddings[0]))
```

## Tips
### GPT4All Python SDK
```
Failed to load llamamodel-mainline-cuda-avxonly.dll: LoadLibraryExW failed with error 0x7e
Failed to load llamamodel-mainline-cuda.dll: LoadLibraryExW failed with error 0x7e
```
[Python binding logs console errors when CUDA is not found, even when CPU is requested](https://github.com/nomic-ai/gpt4all/issues/2521)

## Runtime Environment
- C++

## Screenshots
![](https://gpt4all.io/landing.gif)

## References
- [GPT4All](https://gpt4all.io/)
- [GPT4All GitHub](https://github.com/nomic-ai/gpt4all)
- [GPT4All Documentation](https://docs.gpt4all.io/)
- [GPT4All Python SDK](https://docs.gpt4all.io/gpt4all_python/home.html)
- [GPT4All Python SDK Reference](https://docs.gpt4all.io/gpt4all_python/ref.html)