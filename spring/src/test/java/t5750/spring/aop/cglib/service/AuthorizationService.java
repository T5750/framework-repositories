package t5750.spring.aop.cglib.service;

import java.lang.reflect.Method;

/**
 * A simple authorization service for illustration purpose.
 */
public interface AuthorizationService {
	/**
	 * Authorization check for a method call. An AuthorizationException will be
	 * thrown if the check fails.
	 */
	void authorize(Method method);
}