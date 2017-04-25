package com.phoenix.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.phoenix.cloud.filter.AccessFilter;

@EnableZuulProxy  //注解开启Zuul
//@SpringCloudApplication //整合了@SpringBootApplication、@EnableDiscoveryClient、@EnableCircuitBreaker
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
public class ZullApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZullApplication.class, args);
	}
	
	
	@Bean
    public AccessFilter accessFilter() {
        return new AccessFilter();
    }
}
