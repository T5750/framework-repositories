package t5750.tcctransactionboot.dubbo.context;

import java.lang.reflect.Method;

import org.mengyun.tcctransaction.api.TransactionContext;
import org.mengyun.tcctransaction.api.TransactionContextEditor;

import t5750.tcctransactionboot.dubbo.constants.TransactionContextConstants;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;

/**
 */
public class DubboTransactionContextEditor implements TransactionContextEditor {
	@Override
	public TransactionContext get(Object target, Method method, Object[] args) {
		String context = RpcContext.getContext().getAttachment(
				TransactionContextConstants.TRANSACTION_CONTEXT);
		if (StringUtils.isNotEmpty(context)) {
			return JSON.parseObject(context, TransactionContext.class);
		}
		return null;
	}

	@Override
	public void set(TransactionContext transactionContext, Object target,
			Method method, Object[] args) {
		RpcContext.getContext().setAttachment(
				TransactionContextConstants.TRANSACTION_CONTEXT,
				JSON.toJSONString(transactionContext));
	}
}
