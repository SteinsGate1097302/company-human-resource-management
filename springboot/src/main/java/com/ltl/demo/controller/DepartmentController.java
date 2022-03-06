package com.ltl.demo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ltl.demo.common.constant.StatusConstant;
import com.ltl.demo.entity.Department;
import com.ltl.demo.exception.MyException;
import com.ltl.demo.service.DepartmentService;
import com.ltl.demo.vo.DepartmentPageVo;
import com.ltl.demo.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ltl
 * @since 2022-02-11
 */
@Slf4j
@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/queryPage")
    public Result<IPage<Department>> queryPage(DepartmentPageVo departmentPageVo){
        IPage<Department> iPage = departmentService.queryPage(departmentPageVo);
        return new Result<>(StatusConstant.QUERY_SUCCESS_MSG,StatusConstant.QUERY_SUCCESS_MSG,iPage);
    }

    @GetMapping("/queryAll")
    public Result<List<Department>> queryAll(){
        List<Department> departments = departmentService.queryAll();
        return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.QUERY_SUCCESS_MSG,departments);
    }

    @PostMapping("/insertOrUpdate")
    public Result<?> insertOrUpdate(@Validated @RequestBody Department department) throws MyException {
        return departmentService.insertOrUpdate(department);

    }

    @DeleteMapping("/delete")
    public Result<?> delete(int id){
        departmentService.delete(id);
        return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.DELETE_SUCCESS_MSG);
    }
}

