package com.ltl.demo.validation.constraints;


import com.ltl.demo.validation.validators.NumScopeValidator;
import com.ltl.demo.validation.validators.SalaryScopeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义注解
 * 判断输入值是否是整数且在指定范围内
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})

@Retention(RUNTIME)
@Repeatable(SalaryScope.List.class)
@Documented
@Constraint(validatedBy = SalaryScopeValidator.class)//指定实现校验逻辑的类
public @interface SalaryScope {
    long min() default 0;

    long max() default Long.MAX_VALUE;

    String message() default "请输入正确格式范围";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    /**
     * Defines several {@link SalaryScope} annotations on the same element.
     *
     * @see SalaryScope
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        SalaryScope[] value();
    }
}
