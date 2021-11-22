package xin.codedream.spring.method.override.interceptor;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.MethodReplacer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import xin.codedream.spring.method.override.ReplaceOverride;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author nglsl
 */
public class ReplaceOverrideMethodInterceptor implements MethodInterceptor {
    private final BeanFactory beanFactory;
    private final List<ReplaceOverride> replaceOverrides;
    private final String targetBeanName;

    public ReplaceOverrideMethodInterceptor(BeanFactory beanFactory, List<ReplaceOverride> replaceOverrides, String targetBeanName) {
        this.beanFactory = beanFactory;
        this.replaceOverrides = replaceOverrides;
        this.targetBeanName = targetBeanName;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        ReplaceOverride ro = null;
        for (ReplaceOverride replaceOverride : replaceOverrides) {
            boolean match = replaceOverride.matches(method);
            if (match) {
                ro = replaceOverride;
                break;
            }
        }
        if (ro == null) {
            return methodProxy.invokeSuper(obj, args);
        } else {
            MethodReplacer mr = this.beanFactory.getBean(targetBeanName, MethodReplacer.class);
            return mr.reimplement(obj, method, args);
        }
    }
}
