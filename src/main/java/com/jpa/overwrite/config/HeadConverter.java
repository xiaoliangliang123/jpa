package com.jpa.overwrite.config;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class HeadConverter extends ClassicConverter {


    public String convert(ILoggingEvent event) {

        try{
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            return "xzz" + event.getFormattedMessage();

        }catch (Exception e){
            return  event.getFormattedMessage();
        }
    }
}
