package com.jpa.overwrite.config;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class HeadConverter extends ClassicConverter {


    public String convert(ILoggingEvent event) {

        try{
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
            return request.getRemoteAddr() + event.getFormattedMessage();
        }catch (Exception e){
            return  event.getFormattedMessage();
        }
    }
}
