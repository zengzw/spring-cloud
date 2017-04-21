# Spring Config Server

- 说明
	- Spring Cloud Config为服务端和客户端提供了分布式系统的外部化配置支持
	- 配置服务器为各应用的所有环境提供了一个中心化的外部配置。
	- Spring Cloud Config也提供本地存储配置的方式。  
	我们只需要设置属性spring.profiles.active=native，Config Server会默认从应用的src/main/resource目录下检索配置文件。  
	也可以通过spring.cloud.config.server.native.searchLocations=file:F:/properties/属性来指定配置文件的位置。  
	虽然Spring Cloud Config提供了这样的功能，但是为了支持更好的管理内容和版本控制的功能，还是推荐使用git的方式。
	
- pom.xml中引入spring-cloud-config-server
```
<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-config-server</artifactId>
</dependency>
```

- applicaiton 入口  @EnableConfigServer注解，开启Config Server
```
@EnableConfigServer
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(true).run(args);
	}
}
```

- application.properties 中配置服务信息以及git信息
```
spring.application.name=config-server
server.port=7001
# git管理配置
spring.cloud.config.server.git.uri=http://git.oschina.net/didispace/SpringBoot-Learning/  #配置git仓库位置
spring.cloud.config.server.git.searchPaths=Chapter9-1-4/config-repo #配置仓库路径下的相对搜索位置，可以配置多个
spring.cloud.config.server.git.username=username
spring.cloud.config.server.git.password=password
```