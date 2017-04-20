/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.phoenix.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 *
 * @author zengzw
 * @date 2017年4月20日
 */

@Service
public class ComputeService {
    
    @Autowired
    RestTemplate template;
    
    @HystrixCommand(fallbackMethod = "addServiceFallback")
    public String addService() {
        return template.getForEntity("http://COMPUTE-SERVICE/add?a=10&b=20", String.class).getBody();
    }
    
    public String addServiceFallback() {
        return "error";
    }
}
