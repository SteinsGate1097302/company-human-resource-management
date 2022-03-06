package com.ltl.demo.common.handler;


import com.ltl.demo.exception.MyException;
import com.ltl.demo.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParseException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@Slf4j // 日志输出
@RestControllerAdvice // 全局异常捕获 异步
public class GlobalExceptionHandler {

    //自定义异常
    @ExceptionHandler(MyException.class)
    public Result<?> FreightException(MyException e, HttpServletRequest request) {
        return new Result<>(e.getCode(),e.getMsg());
    }


    @ExceptionHandler(value = Exception.class)
    public Result<?> Exception(Exception e){
        log.error("运行时异常：--------------{}",e);

        return new Result<>("400",e.getMessage());
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<?> handler(MethodArgumentNotValidException e){
        log.error("Json实体参数校验异常：--------------{}",e);
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();

        return new Result<>("400",objectError.getDefaultMessage());
    }


    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result<?> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        log.error("单参数校验异常：--------------{}",e);
        return new Result<>("400",e.getConstraintViolations().stream().findFirst().get().getMessage());
    }


    @ExceptionHandler(value = BindException.class)
    public Result<?> handleBindException(BindException e, HttpServletRequest request) {
        log.error("实体参数校验异常：--------------{}",e);
        return new Result<>("400",e.getBindingResult().getAllErrors().stream()
                .findFirst().get().getDefaultMessage());
    }


    //参数格式错误
    @ExceptionHandler({JsonParseException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public Result<?> JsonParseException(Exception e, HttpServletRequest request) {
        log.error("参数格式错误：--------------{}",e);
        return new Result<>("400","参数格式错误--"+e.getMessage());
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public Result<?> AdminInfoGetError(DataIntegrityViolationException e, HttpServletRequest request) {
        log.error("获取管理员信息失败，请尝试重新登录...：--------------{}",e);
        return new Result<>("400","获取管理员信息失败，请尝试重新登录...");
    }
}
