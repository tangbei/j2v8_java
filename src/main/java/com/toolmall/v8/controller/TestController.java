package com.toolmall.v8.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: wangBin
 * @date: 2019/1/14 16:42
 * @description: 处理当前年度此时购物车商品的控制器
 */
@RestController
@RequestMapping("/testController")
public class TestController {


    @GetMapping(value = "/test")
    public String test() {
        return "测试Controller";
    }
}
