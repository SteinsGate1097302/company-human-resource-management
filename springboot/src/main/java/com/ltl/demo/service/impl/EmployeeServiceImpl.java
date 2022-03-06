package com.ltl.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ltl.demo.common.constant.ErrorConstant;
import com.ltl.demo.common.constant.StatusConstant;
import com.ltl.demo.entity.Department;
import com.ltl.demo.entity.Employee;
import com.ltl.demo.exception.MyException;
import com.ltl.demo.mapper.DepartmentMapper;
import com.ltl.demo.mapper.EmployeeMapper;
import com.ltl.demo.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ltl.demo.util.Des3Util;
import com.ltl.demo.util.PublicUtil;
import com.ltl.demo.vo.BatchUpdateEmpVo;
import com.ltl.demo.vo.EmployeePageVo;
import com.ltl.demo.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ltl
 * @since 2022-02-06
 */
@Slf4j
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private Des3Util des3Util;

    @Override
    public IPage<Employee> queryPage(EmployeePageVo employeePageVo) {
        IPage<Employee> page= new Page<>(employeePageVo.getCurrentPage(),employeePageVo.getPageSize());
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        if (employeePageVo.getId() != null){
            queryWrapper.lambda().eq(Employee::getId,employeePageVo.getId());
        }
        if (employeePageVo.getUsername() != null && employeePageVo.getUsername().length() != 0){
            queryWrapper.lambda().like(Employee::getUsername,employeePageVo.getUsername());
        }
        if (employeePageVo.getNickname() != null && employeePageVo.getNickname().length() != 0){
            queryWrapper.lambda().like(Employee::getNickname,employeePageVo.getNickname());
        }

        //如果有选离职，那就加上查离职的条件，如果没有选，默认查在职
        if (employeePageVo.getWorkerStatus() != null && employeePageVo.getWorkerStatus().equals("departure")){
            queryWrapper.lambda().eq(Employee::getDeleted,"1");
        }else {
            queryWrapper.lambda().eq(Employee::getDeleted,"0");
        }

        if (employeePageVo.getDepartment() != null && employeePageVo.getDepartment().length() != 0){
            queryWrapper.lambda().eq(Employee::getDepartment,employeePageVo.getDepartment());
        }

        IPage<Employee> employeeIPage = employeeMapper.selectPage(page, queryWrapper);
        if (employeePageVo.getSalaryScope() != null && employeePageVo.getSalaryScope().length() != 0){
            employeeIPage.getRecords().removeIf(employee -> !PublicUtil.deliveryNumValidated(employeePageVo.getSalaryScope(), employee.getSalary()));
        }
        //密码解密 && 查询所属部门名称
        for (Employee employee : employeeIPage.getRecords()) {
            employee.setDepartmentName(departmentMapper.selectOne(new QueryWrapper<Department>().eq("deptno",employee.getDepartment())).getDname());
            employee.setPassword(des3Util.undoDes3(employee.getPassword()));
        }
        return employeeIPage;
    }


    @Override
    public Result<?> addOrUpdate(@Validated @RequestBody Employee employee) throws MyException{
        //校验用户名是否重复，部门是否存在
        if (employeeMapper.selectCount(new QueryWrapper<Employee>().lambda().eq(Employee::getUsername,employee.getUsername()).ne(Employee::getId,employee.getId())) > 0){
            throw new MyException(ErrorConstant.USERNAME_EXIST_CODE,ErrorConstant.USERNAME_EXIST_MSG);
        }
        if (departmentMapper.selectOne(new QueryWrapper<Department>().ne("deptno",employee.getDepartment())) == null){
            throw new MyException(ErrorConstant.DEPT_NOT_EXIT_ERROR_CODE,ErrorConstant.DEPT_NOT_EXIT_ERROR_MSG);
        }

        //密码加密
        employee.setPassword(des3Util.doDes3(employee.getPassword()));
        if (employee.getId() != null){
            employeeMapper.updateById(employee);
            return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.UPDATE_SUCCESS_MSG);
        }else {
            employeeMapper.insert(employee);
            return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.INSERT_SUCCESS_MSG);
        }
    }


    @Override
    public void deleteById(int id) {
        employeeMapper.update(null,new UpdateWrapper<Employee>().lambda().eq(Employee::getId,id).set(Employee::getDeleted,1));
    }


    @Override
    public int batchUpdate(BatchUpdateEmpVo batchUpdateEmpVo) {
        int count = 0;
        log.error(batchUpdateEmpVo.toString());
        for (Employee employee : batchUpdateEmpVo.getEmployees()) {
            //密码加密
            employee.setPassword(des3Util.doDes3(employee.getPassword()));
            //更新薪资
            employee.setSalary((employee.getSalary()+employee.getSalary()*batchUpdateEmpVo.getPercentum()*0.01));
            employee.setSalary((employee.getSalary()+batchUpdateEmpVo.getSalaryNum()));
            count++;
        }
        //调用mp的IService自带的批量更新方法
        this.updateBatchById(batchUpdateEmpVo.getEmployees());
        return count;
    }
}
