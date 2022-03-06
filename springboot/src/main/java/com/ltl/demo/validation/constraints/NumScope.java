package com.ltl.demo.validation.constraints;


import com.ltl.demo.validation.validators.NumScopeValidator;

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
@Repeatable(NumScope.List.class)
@Documented
@Constraint(validatedBy = NumScopeValidator.class)//指定实现校验逻辑的类
public @interface NumScope {
    long min() default 0;

    long max() default Long.MAX_VALUE;

    String message() default "请输入正确数字";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    /**
     * Defines several {@link NumScope} annotations on the same element.
     *
     * @see NumScope
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        NumScope[] value();
    }
}
