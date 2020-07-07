package com.jpa.overwrite.config;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class MyPatternLayout extends PatternLayout {

    @Override
    public String doLayout(ILoggingEvent event) {


        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();

        Optional<String> ipOptional = Optional.ofNullable(request.getRemoteAddr());
        String ip = ipOptional.isPresent()?ipOptional.get():"";
        return ip+" "+super.doLayout(event);
    }
}
