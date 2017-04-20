# springClound

# spring-eureka 服务注册中心
- application.properties 配置
	在application.properties 做相关配置。 在默认设置下，该服务注册中心也会将自己作为客户端来尝试注册它自己，所以我们需要禁用它的客户端注册行为
	```
	server.port=1111  #端口
	eureka.client.register-with-eureka=false
	eureka.client.fetch-registry=false
	eureka.client.serviceUrl.defaultZone=http://localhost:${server.port}/eureka/	
	```
	
- applicaiton 入口

```
@EnableEurekaServer //启动服务注册中心
@SpringBootApplication
public class RegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegisterApplication.class, args);
	}
}

```

- POM 文件
```
	<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-eureka-server</artifactId>
	</dependency>
```



# spring-eureka-service 服务提供者

- 配置

```
#配置服务实例的名字，在后续的调用中，可以直接通过该名字对此服务进行访问
spring.application.name=compute-service
#指定服务实例的访问端口
server.port=2222
#指定要注册到上面的服务注册中心的位置
eureka.client.serviceUrl.defaultZone = http://localhost:1111/eureka/	
```

- application.properties 配置文件

```
#配置服务实例的名字，在后续的调用中，可以直接通过该名字对此服务进行访问
spring.application.name=compute-service
#指定服务实例的访问端口
server.port=2222
#指定要注册到上面的服务注册中心的位置
eureka.client.serviceUrl.defaultZone=http://127.0.0.1:1111/eureka/
```

-  Application 入口
```
@EnableDiscoveryClient //该注释能激活DiscoveryClient的实现，实现controller中的信息输出
@SpringBootApplication
public class ServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}
}

```

- POM 文件

```
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-eureka-server</artifactId>
</dependency>
```


# 服务消费者 spring-cloud-robin-client

## Ribbon
- 简介
	- Ribbon是一个基于HTTP和TCP客户端的负载均衡器。
	- Ribbon可以在通过客户端中配置的ribbonServerList服务端列表去轮询访问以达到均衡负载的作用。
	- 当Ribbon与Eureka联合使用时，ribbonServerList会被DiscoveryEnabledNIWSServerList重写，
	 扩展成从Eureka注册中心中获取服务端列表。同时它也会用NIWSDiscoveryPing来取代IPing，它将职责委托给Eureka来确定服务端是否已经启动。

- POM jar包
```
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-ribbon</artifactId>
	</dependency>
```

- service代码
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

- Application 程序入口
```
@EnableDiscoveryClient //该注释能激活DiscoveryClient的实现，实现controller中的信息输出
@SpringBootApplication
@EnableCircuitBreaker //注解开启断路器功能：
public class ClientApplication {

    @Bean
    @LoadBalanced  //负载均衡
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}
}

```

- appliaction.properties 配置文件

```
spring.application.name=ribbon-consumer
server.port=3333
eureka.client.serviceUrl.defaultZone=http://127.0.0.1:1111/eureka/

```




# spring-cloud-feign-clinet 客户端

## Feign
- 简介
	- Feign是一个声明式的Web Service客户端，它使得编写Web Serivce客户端变得更加简单。
	- 我们只需要使用Feign来创建一个接口并用注解来配置它既可完成。它具备可插拔的注解支持，包括Feign注解和JAX-RS注解
	- Spring Cloud为Feign增加了对Spring MVC注解的支持，还`整合了Ribbon和Eureka来提供均衡负载的HTTP客户端实现`。
	

- POM jar包

```
<dependency>
<groupId>org.springframework.cloud</groupId>
<artifactId>spring-cloud-starter-feign</artifactId>
</dependency>
```

- 服务代码

```
@FeignClient(value="compute-service",fallback=ComputeClientHystrix.class) //fallback 熔断支持
public interface ComputeService {

    @RequestMapping(method = RequestMethod.GET, value = "/add")
    Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b);
}
```

- Application 入口

```
@EnableDiscoveryClient //该注释能激活DiscoveryClient的实现，实现controller中的信息输出
@SpringBootApplication
@EnableFeignClients //@EnableFeignClients注解开启Feign功能，
public class FeignClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeignClientApplication.class, args);
	}
}

```


- application.properties 配置

```
pring.application.name=feign-consumer
server.port=3334
eureka.client.serviceUrl.defaultZone=http://127.0.0.1:1111/eureka/
```



# Netflix Hystrix 端路器

- 简介
	- Spring Cloud中使用了Hystrix 来实现断路器的功能。
	- Hystrix是Netflix开源的微服务框架套件之一，该框架目标在于通过控制那些访问远程系统、服务和第三方库的节点，从而对延迟和故障提供更强大的容错能力。
	- Hystrix具备拥有回退机制和断路器功能的线程和信号隔离，请求缓存和请求打包，以及监控和配置等功能。
	
- 在eureka-ribbon的主类RibbonApplication中使用@EnableCircuitBreaker注解开启断路器功能：
```
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
public class RibbonApplication {
	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	public static void main(String[] args) {
		SpringApplication.run(RibbonApplication.class, args);
	}
}
```

- 在使用ribbon消费服务的函数上增加@HystrixCommand注解来指定回调方法。
``` 
@Service
public class ComputeService {
    @Autowired
    RestTemplate restTemplate;
    @HystrixCommand(fallbackMethod = "addServiceFallback")
    public String addService() {
        return restTemplate.getForEntity("http://COMPUTE-SERVICE/add?a=10&b=20", String.class).getBody();
    }
    public String addServiceFallback() {
        return "error";
    }
}
```

