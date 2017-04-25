/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.phoenix.cloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author zengzw
 * @date 2017年4月18日
 */
@RestController
public class DemoController {

    
    private Logger logger = LoggerFactory.getLogger(DemoController.class);
    
    @Autowired
    private DiscoveryClient client;
    
    
    @RequestMapping(value="/add",method= RequestMethod.GET)
    public Integer add(@RequestParam Integer a,@RequestParam Integer b){
        logger.info("--->request params:{},{}",a,b);
        
        Integer result = a + b;
        
        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info("/add, host: " + instance.getHost() + ", service_id: " + instance.getServiceId() + ", result: " + result);
        return result;
    }
    
    
}
