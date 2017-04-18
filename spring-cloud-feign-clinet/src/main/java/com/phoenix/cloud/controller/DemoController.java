/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.phoenix.cloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.phoenix.cloud.service.ComputeService;


/**
 *
 * @author zengzw
 * @date 2017年4月18日
 */
@RestController
public class DemoController {


    private Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    ComputeService computeClient;

    @RequestMapping(value="/add",method= RequestMethod.GET)
    public Integer add(){
        logger.info("--->customer request params:{},{}");
        
     return computeClient.add(10, 20);
    }


}
