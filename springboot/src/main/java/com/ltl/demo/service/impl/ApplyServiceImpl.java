package com.ltl.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ltl.demo.common.constant.ErrorConstant;
import com.ltl.demo.common.constant.StatusConstant;
import com.ltl.demo.entity.Apply;
import com.ltl.demo.entity.Department;
import com.ltl.demo.exception.MyException;
import com.ltl.demo.mapper.ApplyMapper;
import com.ltl.demo.mapper.DepartmentMapper;
import com.ltl.demo.service.ApplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ltl.demo.vo.ApplyPageVo;
import com.ltl.demo.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ltl
 * @since 2022-02-25
 */
@Service
public class ApplyServiceImpl extends ServiceImpl<ApplyMapper, Apply> implements ApplyService {
    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public IPage<Apply> queryPage(ApplyPageVo applyPageVo) {
        IPage<Apply> page = new Page<>(applyPageVo.getCurrentPage(),applyPageVo.getPageSize());
        QueryWrapper<Apply> queryWrapper = new QueryWrapper<>();

        if (applyPageVo.getId() != null){
            queryWrapper.lambda().eq(Apply::getId,applyPageVo.getId());
        }
        if (applyPageVo.getName() != null && applyPageVo.getName().length() != 0){
            queryWrapper.lambda().like(Apply::getName,applyPageVo.getName());
        }
        if (applyPageVo.getPhone() != null && applyPageVo.getPhone().length() != 0){
            queryWrapper.lambda().eq(Apply::getPhone,applyPageVo.getPhone());
        }

        //根据应聘状态（审核中、已通过、未通过）筛选数据
        if (applyPageVo.getResult() != null && applyPageVo.getResult().length() != 0){
            //当前进度 <4 && 当前节点状态=="wait"     --审核中
            if (applyPageVo.getResult().equals("wait")){
                queryWrapper.lambda().eq(Apply::getStatus,applyPageVo.getResult()).lt(Apply::getProgress,4);
            }
            //当前进度 =4       --已通过
            if (applyPageVo.getResult().equals("success")){
                queryWrapper.lambda().eq(Apply::getProgress,4);
            }
            //当前进度 <4 && 当前节点状态=="error"   --未通过
            if (applyPageVo.getResult().equals("error")){
                queryWrapper.lambda().eq(Apply::getStatus,applyPageVo.getResult()).lt(Apply::getProgress,4);
            }
        }

        //根据apply的deptno查询dname用于前端展示
        IPage<Apply> applyIPage = applyMapper.selectPage(page, queryWrapper);
        for (Apply record : applyIPage.getRecords()) {
            record.setDname(departmentMapper.selectOne(new QueryWrapper<Department>().eq("deptno",record.getDeptno())).getDname());
        }
        return applyIPage;
    }


    @Override
    public Result<?> addOrUpdate(Apply apply) throws MyException{
        //校验应聘者联系方式是否重复
        if (applyMapper.selectCount(new QueryWrapper<Apply>().eq("phone",apply.getPhone()).ne("id",apply.getId())) > 0){
            throw new MyException(ErrorConstant.APPLY_PHONE_EXIT_ERROR_CODE,ErrorConstant.APPLY_PHONE_ERROR_MSG);
        }

        if (apply.getId() != null){
            applyMapper.updateById(apply);
            return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.UPDATE_SUCCESS_MSG);
        }else {
            applyMapper.insert(apply);
            return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.INSERT_SUCCESS_MSG);
        }
    }
}
