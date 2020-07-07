package com.jpa.overwrite.service.impl;

import com.jpa.overwrite.entity.Table1;
import com.jpa.overwrite.repository.TableRepository;
import com.jpa.overwrite.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TableServiceImpl implements TableService {

    @Autowired
    private TableRepository tableRepository;

    @Override
    public Table1 getTable1(Integer id) {
        log.info("id:{}",id);
        return tableRepository.findById(id).get();
    }
}
