package com.jpa.overwrite.service;

import com.jpa.overwrite.entity.Table1;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface TableService {

    Table1 getTable1(Integer id);

    Page<Table1> getAllTable1ByPage(int page);
}
