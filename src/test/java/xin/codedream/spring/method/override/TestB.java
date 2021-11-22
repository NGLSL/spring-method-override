package xin.codedream.spring.method.override;

import org.springframework.beans.factory.support.MethodReplacer;
import org.springframework.stereotype.Component;
import xin.codedream.spring.method.override.annotation.MethodOverride;
import xin.codedream.spring.method.override.annotation.ReplacedMethod;

import java.lang.reflect.Method;

@Component
@MethodOverride(beanClass = TestA.class, methods = @ReplacedMethod(method = "hello"))
public class TestB implements MethodReplacer {
    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
        System.out.println("Method override>>> say hi.");
        return "Hi";
    }
}
