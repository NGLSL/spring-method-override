# Spring-method-override

以注解的方式实现Spring replace-method，弥补只有xml的实现方式。

## 使用方式

spring-method-override使用方式

PS：`可以把项目Clone下来，跑跑测试用例！`

注解说明：

- @MethodOverride：
    - beanName：bean的名称
    - beanClass：bean的class
    - methods：@ReplacedMethod 要覆盖的方法
- ReplacedMethod：
    - argType：方法参数
    - method： 方法名

### 引入依赖

```xml

<dependency>
    <groupId>xin.codedream.spring</groupId>
    <artifactId>spring-method-override</artifactId>
    <version>x.y.z</version>
</dependency>
```

### 实例

要被覆盖的Bean的方法

```Java

@Component
public class TestA {
    public String hello() {
        System.out.println("hello");
        return "hello";
    }
}
```

实现覆盖Bean的某个或者某些方法

```Java

@Component
@MethodOverride(beanClass = TestA.class, methods = @ReplacedMethod(method = "hello"))
public class TestB implements MethodReplacer {
    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
        System.out.println("Method override>>> say hi.");
        return "Hi";
    }
}
```

使用的时候还是继续注入TestA

```Java
@SpringBootApplication
public class Application implements CommandLineRunner {
  @Autowired
  private TestA testA;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    String result = testA.hello();
    System.out.println("返回值：" + result);
  }
}
```

正确情况下，应该输出：

`返回值：Hi`
