package com.atguigu.guli.service.sms.controller;

import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.sms.util.SmsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RefreshScope
@RestController
@RequestMapping("/sms/sample")
public class SampleController {

    @Autowired
    private SmsProperties smsProperties;

    @Value("${aliyun.sms.signName}")
    private String signName;

    @GetMapping("test1")
    public R test1(){
        return R.ok().data("signName", signName);
    }

    @GetMapping("test2")
    public R test2(){
        return R.ok().data("smsProperties", smsProperties);
    }
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("test3")
    public R test3(){
        redisTemplate.opsForValue().set("test", "123", 5, TimeUnit.MINUTES);
        return R.ok();
    }

}