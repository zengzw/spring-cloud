# Spring Cloud

# Ribbon
- Ribbon是一个基于HTTP和TCP客户端的负载均衡器。
- Ribbon可以在通过客户端中配置的ribbonServerList服务端列表去轮询访问以达到均衡负载的作用。
- 当Ribbon与Eureka联合使用时，ribbonServerList会被DiscoveryEnabledNIWSServerList重写，扩展成从Eureka注册中心中获取服务端列表。同时它也会用NIWSDiscoveryPing来取代IPing，它将职责委托给Eureka来确定服务端是否已经启动。

# Feign
- Feign是一个声明式的Web Service客户端，它使得编写Web Serivce客户端变得更加简单。
- 我们只需要使用Feign来创建一个接口并用注解来配置它既可完成。它具备可插拔的注解支持，包括Feign注解和JAX-RS注解

## application入口 添加注解
- @EnableFeignClients

## 调用服务
```java
@FeignClient("compute-service") //服务名称
public interface ComputeService {

    @RequestMapping(method = RequestMethod.GET, value = "/add")
    Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b);
}
```


# Netflix Hystrix 集成
 - 在Spring Cloud中使用了Hystrix 来实现断路器的功能。

# pom
```
 <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-hystrix</artifactId>
</dependency>
```

# application 入口
- @EnableCircuitBreaker注解开启断路器功能

# @HystrixCommand 


