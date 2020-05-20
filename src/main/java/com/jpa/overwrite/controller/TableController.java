package com.jpa.overwrite.controller;

import com.jpa.overwrite.entity.Table1;
import com.jpa.overwrite.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

}
