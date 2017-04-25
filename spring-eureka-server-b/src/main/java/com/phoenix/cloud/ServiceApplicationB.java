package com.phoenix.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient //该注释能激活DiscoveryClient的实现，实现controller中的信息输出
@SpringBootApplication
public class ServiceApplicationB {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplicationB.class, args);
	}
}
