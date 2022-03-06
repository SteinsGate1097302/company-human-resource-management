package com.ltl.demo.validation.validators;


import com.ltl.demo.util.PublicUtil;
import com.ltl.demo.validation.constraints.NumScope;
import com.ltl.demo.validation.constraints.SalaryScope;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

/**
 * 判断薪资范围输入是否合法
 */
@Slf4j
public class SalaryScopeValidator implements ConstraintValidator<SalaryScope, Object> {
    private long min;
    private long max;

    @Override
    public void initialize(SalaryScope constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value != null){
            Map<String, Object> map = PublicUtil.splitSymbolAndNum(value.toString());
            String symbol = map.get("symbol").toString();
            String num = map.get("num").toString();
            //符号校验
            if (symbol.length() > 2){
                return false;
            }else if (symbol.length() == 2){
                if (symbol.substring(0,1).equals("=") || !symbol.substring(1).equals("=")){
                    return false;
                }
            }

            //数字不能为空
            if(num == null || num.length() == 0){
                log.error("value is null!!!");
                return false;
            }
            //数字必须是整数且在指定范围
            try {
                Long temp = Long.parseLong(num);
                if (temp >= min && temp <= max){
                    return true;
                }else {
                    return false;
                }
            }catch (NumberFormatException e){
                return false;
            }
        }
        return true;
    }
}
