# Flowise Docker

Low-code developer tool for building LLM Applications

## Docker
```sh
docker run -d -p 3000:3000 --name flowise -e FLOWISE_USERNAME=admin -e FLOWISE_PASSWORD=1234 flowiseai/flowise
```
[http://localhost:3000/](http://localhost:3000/)

### Env Variables
- DATABASE_PATH=/root/.flowise
- LOG_PATH=/root/.flowise/logs
- SECRETKEY_PATH=/root/.flowise
- BLOB_STORAGE_PATH=/root/.flowise/storage

## Runtime Environment
- [TypeScript](https://www.typescriptlang.org/)
- [Node.js](https://nodejs.org/en/download)

## Screenshots
![](https://docs.flowiseai.com/~gitbook/image?url=https%3A%2F%2F823733684-files.gitbook.io%2F%7E%2Ffiles%2Fv0%2Fb%2Fgitbook-x-prod.appspot.com%2Fo%2Fspaces%252F00tYLwhz5RyR7fJEhrWy%252Fuploads%252FK5NWsHkLAelZq9sBlY8x%252FFlowiseIntro.gif%3Falt%3Dmedia%26token%3Dea75ba7b-32fa-447d-8872-41fe5578fe1f&width=400&dpr=3&quality=100&sign=35a65986&sv=2)

## References
- [Flowise](https://flowiseai.com/)
- [Flowise GitHub](https://github.com/FlowiseAI/Flowise)
- [Flowise Get Started](https://docs.flowiseai.com/getting-started)
- [Flowise API Reference](https://docs.flowiseai.com/api-reference)
- [Flowise Environment Variables](https://docs.flowiseai.com/configuration/environment-variables)