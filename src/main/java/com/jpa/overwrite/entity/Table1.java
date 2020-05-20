package com.jpa.overwrite.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="table1")
@Data
public class Table1 {

    @Id    //主键id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    @Column(name="id")//数据库字段名
    private Integer id;

    @Column(name="title")
    private String title;
}
