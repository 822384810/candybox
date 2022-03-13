package me.candybox.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@RefreshScope
public class DemoController {


    @GetMapping("/demo")
    @ResponseBody
    public String demo(){
        return "get demo";
    }


    @Value("${demo}")
    private String demoConfig;

    @GetMapping("/demoConfig")
    @ResponseBody
    public String demoConfig(){
        return demoConfig;
    }
}
