package com.jpa.overwrite.entity;


import com.jpa.overwrite.config.TableGrapCheck;
import com.jpa.overwrite.config.TableGrapValition;
import com.jpa.overwrite.config.TimeNullCheck;
import com.jpa.overwrite.config.TitleNullCheck;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@TableGrapValition(groups = {TableGrapCheck.class})
@Entity
@Table(name="table1")
@Data
public class Table1 {

    public Table1(){}

    public Table1(Integer id){
        this.id = id;
    }
    @Id    //主键id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    @Column(name="id")//数据库字段名
    private Integer id;

    @NotNull(message="标题不能为空！",groups = {TitleNullCheck.class})
    @Column(name="title")
    private String title;

    @NotNull(message="时间不能为空！",groups = {TimeNullCheck.class})
    @Column(name="time")
    private String time;

}
