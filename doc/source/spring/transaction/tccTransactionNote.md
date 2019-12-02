# TCC Transaction Note

Try: 尝试执行业务

    完成所有业务检查（一致性）

    预留必须业务资源（准隔离性）

Confirm: 确认执行业务

    真正执行业务

    不作任何业务检查

    只使用Try阶段预留的业务资源

    Confirm操作满足幂等性

Cancel: 取消执行业务

    释放Try阶段预留的业务资源

    Cancel操作满足幂等性

- tcc-transaction不和底层使用的RPC框架耦合
- 在底层RPC框架为Dubbo情况下，可利用`tcc-transaction-dubbo-*.jar`提供的便利，方便应用使用tcc-transaction
- 示例演示在下完订单后，使用红包帐户和资金帐户来付款，红包帐户服务`RedPacketTradeOrderService`和资金帐户服务`CapitalTradeOrderService`在不同的系统中。
- 下完订单后，订单状态为`DRAFT`，在TCC事务中Try阶段，订单支付服务将订单状态变成`PAYING`，同时远程调用红包帐户服务和资金帐户服务，将付款方的余额减掉（预留业务资源）。
- 如果在Try阶段，任何一个服务失败，tcc-transaction将自动调用这些服务对应的`cancel`方法，订单支付服务将订单状态变成`PAY_FAILED`，同时远程调用红包帐户服务和资金帐户服务，将付款方余额减掉的部分增加回去。
- 如果Try阶段正常完成，则进入Confirm阶段（tcc-transaction自动调用），订单支付服务将订单状态变成`CONFIRMED`，同时远程调用红包帐户服务和资金帐户服务对应的`confirm`方法，将收款方的余额增加。
- 特别说明下，由于是示例，在`confirm`和`cancel`方法中没有实现幂等性，如果在真实项目中使用，需要保证`confirm`和`cancel`方法的幂等性。

## References
- [tcc-transaction 1.1.x](https://github.com/changmingxie/tcc-transaction/tree/master)
- [使用指南1.1.x](https://github.com/changmingxie/tcc-transaction/wiki/%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%971.1.x)