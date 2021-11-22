package xin.codedream.spring.method.override.annotation;

/**
 * @author lxx
 */
public @interface ReplacedMethod {
    /**
     * 参数类型
     *
     * @return
     */
    String[] argType() default {};

    /**
     * 方法名
     *
     * @return
     */
    String method();
}
