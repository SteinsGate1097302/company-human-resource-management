package com.ltl.demo.validation.validators;


import com.ltl.demo.validation.constraints.EnumString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * 判断输入值是否在指定的字符串中
 * 比如@EnumString(value={"M","F",message="性别只能输入M/F"})
 */
public class EnumStringValidator implements ConstraintValidator<EnumString, String> {
    private List<String> enumStringList;

    @Override
    public void initialize(EnumString constraintAnnotation) {
        enumStringList = Arrays.asList(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return false;
        }
        return enumStringList.contains(value);
    }
}
