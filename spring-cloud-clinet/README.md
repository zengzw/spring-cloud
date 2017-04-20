# Spring Cloud

#Ribbon
- Ribbon是一个基于HTTP和TCP客户端的负载均衡器。
- Ribbon可以在通过客户端中配置的ribbonServerList服务端列表去轮询访问以达到均衡负载的作用。
- 当Ribbon与Eureka联合使用时，ribbonServerList会被DiscoveryEnabledNIWSServerList重写，扩展成从Eureka注册中心中获取服务端列表。同时它也会用NIWSDiscoveryPing来取代IPing，它将职责委托给Eureka来确定服务端是否已经启动。

#Feign
- Feign是一个声明式的Web Service客户端，它使得编写Web Serivce客户端变得更加简单。
- 我们只需要使用Feign来创建一个接口并用注解来配置它既可完成。它具备可插拔的注解支持，包括Feign注解和JAX-RS注解

## 服务注册消费 eureka
- @EnableEurekaServer 启用服务注册中心
- 在application.properties 做相关配置。 在默认设置下，该服务注册中心也会将自己作为客户端来尝试注册它自己，所以我们需要禁用它的客户端注册行为
```
server.port=1111
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
eureka.client.serviceUrl.defaultZone=http://localhost:${server.port}/eureka/
```

## 向入口程序添加@EnableDiscoveryClient注解，激活DiscoveryClient的实现，这样才能注册到注册中心上面。