package xin.codedream.spring.method.override.annotation;

import java.lang.annotation.*;

/**
 * @author nglsl
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodOverride {

    /**
     * 要覆盖的Bean的名称
     *
     * @return
     */
    String beanName() default "";

    /**
     * 要覆盖的Bean的Class对象
     *
     * @return
     */
    Class<?> beanClass();

    /**
     * 要覆盖的方法
     *
     * @return
     */
    ReplacedMethod[] methods();
}
