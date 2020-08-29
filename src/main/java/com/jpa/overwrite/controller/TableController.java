package com.jpa.overwrite.controller;

import com.google.common.collect.ImmutableMap;
import com.jpa.overwrite.config.Table1CheckSequence;
import com.jpa.overwrite.entity.Table1;
import com.jpa.overwrite.service.TableService;
import com.jpa.overwrite.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
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
    @RequestMapping("/table1/form")
    public ResponseEntity form(@Validated({Table1CheckSequence.class}) @RequestBody  Table1 table1, BindingResult errors){
        //对表单进行验证
        if (errors.hasErrors()){


            return new ResponseEntity(errors.getAllErrors(),HttpStatus.BAD_REQUEST);
        }
        log.info("全部验证通过~！");
        return new ResponseEntity(table1,HttpStatus.OK);
    }


    @ResponseBody
    @RequestMapping("/table1/search/{page}")
    public Page<Table1> tableSearch(@PathVariable("page") Integer page){
        Page<Table1> table1 = tableService.getAllTable1ByPage(page);
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
