package t5750.spring.aop.cglib.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.CallbackFilter;

/**
 * An implementation of CallbackFilter for PersistenceServiceImpl
 */
public class PersistenceServiceCallbackFilter implements CallbackFilter {
	// callback index for save method
	private static final int SAVE = 0;
	// callback index for load method
	private static final int LOAD = 1;

	/**
	 * Specify which callback to use for the method being invoked.
	 * 
	 * @method the method being invoked.
	 * @return the callback index in the callback array for this method
	 */
	public int accept(Method method) {
		String name = method.getName();
		if ("save".equals(name)) {
			return SAVE;
		}
		// for other methods, including the load method, use the
		// second callback
		return LOAD;
	}
}