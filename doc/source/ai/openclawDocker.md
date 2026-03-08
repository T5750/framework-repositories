# OpenClaw Docker

Your own personal AI assistant. Any OS. Any Platform. The lobster way.

## 安装
```sh
curl -fsSL https://openclaw.ai/install.sh | bash
```
Windows（PowerShell）：
```sh
iwr -useb https://openclaw.ai/install.ps1 | iex
```

## 安装后
- 运行新手引导：`openclaw onboard --install-daemon`
- 快速检查：`openclaw doctor`
- 检查 Gateway 网关健康状态：`openclaw status` + `openclaw health`
- 打开仪表板：`openclaw dashboard`

## Docker
```sh
./docker-setup.sh
```
[http://localhost:18789/](http://localhost:18789/)

## Runtime Environment
- [TypeScript](https://www.typescriptlang.org/)
- [Node.js](https://nodejs.org/en/download)

## References
- [OpenClaw](https://openclaw.ai/)
- [OpenClaw 中文](https://docs.openclaw.ai/zh-CN)
- [OpenClaw GitHub](https://github.com/openclaw/openclaw)
- [OpenClaw 安装](https://docs.openclaw.ai/zh-CN/install)
- [OpenClaw Docker](https://docs.openclaw.ai/zh-CN/install/docker)