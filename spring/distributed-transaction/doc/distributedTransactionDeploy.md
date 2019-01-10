# Distributed Transaction

## databases
- `doc\db\pay_dubbo_*.sql`

## pay-common-config
- `jdbc.properties`
- `mq_config.properties`
- `public_system.properties`
- `alipay_config.properties`
- `weixinpay_config.properties`

## service
Service | Dubbo Port 
----|----
`PayServiceAccount` | 20881
`PayServiceAccounting` | 20882
`PayServiceMessage` | 20888
`PayServiceNotify` | 20883
`PayServiceTrade` | 20886
`PayServiceUser` | 20887

## app
- `PayAppMessageTask`
- `PayAppNotify`
- `PayAppQueue`

## war
Module | HTTP Port | Application Context | Url
----|----|----|----
`pay-web-boss` | 8081 | `/pay-web-boss` | [http://localhost:8081/pay-web-boss](http://localhost:8081/pay-web-boss)
`pay-web-gateway` | **8082** | `/pay-web-gateway` | 
`pay-web-message` | 8084 | `/pay-web-message` | [http://localhost:8084/pay-web-message](http://localhost:8084/pay-web-message)
`pay-web-sample-shop` | **8083** | `/pay-web-sample-shop` | [http://localhost:8083/pay-web-sample-shop](http://localhost:8083/pay-web-sample-shop)
`incubator-dubbo-ops` | 8080 |  | [http://localhost:8080](http://localhost:8080)

## Tips
### pay-web-message/pay-web-boss
Optimized files:
```
pay-web-message\src\main\webapp\jsp\common\dwz.jsp
pay-web-message\src\main\webapp\common
pay-web-message\src\main\webapp\dwz
```

### pay-web-gateway/pay-web-sample-shop
Optimized files:
```
pay-web-gateway\src\main\webapp\pay_files
```
Deleted files:
```
pay-web-message\src\main\webapp\js
```

### pay-service-trade-api/pay-web-sample-shop
Merged utils:
```
pay-service-trade-api\src\main\java\t5750\pay\utils\httpclient\*
pay-service-trade-api\src\main\java\t5750\pay\utils\AlipayConfigUtil
```