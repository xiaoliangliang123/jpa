package com.jpa.overwrite.config;

import com.jpa.overwrite.entity.Table1;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class Table1Validator implements ConstraintValidator<TableGrapValition,Table1> {


    @Override
    public boolean isValid(Table1 table1, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
