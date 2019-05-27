/**
 * @author nartan.vyas
 */

package utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * retry if test failed check method and updat result by test case id
 * @author nartan.vyas
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) // can use in method only.
public @interface TestAnnotation {
	public String testCaseID() default "";
}
