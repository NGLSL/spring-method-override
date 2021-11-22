package xin.codedream.spring.method.override;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest(classes = Application.class)
class TestMethodOverride {
    @Autowired
    private TestA testA;

    @Test
    public void testMethodOverrideHello() {
        String result = testA.hello();
        Assert.state("Hi".equals(result), "Failed");
    }

    @Test
    public void testMethodOverrideHello2() {
        String result = testA.hello2();
        Assert.state("hello2".equals(result), "Failed");
    }
}
