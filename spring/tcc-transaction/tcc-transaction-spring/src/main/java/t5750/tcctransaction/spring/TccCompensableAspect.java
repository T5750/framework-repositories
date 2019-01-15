package t5750.tcctransaction.spring;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;

import t5750.tcctransaction.interceptor.CompensableTransactionInterceptor;

/**
 * TCC补偿切面（切面是通知和切点的结合）. 拦截带@Compensable注解的可补偿事务方法.
 */
@Aspect
public class TccCompensableAspect implements Ordered {
	static final Logger LOG = Logger.getLogger(TccCompensableAspect.class
			.getSimpleName());
	/**
	 * 通知顺序(默认：最高优先级). 在“进入”连接点的情况下，最高优先级的通知会先执行（所以给定的两个前置通知中，优先级高的那个会先执行）。
	 * 在“退出”连接点的情况下，最高优先级的通知会最后执行。 当定义在不同的切面里的两个通知都需要在一个相同的连接点中运行，
	 * 那么除非你指定，否则执行的顺序是未知的，你可以通过指定优先级来控制执行顺序。
	 */
	private int order = Ordered.HIGHEST_PRECEDENCE; // 最高优先级（值较低的那个有更高的优先级）
	/**
	 * 可补偿事务拦截器
	 */
	private CompensableTransactionInterceptor compensableTransactionInterceptor;

	/**
	 * 定义切入点（包含切入点表达式和切点签名，切点用于准确定位应该在什么地方应用切面的通知，切点可以被切面内的所有通知元素引用）.
	 */
	@Pointcut("@annotation(t5750.tcctransaction.Compensable)")
	public void compensableService() {
	}

	/**
	 * 定义环绕通知（在一个方法执行之前和执行之后运行，第一个参数必须是
	 * ProceedingJoinPoint类型，pjp将包含切点拦截的方法的参数信息）
	 * 
	 * @param pjp
	 * @throws Throwable
	 */
	@Around("compensableService()")
	public Object interceptCompensableMethod(ProceedingJoinPoint pjp)
			throws Throwable {
		LOG.debug("==>interceptCompensableMethod");
		return compensableTransactionInterceptor
				.interceptCompensableMethod(pjp);
	}

	@Override
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * 设置可补偿事务拦截器.
	 * 
	 * @param compensableTransactionInterceptor
	 */
	public void setCompensableTransactionInterceptor(
			CompensableTransactionInterceptor compensableTransactionInterceptor) {
		this.compensableTransactionInterceptor = compensableTransactionInterceptor;
	}
}