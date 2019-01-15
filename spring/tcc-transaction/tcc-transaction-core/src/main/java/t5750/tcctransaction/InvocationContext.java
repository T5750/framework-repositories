package t5750.tcctransaction;

import java.io.Serializable;

/**
 * 方法调用上下文.
 */
public class InvocationContext implements Serializable {
	private static final long serialVersionUID = -7969140711432461165L;
	private Class targetClass;
	private String methodName;
	private Class[] parameterTypes;
	private Object[] args;

	public InvocationContext() {
	}

	public InvocationContext(Class targetClass, String methodName,
			Class[] parameterTypes, Object... args) {
		this.methodName = methodName;
		this.parameterTypes = parameterTypes;
		this.targetClass = targetClass;
		this.args = args;
	}

	public Object[] getArgs() {
		return args;
	}

	public Class getTargetClass() {
		return targetClass;
	}

	public String getMethodName() {
		return methodName;
	}

	public Class[] getParameterTypes() {
		return parameterTypes;
	}
}
