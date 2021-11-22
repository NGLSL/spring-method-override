package xin.codedream.spring.method.override;

import org.springframework.stereotype.Component;

@Component
public class TestA {
    public String hello() {
        System.out.println("hello");
        return "hello";
    }

    public String hello2() {
        System.out.println("hello2");
        return "hello2";
    }
}
