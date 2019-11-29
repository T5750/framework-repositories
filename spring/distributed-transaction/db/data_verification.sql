## 测试数据：按交易额收商户费0.8%的手续费，每笔支付订单金额10元（相当于每单收8分钱手续费）
## 订单系统：成功订单、失败订单、等待支付状态订单、其他状态……
## 资金账户：每笔成功支付订单进账9.92元
## 积分账户：每笔成功支付订单进账10个积分（积分总数=成功支付订单数*10）
## 会计系统：每笔成功支付订单记录一条会计原始凭证记录（记录数=成功支付订单数）
## 商户通知：收到支付结果，就会向商户发送通知

################################################################################################
##=======订单库：pay_dubbo_order
################################################################################################
## 订单总笔数：
SELECT COUNT(id) FROM pay_dubbo_order.rp_trade_payment_order; ## 1008

## 支付成功的订单
SELECT COUNT(id) FROM pay_dubbo_order.rp_trade_payment_order WHERE STATUS='SUCCESS'; ## 1000

## 支付中状态的订单数（模拟支付进程关闭是会产生）
SELECT COUNT(id) FROM pay_dubbo_order.rp_trade_payment_order WHERE STATUS='WAITING_PAYMENT'; ## 8

################################################################################################
##=======资金账户库：pay_dubbo_account
################################################################################################
## 账户余额总数（总数/9.92=成功笔数）
SELECT SUM(balance) FROM pay_dubbo_account.rp_account; ## 9920.000000

## 账户变动历史记录总数
SELECT COUNT(id) FROM pay_dubbo_account.rp_account_history; ## 1000

## 成功支付订单对应的账户变动历史记录数
SELECT COUNT(id) FROM pay_dubbo_account.rp_account_history WHERE fund_direction='ADD' AND STATUS='CONFORM'; ## 1000

################################################################################################
##=======积分账户库：pay_dubbo_point
################################################################################################
## 积分余额总和
SELECT SUM(balance) FROM pay_dubbo_point.rp_point_account; ## 10000

## 成功支付订单对应的积分账户变动历史记录数
SELECT COUNT(id) FROM pay_dubbo_point.rp_point_account_history WHERE fund_direction='ADD' AND STATUS='CONFORM'; ## 1000

################################################################################################
##=======会计系统库：pay_dubbo_accounting
################################################################################################
## 会计原始凭证数
SELECT COUNT(id) FROM pay_dubbo_accounting.rp_accounting_voucher; ## 1000

################################################################################################
##=======基础库：pay_dubbo_base
################################################################################################
## 商户通知记录数
SELECT COUNT(id) FROM pay_dubbo_base.rp_notify_record; ## 970

## 商户通知记录日志
SELECT COUNT(id) FROM pay_dubbo_base.rp_notify_record_log; ## 970
