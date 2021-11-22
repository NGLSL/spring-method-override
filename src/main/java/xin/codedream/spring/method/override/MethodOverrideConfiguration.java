package xin.codedream.spring.method.override;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import xin.codedream.spring.method.override.annotation.MethodOverride;
import xin.codedream.spring.method.override.annotation.ReplacedMethod;
import xin.codedream.spring.method.override.interceptor.ReplaceOverrideMethodInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author lxx
 */
@Configuration
public class MethodOverrideConfiguration implements InitializingBean, ApplicationContextAware {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getBeanFactory();
        Map<String, Object> beans = beanFactory.getBeansWithAnnotation(MethodOverride.class);
        final List<Reimplement> reimplements = new ArrayList<>();

        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object bean = entry.getValue();
            String beanName = entry.getKey();
            MethodOverride methodOverrideAnnotation = bean.getClass().getDeclaredAnnotation(MethodOverride.class);
            if (Objects.nonNull(methodOverrideAnnotation)) {
                List<ReplaceOverride> replaceOverrides = new ArrayList<>();
                for (ReplacedMethod method : methodOverrideAnnotation.methods()) {
                    ReplaceOverride replaceOverride = new ReplaceOverride(method.method());
                    for (String argType : method.argType()) {
                        replaceOverride.addTypeIdentifier(argType);
                    }
                    replaceOverrides.add(replaceOverride);
                }
                Reimplement reimplement = new Reimplement();
                reimplement.setSourceBeanName(beanName);
                reimplement.setTargetBeanName(methodOverrideAnnotation.beanName());
                reimplement.setTargetBeanClass(methodOverrideAnnotation.beanClass());
                reimplement.setReplaceOverrides(replaceOverrides);
                reimplements.add(reimplement);
            }
        }

        for (Reimplement reimplement : reimplements) {
            String beanName = reimplement.getTargetBeanName();
            if (!StringUtils.hasLength(beanName)) {
                Assert.notNull(reimplement.getTargetBeanClass(), "BeanName or beanClass is null.");
                beanName = beanFactory.resolveNamedBean(reimplement.getTargetBeanClass())
                        .getBeanName();
            }
            Assert.state(StringUtils.hasLength(beanName), "BeanName or beanClass is null.");

            Object bean = applicationContext.getBean(beanName);
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(bean.getClass());
            enhancer.setCallback(new ReplaceOverrideMethodInterceptor(beanFactory, reimplement.getReplaceOverrides(),
                    reimplement.getSourceBeanName()));
            Object proxyBean = enhancer.create();
            // remove originalBean
            beanFactory.removeBeanDefinition(beanName);
            // register proxyBean
            beanFactory.registerSingleton(beanName, proxyBean);
        }
    }
}
