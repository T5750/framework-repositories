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
- `PayServiceAccountDubboProvider`
- `PayServiceAccountingDubboProvider`
- `PayServiceMessageDubboProvider`
- `PayServiceNotifyDubboProvider`
- `PayServiceTradeDubboProvider`
- `PayServiceUserDubboProvider`

## app
- `PayAppMessageTask`
- `PayAppNotify`
- `PayAppQueue`

## war
Module | HTTP Port | Application Context
----|----|------
`pay-web-boss` | `8081` | `/pay-web-boss` 
`pay-web-gateway` | `8082` | `/pay-web-gateway` 
`pay-web-message` | `8084` | `/pay-web-message` 
`pay-web-sample-shop` | `8083` | `/pay-web-sample-shop` 

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