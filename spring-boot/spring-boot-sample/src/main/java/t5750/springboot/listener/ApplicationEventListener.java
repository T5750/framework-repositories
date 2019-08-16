package t5750.springboot.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;

/**
 * springboot 生命周期<br/>
 * https://blog.csdn.net/ardo_pass/article/details/82897927
 */
public class ApplicationEventListener implements
		ApplicationListener<ApplicationEvent> {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		// 在这里可以监听到Spring Boot的生命周期
		// 初始化环境变量
		if (event instanceof ApplicationEnvironmentPreparedEvent) {
			logger.info("初始化环境变量");
		} else
		// 初始化完成
		if (event instanceof ApplicationPreparedEvent) {
			logger.info("初始化环境变量完成");
			System.out.println("Spring容器初始化");
			System.out.println("=====================================");
		} else
		// 应用刷新，当ApplicationContext初始化或者刷新时触发该事件。
		if (event instanceof ContextRefreshedEvent) {
			logger.info("应用刷新");
			System.out.println("Spring容器初始化完毕");
			System.out.println("=====================================");
		} else
		// 应用已启动完成
		if (event instanceof ApplicationReadyEvent) {
			logger.info("应用已启动完成");
		} else
		// 应用启动，Spring2.5新增的事件，当容器调用ConfigurableApplicationContext的Start()方法开始/重新开始容器时触发该事件。
		if (event instanceof ContextStartedEvent) {
			logger.info("应用启动");
		} else
		// 应用停止，Spring2.5新增的事件，当容器调用ConfigurableApplicationContext的Stop()方法停止容器时触发该事件。
		if (event instanceof ContextStoppedEvent) {
			logger.info("应用停止");
		} else
		// 应用关闭，当ApplicationContext被关闭时触发该事件。容器被关闭时，其管理的所有单例Bean都被销毁。
		if (event instanceof ContextClosedEvent) {
			logger.info("应用关闭");
			System.out.println("=====================================");
			System.out.println("Spring容器关闭");
		} else {
		}
	}
}