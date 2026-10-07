# ToughRADIUS Docker

ToughRADIUS 是一款使用 Go 语言编写、功能强大的开源 RADIUS 服务器，面向 ISP、 企业网络与运营商场景。

每个身份都应被确认，每段连接都应可追溯。

## 服务模型

| 服务             | 协议 / 端口          | 用途                                 |
| ---------------- | -------------------- | ------------------------------------ |
| Web / Admin API  | HTTP，TCP `1816`     | 管理界面与 REST API                  |
| RADIUS 认证      | UDP `1812`           | 认证                                 |
| RADIUS 计费      | UDP `1813`           | 计费                                 |
| RadSec           | TLS over TCP `2083`  | 加密的 RADIUS 传输                    |

## 一段话理解 AAA
RADIUS（Remote Authentication Dial In User Service）协议让网络设备向中心服务器提出三个问题：*这个用户是谁*（**认证 Authentication**）、*他能做什么*（**授权 Authorization**）、*他用了多少*（**计费 Accounting**）。ToughRADIUS 对三者全部作答：校验凭据、在 `Access-Accept` 中下发授权属性（IP 地址、带宽、VLAN、会话限制），并从计费报文中记录用量。

## 一次认证请求的流转
```
NAS ──Access-Request──▶ UDP 1812
        │
        ▼
  goroutine 池（ants，TOUGHRADIUS_RADIUS_POOL，默认 1024）
        │
        ▼
  1. NAS 查找 ─ 源 IP / identifier 必须匹配已登记的 NAS 记录
  2. 厂商解析器 ─ 提取 MAC（Calling-Station-Id）与 VLAN（NAS-Port-Id）
       · 华为 / H3C / 中兴解析器可提取 VLAN ID
       · 其余厂商走默认解析器（仅 MAC）
  3. 凭据校验 ─ PAP / CHAP / MS-CHAPv2，或 EAP 状态机
  4. 检查器 ─ 账号状态、过期时间、MAC 绑定、VLAN 绑定、并发数（active_num）
  5. Accept 增强器 ─ 标准属性（Session-Timeout、地址池、静态 IP、IPv6）
     以及按 NAS 厂商代码选择的厂商属性
        │
        ▼
NAS ◀─Access-Accept / Access-Reject──
```

## Docker
```sh
docker run -d --name toughradius \
  -p 1816:1816 -p 1812:1812/udp -p 1813:1813/udp -p 2083:2083 \
  -v toughradius-data:/var/toughradius \
  talkincode/toughradius:latest -c /etc/toughradius.yml
```
- [http://localhost:1816](http://localhost:1816)
- Bootstrap Admin Account: admin/Password(`{workdir}/private/admin-bootstrap-password`)

## 配置
`vi /etc/toughradius.yml`
```
system:
  appid: ToughRADIUS
  location: Asia/Shanghai
  workdir: /var/toughradius     # 数据/日志/证书都在这里
  debug: false

web:
  host: 0.0.0.0
  port: 1816
  secret: change-me-to-a-long-random-string   # JWT 签名密钥

database:
  type: sqlite                  # 或 postgres（需配 host/port/user/passwd）
  name: toughradius.db          # 存放于 {workdir}/data/ 下

radiusd:
  enabled: true
  host: 0.0.0.0
  auth_port: 1812
  acct_port: 1813
  radsec_port: 2083
  debug: true                   # 输出完整报文转储；生产环境建议关闭

logger:
  mode: production
  file_enable: true
  filename: /var/toughradius/toughradius.log
```

## Runtime Environment
- [Go](https://golang.org/)
- [TypeScript](https://www.typescriptlang.org/)

## References
- [ToughRADIUS](https://www.toughradius.net/)
- [ToughRADIUS GitHub](https://github.com/talkincode/toughradius)
- [ToughRADIUS 概述](https://www.toughradius.net/zh/overview.html)
- [ToughRADIUS 核心术语与概念](https://www.toughradius.net/zh/concepts.html)
- [ToughRADIUS 快速开始](https://www.toughradius.net/zh/quickstart.html)