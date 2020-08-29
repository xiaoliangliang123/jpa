package com.jpa.overwrite.repository;

import com.jpa.overwrite.entity.Table1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TableRepository extends  JpaRepository<Table1,Integer>,JpaSpecificationExecutor<Table1> {



}




