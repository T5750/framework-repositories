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
[http://127.0.0.1:18789/](http://127.0.0.1:18789/)

### 设备配对（首次连接）
disconnected (1008): pairing required
```sh
docker exec openclaw-openclaw-gateway-1 bash -c "echo \$OPENCLAW_GATEWAY_TOKEN"
vi ~/.openclaw/openclaw.json # 替换gateway.auth.token
docker compose exec -T openclaw-gateway node dist/index.js devices list
docker compose exec -T openclaw-gateway node dist/index.js devices approve xxx
docker compose exec -T openclaw-gateway node dist/index.js status
docker restart openclaw-openclaw-gateway-1
```

## 配置目录
```
~/.openclaw/
├── openclaw.json                 # 主配置文件（JSON/JSON5）
├── workspace/                    # 你的 AI “灵魂”文件夹（推荐 git 版本控制）
│   ├── SOUL.md                   # 人格设定（语气、风格）
│   ├── USER.md                   # 你的个人信息（让 AI 更懂你）
│   ├── MEMORY.md                 # 长期记忆（手动可编辑）
│   ├── IDENTITY.md               # Agent 名称、形象
│   ├── AGENTS.md                 # 多 Agent 路由规则
│   ├── BOOT.md                   # 启动提示词
│   ├── HEARTBEAT.md              # 每日检查清单
│   └── skills/                   # 已安装技能（每个技能一个子文件夹）
├── agents/<cid>/                 # 每个 Agent 的独立状态
├── memory/<cid>.sqlite           # 向量记忆库
├── credentials/                  # API Key、OAuth（旧版）
├── skills/                       # 全局技能包
└── secrets.json                  # 加密凭证（可选）
```

## 模型 CLI
在聊天中切换模型
```sh
/model
/model status
```

## Runtime Environment
- [TypeScript](https://www.typescriptlang.org/)
- [Node.js](https://nodejs.org/en/download)

## References
- [OpenClaw](https://openclaw.ai/)
- [OpenClaw 中文](https://docs.openclaw.ai/zh-CN)
- [OpenClaw GitHub](https://github.com/openclaw/openclaw)
- [OpenClaw 安装](https://docs.openclaw.ai/zh-CN/install)
- [OpenClaw Docker](https://docs.openclaw.ai/zh-CN/install/docker)
- [OpenClaw 控制 UI](https://docs.openclaw.ai/zh-CN/web/control-ui)
- [OpenClaw 模型 CLI](https://docs.openclaw.ai/zh-CN/concepts/models)
- [OpenClaw Ollama](https://docs.openclaw.ai/zh-CN/providers/ollama)
- [OpenClaw 配置目录](https://www.runoob.com/ai-agent/openclaw-setup.html)