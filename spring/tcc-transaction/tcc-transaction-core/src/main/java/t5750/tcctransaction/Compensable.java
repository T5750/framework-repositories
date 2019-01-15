package t5750.tcctransaction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 事务补偿注解.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Compensable {
	public String confirmMethod() default "";

	public String cancelMethod() default "";
}