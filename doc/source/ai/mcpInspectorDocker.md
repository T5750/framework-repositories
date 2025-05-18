# MCP Inspector Docker

MCP Inspector 是一个交互式开发者工具，用于测试和调试 MCP 服务器

## Docker
```sh
git clone https://github.com/boclifton-MSFT/McpInspectorDocker.git
docker build -t mcp-inspector .
#docker run -p 8080:6274 -p 9000:9000 mcp-inspector
docker run -d --name mcp-inspector -p 6274:6274 -p 6277:6277 -p 9000:9000 mcp-inspector
```
[http://localhost:6274/](http://localhost:6274/)

## Runtime Environment
- [TypeScript](https://www.typescriptlang.org/)

## Screenshots
![](https://mintlify.s3.us-west-1.amazonaws.com/promplate/images/mcp-inspector.png)

## References
- [MCP Inspector](https://modelcontextprotocol.io/docs/tools/inspector)
- [MCP Inspector GitHub](https://github.com/modelcontextprotocol/inspector)
- [MCP Inspector 教程](https://mcp-docs.cn/docs/tools/inspector)
- [boclifton-MSFT/McpInspectorDocker](https://github.com/boclifton-MSFT/McpInspectorDocker)