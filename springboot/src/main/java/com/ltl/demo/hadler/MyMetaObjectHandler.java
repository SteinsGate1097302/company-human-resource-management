package com.ltl.demo.hadler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@Component // 一定不要忘记把处理器加到IOC容器中！
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Autowired
    HttpServletRequest request;

    // 插入时的填充策略
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill.....");
        // setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject)
        this.setFieldValByName("createBy",request.getSession().getAttribute("LoginAdminId") , metaObject);
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("lastUpdateBy", request.getSession().getAttribute("LoginAdminId"), metaObject);
        this.setFieldValByName("lastUpdateTime", new Date(), metaObject);

        //添加admin时默认是普通管理员
        this.setFieldValByName("accountType", "N", metaObject);
        //添加admin,emp时默认 在职
        this.setFieldValByName("deleted", "0", metaObject);
        //添加emp时默认入职时间为当前时间
        this.setFieldValByName("inductionTime",new Date(),metaObject);
        //添加emp时默认工资为8000
        this.setFieldValByName("salary",8000,metaObject);
    }

    // 更新时的填充策略
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill.....");
        this.setFieldValByName("lastUpdateBy", request.getSession().getAttribute("LoginAdminId"), metaObject);
        this.setFieldValByName("lastUpdateTime", new Date(), metaObject);
    }

}