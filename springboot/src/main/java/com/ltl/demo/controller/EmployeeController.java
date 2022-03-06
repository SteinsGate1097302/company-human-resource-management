package com.ltl.demo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ltl.demo.common.constant.StatusConstant;
import com.ltl.demo.entity.Employee;
import com.ltl.demo.exception.MyException;
import com.ltl.demo.service.EmployeeService;
import com.ltl.demo.validation.constraints.NumScope;
import com.ltl.demo.vo.BatchUpdateEmpVo;
import com.ltl.demo.vo.EmployeePageVo;
import com.ltl.demo.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ltl
 * @since 2022-02-06
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/queryPage")
    public Result<IPage<Employee>> queryPage(@Validated EmployeePageVo employeePageVo){
        IPage<Employee> employeeIPage = employeeService.queryPage(employeePageVo);
        return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.QUERY_SUCCESS_MSG,employeeIPage);
    }

    @PostMapping("/addOrUpdate")
    public Result<?> addOrUpdate(@Validated @RequestBody Employee employee) throws MyException {
        return employeeService.addOrUpdate(employee);

    }

    @PutMapping("/batchUpdateEmp")
    public Result<?> batchUpdate(@RequestBody BatchUpdateEmpVo batchUpdateEmpVo){
        int count = employeeService.batchUpdate(batchUpdateEmpVo);
        return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.UPDATE_SUCCESS_MSG+"更新条数: "+count);
    }

    @DeleteMapping("/deleteById")
    public Result<?> deleteById(@NumScope(message = "请输入正确格式ID") int id){
        employeeService.deleteById(id);
        return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.DELETE_SUCCESS_MSG);
    }
}

