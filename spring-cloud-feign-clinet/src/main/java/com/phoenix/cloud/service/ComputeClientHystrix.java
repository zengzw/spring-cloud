/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.phoenix.cloud.service;

import org.springframework.stereotype.Component;

/**
 * 熔断
 * 
 * @author zengzw
 * @date 2017年4月20日
 */
@Component
public class ComputeClientHystrix implements ComputeService{

    @Override
    public Integer add(Integer a, Integer b) {
      return -9999;
    }

}
