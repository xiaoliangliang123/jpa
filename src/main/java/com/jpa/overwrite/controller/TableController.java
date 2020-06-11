package com.jpa.overwrite.controller;

import com.google.common.collect.ImmutableMap;
import com.jpa.overwrite.entity.Table1;
import com.jpa.overwrite.service.TableService;
import com.jpa.overwrite.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TableController {


    @Autowired
    TableService tableService;

    @ResponseBody
    @RequestMapping("/table1/{id}")
    public Table1 table1(@PathVariable("id") Integer id){
        Table1 table1 = tableService.getTable1(id);
        return table1;
    }


    @ResponseBody
    @RequestMapping("/kibana/serch")
    public String kibana(){

        String url = "http://localhost:5601/api/console/proxy?path=wk_topic%2F_search&method=GET";
        Map headers = ImmutableMap.of("kbn-xsrf ","sss","content-type","application/json");
        String  text = "{\n" +
                "  \"query\": {\n" +
                "    \"match_all\": {}\n" +
                "  }\n" +
                "}";
        String response = HttpUtil.sendPostToJson(url,headers,text);
        return response;
    }

}
