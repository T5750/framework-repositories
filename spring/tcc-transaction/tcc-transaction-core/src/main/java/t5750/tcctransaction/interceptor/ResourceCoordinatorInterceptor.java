package t5750.tcctransaction.interceptor;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import t5750.tcctransaction.*;
import t5750.tcctransaction.Terminator;
import t5750.tcctransaction.api.TransactionContext;
import t5750.tcctransaction.api.TransactionStatus;
import t5750.tcctransaction.api.TransactionXid;
import t5750.tcctransaction.common.MethodType;
import t5750.tcctransaction.support.TransactionConfigurator;
import t5750.tcctransaction.utils.CompensableMethodUtils;
import t5750.tcctransaction.utils.ReflectionUtils;

/**
 * 资源协调拦截器.
 */
public class ResourceCoordinatorInterceptor {
	static final Logger LOG = Logger
			.getLogger(ResourceCoordinatorInterceptor.class.getSimpleName());
	/**
	 * 事务配置器.
	 */
	private TransactionConfigurator transactionConfigurator;

	/**
	 * 设置事务配置器.
	 * 
	 * @param transactionConfigurator
	 */
	public void setTransactionConfigurator(
			TransactionConfigurator transactionConfigurator) {
		this.transactionConfigurator = transactionConfigurator;
	}

	/**
	 * 拦截事务上下文方法.
	 * 
	 * @param pjp
	 * @throws Throwable
	 */
	public Object interceptTransactionContextMethod(ProceedingJoinPoint pjp)
			throws Throwable {
		LOG.debug("==>interceptTransactionContextMethod(ProceedingJoinPoint pjp)");
		// 获取当前事务
		Transaction transaction = transactionConfigurator
				.getTransactionManager().getCurrentTransaction();
		// Trying(判断是否Try阶段的事务)
		if (transaction != null
				&& transaction.getStatus().equals(TransactionStatus.TRYING)) {
			LOG.debug("==>TransactionStatus:"
					+ transaction.getStatus().toString());
			// 从参数获取事务上下文
			TransactionContext transactionContext = CompensableMethodUtils
					.getTransactionContextFromArgs(pjp.getArgs());
			// 获取事务补偿注解
			Compensable compensable = getCompensable(pjp);
			// 计算方法类型
			MethodType methodType = CompensableMethodUtils.calculateMethodType(
					transactionContext, compensable != null ? true : false);
			LOG.debug("==>methodType:" + methodType.toString());
			switch (methodType) {
			case ROOT:
				generateAndEnlistRootParticipant(pjp); // 生成和登记根参与者
				break;
			case CONSUMER:
				generateAndEnlistConsumerParticipant(pjp); // 生成并登记消费者的参与者
				break;
			case PROVIDER:
				generateAndEnlistProviderParticipant(pjp); // 生成并登记服务提供者的参与者
				break;
			}
		}
		LOG.debug("==>pjp.proceed(pjp.getArgs())");
		return pjp.proceed(pjp.getArgs()); // 开始执行被拦截的方法，或进入下一个拦截器处理逻辑
	}

	/**
	 * 生成和登记根参与者.
	 * 
	 * @param pjp
	 * @return
	 */
	private Participant generateAndEnlistRootParticipant(ProceedingJoinPoint pjp) {
		LOG.debug("==>generateAndEnlistRootParticipant(ProceedingJoinPoint pjp)");
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		Compensable compensable = getCompensable(pjp);
		String confirmMethodName = compensable.confirmMethod(); // 确认方法
		String cancelMethodName = compensable.cancelMethod(); // 取消方法
		Transaction transaction = transactionConfigurator
				.getTransactionManager().getCurrentTransaction(); // 获取当前事务
		TransactionXid xid = new TransactionXid(transaction.getXid()
				.getGlobalTransactionId()); // 使用全局事务ID和新的分支事务限定符号，生成新的事务Xid
		LOG.debug("==>TransactionXid："
				+ TransactionXid.byteArrayToUUID(xid.getGlobalTransactionId())
						.toString()
				+ "|"
				+ TransactionXid.byteArrayToUUID(xid.getBranchQualifier())
						.toString());
		// 获取到目标类（最好做成独立的类）
		Class targetClass = ReflectionUtils.getDeclaringType(pjp.getTarget()
				.getClass(), method.getName(), method.getParameterTypes());
		// 构建确认方法的提交上下文（相同的参数）
		InvocationContext confirmInvocation = new InvocationContext(
				targetClass, confirmMethodName, method.getParameterTypes(),
				pjp.getArgs());
		// 构建取消方法的提交上下文（相同的参数）
		InvocationContext cancelInvocation = new InvocationContext(targetClass,
				cancelMethodName, method.getParameterTypes(), pjp.getArgs());
		// 构建参与者对像
		Participant participant = new Participant(xid, new Terminator(
				confirmInvocation, cancelInvocation));
		transaction.enlistParticipant(participant); // 加入参与者
		TransactionRepository transactionRepository = transactionConfigurator
				.getTransactionRepository();
		transactionRepository.update(transaction); // 更新事务信息（加入了事务参与者，包含了触发confirm或cancel方法的参数信息）
		return participant;
	}

	/**
	 * 生成并登记消费者的参与者
	 * 
	 * @param pjp
	 * @return
	 */
	private Participant generateAndEnlistConsumerParticipant(
			ProceedingJoinPoint pjp) {
		LOG.debug("==>generateAndEnlistConsumerParticipant(ProceedingJoinPoint pjp)");
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		Transaction transaction = transactionConfigurator
				.getTransactionManager().getCurrentTransaction(); // 获取当前事务
		TransactionXid xid = new TransactionXid(transaction.getXid()
				.getGlobalTransactionId()); // 使用全局事务ID和新的分支事务限定符号，生成新的事务Xid
		LOG.debug("==>TransactionXid："
				+ TransactionXid.byteArrayToUUID(xid.getGlobalTransactionId())
						.toString()
				+ "|"
				+ TransactionXid.byteArrayToUUID(xid.getBranchQualifier())
						.toString());
		// 注意！！！此处给服务接口的 TransactionContext 参数设值（新的事务分支ID），状态为当前transaction状态TRYING
		int position = CompensableMethodUtils
				.getTransactionContextParamPosition(((MethodSignature) pjp
						.getSignature()).getParameterTypes());
		// 给服务接口的TransactionContext参数设值
		pjp.getArgs()[position] = new TransactionContext(xid, transaction
				.getStatus().getId()); // 构建事务上下文
		Object[] tryArgs = pjp.getArgs(); // 获取服务接口参数
		Object[] confirmArgs = new Object[tryArgs.length]; // 确认提交参数
		Object[] cancelArgs = new Object[tryArgs.length]; // 取消提交参数
		System.arraycopy(tryArgs, 0, confirmArgs, 0, tryArgs.length); // 数组拷贝
		confirmArgs[position] = new TransactionContext(xid,
				TransactionStatus.CONFIRMING.getId()); // 构建事务确认上下文
		System.arraycopy(tryArgs, 0, cancelArgs, 0, tryArgs.length); // 数组拷贝
		cancelArgs[position] = new TransactionContext(xid,
				TransactionStatus.CANCELLING.getId()); // 构建事务取消上下文
		// targetClass 其实是在本地被引用的远程服务接口类
		Class targetClass = ReflectionUtils.getDeclaringType(pjp.getTarget()
				.getClass(), method.getName(), method.getParameterTypes());
		// 构建确认方法的提交上下文
		InvocationContext confirmInvocation = new InvocationContext(
				targetClass, method.getName(), method.getParameterTypes(),
				confirmArgs);
		// 构建取消方法的提交上下文
		InvocationContext cancelInvocation = new InvocationContext(targetClass,
				method.getName(), method.getParameterTypes(), cancelArgs);
		// 构建参与者对像
		Participant participant = new Participant(xid, new Terminator(
				confirmInvocation, cancelInvocation));
		transaction.enlistParticipant(participant); // 加入到参与者（包含消费者调用上下文信息）
		TransactionRepository transactionRepository = transactionConfigurator
				.getTransactionRepository();
		transactionRepository.update(transaction); // 更新事务（此时的transaction包含了根参与者的和消费者的参与者的事务上下文信息）
		return participant;
	}

	/**
	 * 生成并登记服务提供者的参与者
	 * 
	 * @param pjp
	 * @return
	 */
	private Participant generateAndEnlistProviderParticipant(
			ProceedingJoinPoint pjp) {
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		Compensable compensable = getCompensable(pjp);
		String confirmMethodName = compensable.confirmMethod();
		String cancelMethodName = compensable.cancelMethod();
		Transaction transaction = transactionConfigurator
				.getTransactionManager().getCurrentTransaction();
		TransactionXid xid = new TransactionXid(transaction.getXid()
				.getGlobalTransactionId()); // 使用全局事务ID和新的分支事务限定符号，生成新的事务Xid
		LOG.debug("==>TransactionXid："
				+ TransactionXid.byteArrayToUUID(xid.getGlobalTransactionId())
						.toString()
				+ "|"
				+ TransactionXid.byteArrayToUUID(xid.getBranchQualifier())
						.toString());
		// targetClass：服务提供者接口实现类
		Class targetClass = ReflectionUtils.getDeclaringType(pjp.getTarget()
				.getClass(), method.getName(), method.getParameterTypes());
		// 构建确认方法的提交上下文
		InvocationContext confirmInvocation = new InvocationContext(
				targetClass, confirmMethodName, method.getParameterTypes(),
				pjp.getArgs());
		// 构建取消方法的提交上下文
		InvocationContext cancelInvocation = new InvocationContext(targetClass,
				cancelMethodName, method.getParameterTypes(), pjp.getArgs());
		// 事务的ID不变，参与者的分支ID是新生成的
		Participant participant = new Participant(xid, new Terminator(
				confirmInvocation, cancelInvocation));
		transaction.enlistParticipant(participant);
		TransactionRepository transactionRepository = transactionConfigurator
				.getTransactionRepository();
		transactionRepository.update(transaction);
		return participant;
	}

	/**
	 * 根据切点，获取事务注解.
	 * 
	 * @param pjp
	 * @return
	 */
	private Compensable getCompensable(ProceedingJoinPoint pjp) {
		LOG.debug("==>getCompensable(ProceedingJoinPoint pjp)");
		MethodSignature signature = (MethodSignature) pjp.getSignature(); // 获取签名
		Method method = signature.getMethod(); // 获取方法
		Compensable compensable = method.getAnnotation(Compensable.class); // 获取注解
		if (compensable == null) {
			Method targetMethod = null;
			try {
				// 获取目标方法
				targetMethod = pjp
						.getTarget()
						.getClass()
						.getMethod(method.getName(), method.getParameterTypes());
				if (targetMethod != null) {
					compensable = targetMethod.getAnnotation(Compensable.class);
				}
			} catch (NoSuchMethodException e) {
				compensable = null;
			}
		}
		return compensable;
	}
}
