package t5750.tcctransaction.spring;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;

import t5750.tcctransaction.interceptor.ResourceCoordinatorInterceptor;

/**
 * TCC事务上下文切面。 拦截带@Compensable注解或包含TransactionContext参数的可补偿事务方法.
 */
@Aspect
public class TccTransactionContextAspect implements Ordered {
	static final Logger LOG = Logger
			.getLogger(TccTransactionContextAspect.class.getSimpleName());
	/**
	 * 通知顺序(默认：最高优先级). 在“进入”连接点的情况下，最高优先级的通知会先执行（所以给定的两个前置通知中，优先级高的那个会先执行）。
	 * 在“退出”连接点的情况下，最高优先级的通知会最后执行。 当定义在不同的切面里的两个通知都需要在一个相同的连接点中运行，
	 * 那么除非你指定，否则执行的顺序是未知的，你可以通过指定优先级来控制执行顺序。
	 */
	private int order = Ordered.HIGHEST_PRECEDENCE + 1; // 最高优先级+1（值较低的那个有更高的优先级）
	/**
	 * 资源协调拦截器
	 */
	private ResourceCoordinatorInterceptor resourceCoordinatorInterceptor;

	/**
	 * 定义切入点（包含切入点表达式和切点签名）.
	 */
	@Pointcut("execution(public * *(t5750.tcctransaction.api.TransactionContext,..))||@annotation(t5750.tcctransaction.Compensable)")
	public void transactionContextCall() {
	}

	/**
	 * 定义环绕通知（在一个方法执行之前和执行之后运行，第一个参数必须是
	 * ProceedingJoinPoint类型，方法的调用者得到的返回值就是环绕通知返回的值）
	 * 
	 * @param pjp
	 * @throws Throwable
	 */
	@Around("transactionContextCall()")
	public Object interceptTransactionContextMethod(ProceedingJoinPoint pjp)
			throws Throwable {
		LOG.debug("==>interceptTransactionContextMethod(ProceedingJoinPoint pjp)");
		return resourceCoordinatorInterceptor
				.interceptTransactionContextMethod(pjp);
	}

	@Override
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * 设置资源协调拦截器
	 * 
	 * @param resourceCoordinatorInterceptor
	 */
	public void setResourceCoordinatorInterceptor(
			ResourceCoordinatorInterceptor resourceCoordinatorInterceptor) {
		this.resourceCoordinatorInterceptor = resourceCoordinatorInterceptor;
	}
}
/*
 * ========================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com） 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ========================================================================
 */