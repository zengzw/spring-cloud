/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.phoenix.cloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


/**
 *
 * @author zengzw
 * @date 2017年4月18日
 */
@RestController
public class DemoController {


    private Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    RestTemplate restTemplate;


    @RequestMapping(value="/add",method= RequestMethod.GET)
    public String add(){
        logger.info("--->customer request params:{},{}");
        
        String url = "http://COMPUTE-SERVICE/add?a=10&b=20";
        return restTemplate.getForEntity(url,String.class).getBody();
    }


}
