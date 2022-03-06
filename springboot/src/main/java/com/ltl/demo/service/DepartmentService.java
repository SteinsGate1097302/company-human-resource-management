package com.ltl.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ltl.demo.entity.Department;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ltl.demo.exception.MyException;
import com.ltl.demo.vo.DepartmentPageVo;
import com.ltl.demo.vo.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ltl
 * @since 2022-02-11
 */
public interface DepartmentService extends IService<Department> {

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    Department queryById(int id);

    /**
     * 查询所有部门
     * @return
     */
    List<Department> queryAll();

    /**
     * 分页查询
     * @param departmentPageVo
     * @return
     */
    IPage<Department> queryPage(DepartmentPageVo departmentPageVo);


    /**
     * 新增或更新部门信息
     * @param department
     * @return
     */
    Result<?> insertOrUpdate(Department department) throws MyException;

    /**
     * 根据ID删除部门信息
     * @param id
     */
    void delete(int id);
}
