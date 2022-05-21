package me.candybox.core;

import java.util.HashMap;
import java.util.Map;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import me.candybox.core.annotation.CbDataName;

/**
 *  应用监听事件
 */

@Component
@Slf4j
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
    
    public static Map<String,Object> beans =new HashMap<String,Object>();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 根容器为Spring容器
        if(event.getApplicationContext().getParent()!=null){
            Map<String,Object> tmpBeans = event.getApplicationContext().getBeansWithAnnotation(CbDataName.class);
            if(tmpBeans==null){
                log.info("CbDataName Map is null");
                return;
            }
            tmpBeans.forEach((K,V)->{
                String cbDataName=V.getClass().getAnnotation(CbDataName.class).name();
                log.debug("cbDataName={}",cbDataName);
                if(StrUtil.isNotBlank(cbDataName)){
                    beans.put(cbDataName, V);
                }
            });
            log.debug(event.getSource().getClass().getName());
        }
    }
}
