package me.candybox.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import me.candybox.demo.utils.RedisUtil;

@Slf4j
@RequestMapping("/api")
@RestController
@RefreshScope
@Tag(name="demo-controller",description="demo接口")
public class DemoController {

    @Autowired
    private RedisUtil redisUtil;

    @Value("${demo}")
    private String demoConfig;


/* 
	OpenAPI 3								注解位置
@Tag(name = “接口类描述”)			    Controller 类上
@Operation(summary =“接口方法描述”)		Controller 方法上
@Parameters							   Controller 方法上
@Parameter(description=“参数描述”)		Controller 方法上 @Parameters 里
@Parameter(description=“参数描述”)		Controller 方法的参数上
(@Parameter(hidden = true) 或 @Operation(hidden = true) 或 @Hidden)
@Schema								   DTO类上
@Schema								   DTO属性上

参考：https://github.com/OAI/OpenAPI-Specification/blob/3.0.1/versions/3.0.1.md
*/

    @Cacheable(value="demoCacheService",keyGenerator = "keyGenerator",sync = true)
    @Operation(summary ="demo get接口")
    @GetMapping("/demo")
    @ResponseBody
    public String demo(@Parameter(description="demo 参数") @RequestParam(defaultValue ="测试参数") String demo){
        log.info("参数={}",demo);
        redisUtil.set("demo", demo);
        log.info("reids 参数={}",redisUtil.get("demo"));
        return demo;
 
    }


    @Operation(summary ="demoConfig get接口")
    @GetMapping("/demoConfig")
    @ResponseBody
    public String demoConfig(){
        return demoConfig;
    }


}
