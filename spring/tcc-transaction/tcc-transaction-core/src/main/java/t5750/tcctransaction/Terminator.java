package t5750.tcctransaction;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import t5750.tcctransaction.support.BeanFactoryAdapter;
import t5750.tcctransaction.utils.StringUtils;

/**
 * 终结者.
 */
public class Terminator implements Serializable {
	static final Logger LOG = Logger
			.getLogger(Terminator.class.getSimpleName());
	private static final long serialVersionUID = -164958655471605778L;
	/**
	 * 确认调用的上下文.
	 */
	private InvocationContext confirmInvocationContext;
	/**
	 * 取消调用的上下文.
	 */
	private InvocationContext cancelInvocationContext;

	public Terminator() {
	}

	/**
	 * 构建终结者对像.
	 * 
	 * @param confirmInvocationContext
	 * @param cancelInvocationContext
	 */
	public Terminator(InvocationContext confirmInvocationContext,
			InvocationContext cancelInvocationContext) {
		this.confirmInvocationContext = confirmInvocationContext;
		this.cancelInvocationContext = cancelInvocationContext;
	}

	/**
	 * 提交参与者事务（在Participant中调用）.
	 */
	public void commit() {
		LOG.debug("==>Terminator commit invoke");
		invoke(confirmInvocationContext);
	}

	/**
	 * 回滚参与者事务（在Participant中调用）.
	 */
	public void rollback() {
		LOG.debug("==>Terminator rollback invoke");
		invoke(cancelInvocationContext);
	}

	/**
	 * 根据调用上下文，获取目标方法并执行方法调用.
	 * 
	 * @param invocationContext
	 * @return
	 */
	private Object invoke(InvocationContext invocationContext) {
		if (StringUtils.isNotEmpty(invocationContext.getMethodName())) {
			LOG.debug("==>Terminator invoke "
					+ invocationContext.getTargetClass().getName() + "."
					+ invocationContext.getMethodName());
			try {
				Object target = BeanFactoryAdapter.getBean(invocationContext
						.getTargetClass());
				if (target == null
						&& !invocationContext.getTargetClass().isInterface()) {
					target = invocationContext.getTargetClass().newInstance();
				}
				Method method = null;
				// 找到要调用的目标方法
				method = target.getClass().getMethod(
						invocationContext.getMethodName(),
						invocationContext.getParameterTypes());
				// 调用服务方法，被再次被TccTransactionContextAspect和ResourceCoordinatorInterceptor拦截，但因为事务状态已经不再是TRYING了，所以直接执行远程服务
				return method.invoke(target, invocationContext.getArgs()); // 调用服务方法
			} catch (Exception e) {
				throw new SystemException(e);
			}
		}
		return null;
	}
}
