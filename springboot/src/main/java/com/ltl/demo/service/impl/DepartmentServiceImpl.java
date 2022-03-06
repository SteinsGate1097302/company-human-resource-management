package com.ltl.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ltl.demo.common.constant.ErrorConstant;
import com.ltl.demo.common.constant.StatusConstant;
import com.ltl.demo.entity.Department;
import com.ltl.demo.exception.MyException;
import com.ltl.demo.mapper.DepartmentMapper;
import com.ltl.demo.service.DepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ltl.demo.vo.DepartmentPageVo;
import com.ltl.demo.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ltl
 * @since 2022-02-11
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public Department queryById(int id) {
        return departmentMapper.selectById(id);
    }


    @Override
    public List<Department> queryAll() {
        return departmentMapper.selectList(null);
    }


    @Override
    public IPage<Department> queryPage(DepartmentPageVo departmentPageVo) {
        IPage<Department> page = new Page<>(departmentPageVo.getCurrentPage(),departmentPageVo.getPageSize());
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        if (departmentPageVo.getDname() != null){
            queryWrapper.lambda().like(Department::getDname,departmentPageVo.getDname());
        }

        return departmentMapper.selectPage(page,queryWrapper);
    }


    @Override
    public Result<?> insertOrUpdate(Department department) throws MyException{
        //校验deptno，dname是否重复
        if (departmentMapper.selectCount(new QueryWrapper<Department>().eq("deptno",department.getDeptno()).ne("id",department.getId())) > 0 ){
            throw new MyException(ErrorConstant.DEPTNO_EXIT_ERROR_CODE,ErrorConstant.DEPTNO_EXIT_ERROR_MSG);
        }
        if (departmentMapper.selectCount(new QueryWrapper<Department>().eq("dname",department.getDname()).ne("id",department.getId())) > 0 ){
            throw new MyException(ErrorConstant.DNAME_EXIT_ERROR_CODE,ErrorConstant.DNAME_EXIT_ERROR_MSG);
        }

        //id为空，新增
        if (department.getId() == null){
            departmentMapper.insert(department);
            return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.INSERT_SUCCESS_MSG);
        }else {
            //更新
            departmentMapper.updateById(department);
            return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.UPDATE_SUCCESS_MSG);
        }
    }


    @Override
    public void delete(int id) {
        departmentMapper.deleteById(id);
    }
}
