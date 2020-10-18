# morpc

> A simple Rpc framework.

## 1. Run Demo

1. run `mvn install`
2. start registry center: demos/morpc-registry-center-demo
3. start service: demos/morpc-service-demo
4. start reference: demos/morpc-reference-demo
5. test rpc: [http://localhost:8080/test?message=world](http://localhost:8080/test?message=world)

## 2. Usage

### 2.1. Declare Rpc Service

```java
public interface DemoHelloService {

    String sayHello(String message);
}


@MoService
@Service
public class DemoHelloServiceImpl implements DemoHelloService {

    @Override
    public String sayHello(String message) {
        return "hello " + message;
    }
}
```

### 2.2. Rpc Reference

```java
@RestController
public class TestController {

    @MoReference
    private DemoHelloService demoHelloService;

    @GetMapping("/test")
    public String test() {
        return demoHelloService.sayHello("world");
    }
}
```
