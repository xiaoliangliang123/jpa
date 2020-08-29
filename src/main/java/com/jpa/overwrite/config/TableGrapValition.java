package com.jpa.overwrite.config;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Table1Validator.class)
@Documented
public @interface TableGrapValition {

    /**
     * 添加value属性，可以作为校验时的条件,若不需要，可去掉此处定义
     */
    int value() default 0;

    String message() default "组合集合中不能含有null元素";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}