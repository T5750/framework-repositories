# Netty网摘笔记

## Netty实现WebSocket聊天功能
WebSocket通过“Upgrade handshake（升级握手）”从标准的HTTP或HTTPS协议转为WebSocket。因此，使用WebSocket的应用程序将始终以HTTP/S开始，然后进行升级。在什么时候发生这种情况取决于具体的应用;它可以是在启动时，或当一个特定的URL被请求时。

在我们的应用中，当URL请求以“/ws”结束时，我们才升级协议为WebSocket。否则，服务器将使用基本的HTTP/S。一旦升级连接将使用的WebSocket传输所有数据。

整个服务器逻辑如下：

![netty-websocket-chat-server-min](https://www.wailian.work/images/2018/12/04/netty-websocket-chat-server-min.jpg)

1. 客户端/用户连接到服务器并加入聊天
1. HTTP请求页面或WebSocket升级握手
1. 服务器处理所有客户端/用户
1. 响应URI“/”的请求，转到默认html页面
1. 如果访问的是URI“/ws”，处理WebSocket升级握手
1. 升级握手完成后，通过WebSocket发送聊天消息

### 示例
- `WebSocketChatServer`
- [http://localhost:8080](http://localhost:8080)

## References
- [Netty实现WebSocket聊天功能](https://waylau.com/netty-websocket-chat/)
- [netty-4-user-guide-demos](https://github.com/waylau/netty-4-user-guide-demos)