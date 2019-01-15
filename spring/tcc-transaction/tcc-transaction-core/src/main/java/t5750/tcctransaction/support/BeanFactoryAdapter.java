package t5750.tcctransaction.support;

/**
 */
public class BeanFactoryAdapter {
	private static BeanFactory beanFactory;

	public static Object getBean(Class<?> aClass) {
		return beanFactory.getBean(aClass);
	}

	public static void setBeanFactory(BeanFactory beanFactory) {
		BeanFactoryAdapter.beanFactory = beanFactory;
	}
}
