package t5750.spring.aop.cglib.proxy;

import java.lang.reflect.Method;


import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import t5750.spring.aop.cglib.service.AuthorizationService;

/**
 * A simple MethodInterceptor implementation to apply authorization checks for
 * proxy method calls.
 *
 */
public class AuthorizationInterceptor implements MethodInterceptor {
	private AuthorizationService authorizationService;

	/**
	 * Create a AuthorizationInterceptor with the given AuthorizationService
	 */
	public AuthorizationInterceptor(AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	/**
	 * Intercept the proxy method invocations to inject authorization check. The
	 * original method is invoked through MethodProxy.
	 * 
	 * @param object
	 *            the proxy object
	 * @param method
	 *            intercepted Method
	 * @param args
	 *            arguments of the method
	 * @param proxy
	 *            the proxy used to invoke the original method
	 * @throws Throwable
	 *             any exception may be thrown; if so, super method will not be
	 *             invoked
	 * @return any value compatible with the signature of the proxied method.
	 */
	public Object intercept(Object object, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {
		if (authorizationService != null) {
			// may throw an AuthorizationException if authorization failed
			authorizationService.authorize(method);
		}
		return methodProxy.invokeSuper(object, args);
	}
}