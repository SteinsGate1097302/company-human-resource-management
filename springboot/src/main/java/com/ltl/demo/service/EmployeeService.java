package com.ltl.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ltl.demo.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ltl.demo.exception.MyException;
import com.ltl.demo.vo.BatchUpdateEmpVo;
import com.ltl.demo.vo.EmployeePageVo;
import com.ltl.demo.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ltl
 * @since 2022-02-06
 */
public interface EmployeeService extends IService<Employee> {

    /**
     * 分页查询
     * @param employeePageVo
     * @return
     */
    IPage<Employee> queryPage(EmployeePageVo employeePageVo);


    /**
     * 新增或更新员工信息
     * @param employee
     * @return
     */
    Result<?> addOrUpdate(Employee employee) throws MyException;


    /**
     * 根据ID逻辑删除员工信息,实际上是修改deleted
     * @param id
     */
    void deleteById(int id);

    /**
     * 批量更新薪资
     * @param
     */
    int batchUpdate(BatchUpdateEmpVo batchUpdateEmpVo);
}
