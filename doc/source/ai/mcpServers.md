# MCP Servers

## mangooer/mysql-mcp-server-sse
基于MCP框架的MySQL查询服务器，支持通过SSE协议进行实时数据库操作

### Docker
```sh
docker run -d \
  --name mysql-mcp-server-sse \
  -e HOST=0.0.0.0 \
  -e PORT=3000 \
  -e MYSQL_HOST=your_mysql_host \
  -e MYSQL_PORT=3306 \
  -e MYSQL_USER=your_mysql_user \
  -e MYSQL_PASSWORD=your_mysql_password \
  -e MYSQL_DATABASE=your_database \
  -p 3000:3000 \
  mangooer/mysql-mcp-server-sse
```
Default endpoint: [http://127.0.0.1:3000/sse](http://127.0.0.1:3000/sse)

## References
- [MCP Servers](https://mcp.so/)
- [mangooer/mysql-mcp-server-sse](https://mcp.so/server/mysql-mcp-server-sse/mangooer?tab=content)
- [mangooer/mysql-mcp-server-sse GitHub](https://github.com/mangooer/mysql-mcp-server-sse)