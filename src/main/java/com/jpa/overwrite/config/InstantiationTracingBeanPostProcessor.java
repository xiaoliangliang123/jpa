package com.jpa.overwrite.config;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ApplicationStartingEvent> {


    //    对消息进行处理
    @Override
    public void onApplicationEvent(ApplicationStartingEvent applicationStartingEvent) {
        System.out.println(" 已接受的信息");
    }


}
