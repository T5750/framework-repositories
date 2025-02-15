# AnythingLLM Docker

The last chatbot you will ever need

## Docker
```sh
export STORAGE_LOCATION=$HOME/anythingllm && \
mkdir -p $STORAGE_LOCATION && \
touch "$STORAGE_LOCATION/.env" && \
docker run -d -p 3001:3001 \
--cap-add SYS_ADMIN \
-v ${STORAGE_LOCATION}:/app/server/storage \
-v ${STORAGE_LOCATION}/.env:/app/server/.env \
-e STORAGE_DIR="/app/server/storage" \
mintplexlabs/anythingllm
```
[http://localhost:3001/](http://localhost:3001/)

## Docker vs Desktop Version
- AnythingLLM Desktop: One-click install, Built-in LLM provider
- AnythingLLM Docker: Multi-user support, Emeddable chat widgets, White-labeling, Password protection, Invite new users to instance, User management, Workspace access management

## Runtime Environment
- [Node.js](https://nodejs.org/en/download)

## Screenshots
![](https://framerusercontent.com/images/Tp02ryw6UgcdmNoOxHJ8Pm1vo.png)

![](https://docs.useanything.com/_next/image?url=%2Fimages%2Ffeatures%2Fai-agents%2Fai-agent.png&w=3840&q=100)

![](https://docs.useanything.com/_next/image?url=%2Fimages%2Ffeatures%2Fprivacy-and-data-handling%2Fprivacy-and-data.png&w=3840&q=100)

## References
- [AnythingLLM](https://useanything.com/)
- [AnythingLLM GitHub](https://github.com/Mintplex-Labs/anything-llm)
- [AnythingLLM Docker](https://docs.useanything.com/installation-docker/local-docker)
- [AnythingLLM Installation Overview](https://docs.useanything.com/installation-docker/overview)