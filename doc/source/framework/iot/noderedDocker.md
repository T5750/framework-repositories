# Node-RED Docker

## Docker Compose
`nodered.yml`

[http://localhost:1880/](http://localhost:1880/)

### Security
`vi nodered_data/settings.js`
```
adminAuth: {
    type: "credentials",
    users: [{
        username: "admin",
        password: "$2a$08$zZWtXTja0fB1pzD4sHCMyOCMYz2Z6dNbM6tl8sJogENOMcxWV9DN.",
        permissions: "*"
    }]
}
```
User: admin / password

生成密码加密字串
`node -e "console.log(require('bcryptjs').hashSync(process.argv[1], 8));" your-password-here`

## References
- [Node-RED](https://hub.docker.com/r/nodered/node-red)
- [Node-RED Docker](https://github.com//node-red/node-red-docker)