package com.jpa.overwrite.util;


import com.alibaba.fastjson.JSON;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.springframework.util.StringUtils;

import java.util.Map;

public class HttpUtil {

    /**
     * 发送Get请求
     * @param url : 请求的连接
     * @param params ： 请求参数，无参时传null
     * @return
     */
    public static String sendGet(String url,Map<String,String> params){
        HttpRequest request = HttpRequest.get(url);
        if(params!=null) {
            request.query(params);
        }
        HttpResponse response = request.send();
        String respJson = response.bodyText();
        return respJson;
    }


    /**
     * 发送Post请求-json数据
     * @param url : 请求的连接
     * @param params ：  请求参数，无参时传null
     * @return
     */
    public static String sendPostToJson(String url,Map<String,String> headers, String text){
        HttpRequest request = HttpRequest.post(url);
        request.contentType("application/json");
        request.charset("utf-8");
        request.header(headers);
        //参数详情
        if(!org.apache.commons.lang3.StringUtils.isEmpty(text)) {
            request.bodyText(text);
        }

        HttpResponse response = request.send();
        String respJson = response.bodyText();
        return respJson;
    }

    /**
     * 发送Post请求
     * @param url : 请求的连接
     * @param params ：  请求参数，无参时传null
     * @return
     */
    public static String sendPost(String url,Map<String,Object> params ){
        HttpRequest request = HttpRequest.post(url);

        //参数详情
        if(params!=null) {
            request.form(params);
        }
        HttpResponse response = request.send();
        String respJson = response.bodyText();
        return respJson;
    }


}
