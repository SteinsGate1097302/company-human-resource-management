package com.ltl.demo.validation.validators;


import com.ltl.demo.validation.constraints.NumScope;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 判断输入值是否是整数且在指定范围内
 */
@Slf4j
public class NumScopeValidator implements ConstraintValidator<NumScope, Object> {
    private long min;
    private long max;

    @Override
    public void initialize(NumScope constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        //不能为空
        if(value == null){
            log.error("value is null!!!");
            return false;
        }
        //必须是整数且在指定范围
        try {
            Long temp = Long.parseLong(value.toString());
            if (temp >= min && temp <= max){
                return true;
            }else {
                return false;
            }
        }catch (NumberFormatException e){
            return false;
        }

    }
}
