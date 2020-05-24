package com.jpa.overwrite.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

@ConditionalOnProperty(prefix = "spring.jpa.hibernate",value = "ddl-auto")
@Component
public class MyHibernateProperties {

    public static final String DDL_AUTO_NONE = "none";
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto ;

    class JpaDDLAUTO{


    }

    @PostConstruct
    public void con() {
        System.out.println( " _init");
    }


    public void init() throws Exception {

        if(StringUtils.endsWithIgnoreCase(ddlAuto,DDL_AUTO_NONE)){

        }else {
            throw new Exception("datasource not support ddlAuto operation");
        }

    }

}
