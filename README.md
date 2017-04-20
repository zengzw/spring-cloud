# springClound

# spring-eureka 服务注册中心
- 配置
	- @EnableEurekaServer 启用服务注册中心
	- 在application.properties 做相关配置。 在默认设置下，该服务注册中心也会将自己作为客户端来尝试注册它自己，所以我们需要禁用它的客户端注册行为
	```
	server.port=1111
	eureka.client.register-with-eureka=false
	eureka.client.fetch-registry=false
	eureka.client.serviceUrl.defaultZone=http://localhost:${server.port}/eureka/
	```


# spring-eureka-service 服务提供者

## 配置

```
#配置服务实例的名字，在后续的调用中，可以直接通过该名字对此服务进行访问
spring.application.name=compute-service

#指定服务实例的访问端口
server.port=2222

#指定要注册到上面的服务注册中心的位置
eureka.client.serviceUrl.defaultZone = http://localhost:1111/eureka/	
```




# 服务消费者 spring-cloud-robin-client

## Ribbon
- Ribbon是一个基于HTTP和TCP客户端的负载均衡器。
- Ribbon可以在通过客户端中配置的ribbonServerList服务端列表去轮询访问以达到均衡负载的作用。
- 当Ribbon与Eureka联合使用时，ribbonServerList会被DiscoveryEnabledNIWSServerList重写，扩展成从Eureka注册中心中获取服务端列表。同时它也会用NIWSDiscoveryPing来取代IPing，它将职责委托给Eureka来确定服务端是否已经启动。

### POM jar包
```
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-ribbon</artifactId>
	</dependency>
```

### service代码
```
@Service
public class ComputeService {
    
    @Autowired
    RestTemplate template;
    
    @HystrixCommand(fallbackMethod = "addServiceFallback") //开启熔断
    public String addService() {
        return template.getForEntity("http://COMPUTE-SERVICE/add?a=10&b=20", String.class).getBody();
    }
    
    public String addServiceFallback() {
        return "error";
    }
}
```

### Application 程序入口
```
@EnableDiscoveryClient //该注释能激活DiscoveryClient的实现，实现controller中的信息输出
@SpringBootApplication
@EnableCircuitBreaker //注解开启断路器功能：
public class ClientApplication {

    @Bean
    @LoadBalanced  //路由切换
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}
}

```

### appliaction.properties 配置文件
```
spring.application.name=ribbon-consumer
server.port=3333
eureka.client.serviceUrl.defaultZone=http://127.0.0.1:1111/eureka/

```





## Feign

- Feign是一个声明式的Web Service客户端，它使得编写Web Serivce客户端变得更加简单。
- 我们只需要使用Feign来创建一个接口并用注解来配置它既可完成。它具备可插拔的注解支持，包括Feign注解和JAX-RS注解

### POM jar包

```
<dependency>
<groupId>org.springframework.cloud</groupId>
<artifactId>spring-cloud-starter-feign</artifactId>
</dependency>
```

### 服务代码

```
@FeignClient(value="compute-service",fallback=ComputeClientHystrix.class) //fallback 熔断支持
public interface ComputeService {

    @RequestMapping(method = RequestMethod.GET, value = "/add")
    Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b);
}
```

### Application 入口

```
@EnableDiscoveryClient //该注释能激活DiscoveryClient的实现，实现controller中的信息输出
@SpringBootApplication
@EnableFeignClients
public class FeignClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeignClientApplication.class, args);
	}
}

```


### application.properties 配置

```
pring.application.name=feign-consumer
server.port=3334
eureka.client.serviceUrl.defaultZone=http://127.0.0.1:1111/eureka/
```



## 服务注册消费 eureka
- @EnableEurekaServer 启用服务注册中心
- 在application.properties 做相关配置。 在默认设置下，该服务注册中心也会将自己作为客户端来尝试注册它自己，所以我们需要禁用它的客户端注册行为
```
server.port=1111
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
eureka.client.serviceUrl.defaultZone=http://localhost:${server.port}/eureka/
```

##入口程序