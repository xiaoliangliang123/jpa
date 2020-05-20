package com.jpa.overwrite.repository;

import com.jpa.overwrite.entity.Table1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<Table1,Integer> {
}
