# TCC Transaction Deploy

## databases
- `spring\tcc-transaction\db\tcc*.sql`

## config
- `jdbc.properties`
- `sample-dubbo-*.properties`
- `tcc-transaction-server.properties`
- `tcc-transaction-unit-test.xml`
- `redis-domain-key-prefix.properties`
- `jdbc-domain-suffix.properties`

## service

Service | Dubbo Port 
----|----
`TccDubboCapital` | 2881
`TccDubboRedpacket` | 2880
`TccDubboOrder` | 2882

## war

Module | HTTP Port | Application Context | Url
---|---|---|------
`tcc-transaction-server` | 8085 | `/tcc-transaction-server` | [http://localhost:8085/tcc-transaction-server/management](http://localhost:8085/tcc-transaction-server/management)
`tcc-transaction-dubbo-web-trade` | 8086 | `/` | [http://localhost:8086/](http://localhost:8086/)
`dubbo-admin` | 8080 | `/` | [http://localhost:8080](http://localhost:8080)

## Tests
- `TransferServiceTest`
- `PerformanceTest`

## Tips
### tcc-transaction-server
Deleted files:
```
tcc-transaction-server\src\main\config\*
tcc-transaction-server\src\main\webapp\static\css\
tcc-transaction-server\src\main\webapp\static\js\jquery
tcc-transaction-server\src\main\webapp\index.jsp
```

## References
- [tcc-transaction 1.1.5](https://github.com/changmingxie/tcc-transaction/tree/master)