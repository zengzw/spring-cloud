# springClound

## spring-eureka 服务注册中心
- 配置
	- @EnableEurekaServer 启用服务注册中心
	- 在application.properties 做相关配置。 在默认设置下，该服务注册中心也会将自己作为客户端来尝试注册它自己，所以我们需要禁用它的客户端注册行为
	```
	server.port=1111
	eureka.client.register-with-eureka=false
	eureka.client.fetch-registry=false
	eureka.client.serviceUrl.defaultZone=http://localhost:${server.port}/eureka/
	```


## spring-eureka-service 服务提供者

- 配置
```
#配置服务实例的名字，在后续的调用中，可以直接通过该名字对此服务进行访问
spring.application.name=compute-service
#指定服务实例的访问端口
server.port=2222
#指定要注册到上面的服务注册中心的位置
eureka.client.serviceUrl.defaultZone = http://localhost:1111/eureka/	
```

## 服务消费者 spring-cloud-client
