package com.ltl.demo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ltl.demo.entity.Admin;
import com.ltl.demo.exception.MyException;
import com.ltl.demo.validation.ValidGroup;
import com.ltl.demo.vo.AdminPageVo;
import com.ltl.demo.vo.Result;
import com.ltl.demo.common.constant.StatusConstant;
import com.ltl.demo.service.AdminService;
import com.ltl.demo.vo.AdminVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ltl
 * @since 2022-01-14
 */
@Slf4j
@RestController
@RequestMapping("/admin")
@Validated
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    HttpServletRequest request;

    @PostMapping("/login")
    public Result<?> login(@Validated @RequestBody Admin admin, HttpSession session) throws MyException{
        adminService.login(admin);
        session.setAttribute("LoginAdminId",adminService.queryByUsername(admin.getUsername()).getId());
        log.error(request.getSession().getAttribute("LoginAdminId").toString());
        return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.LOGIN_SUCCESS_MSG);
    }

    @PostMapping("/add")
    public Result<?> add(@Validated(value = ValidGroup.Crud.Create.class) @RequestBody AdminVo adminVo) throws MyException {
        adminService.addOrUpdate(adminVo);
        return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.INSERT_SUCCESS_MSG);
    }

    @PutMapping("/update")
    public Result<?> update(@Validated(value = ValidGroup.Crud.Update.class) @RequestBody AdminVo adminVo) throws MyException{
        adminService.addOrUpdate(adminVo);
        return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.UPDATE_SUCCESS_MSG);
    }

    @GetMapping("/queryPage")
    public Result<IPage<AdminVo>> queryPage(@Validated AdminPageVo adminPageVo){
        IPage<AdminVo> adminPage = adminService.queryPage(adminPageVo);
        return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.QUERY_SUCCESS_MSG,adminPage);
    }

    @GetMapping("/queryById")
    public Result<AdminVo> queryById(Integer id){
        return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.QUERY_SUCCESS_MSG,adminService.queryById(id));
    }

    @GetMapping("/queryByUsername")
    public Result<AdminVo> queryByUsername(String username){
        return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.QUERY_SUCCESS_MSG,adminService.queryByUsername(username));
    }

    @DeleteMapping("/deleteById")
    public Result<?> deleteById(Integer id){
        adminService.deleteById(id);
        return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.DELETE_SUCCESS_MSG);
    }


}

