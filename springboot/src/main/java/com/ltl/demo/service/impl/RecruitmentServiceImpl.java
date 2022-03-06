package com.ltl.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ltl.demo.common.constant.StatusConstant;
import com.ltl.demo.entity.Recruitment;
import com.ltl.demo.mapper.RecruitmentMapper;
import com.ltl.demo.service.RecruitmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ltl.demo.vo.RecruitmentPageVo;
import com.ltl.demo.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ltl
 * @since 2022-02-24
 */
@Service
public class RecruitmentServiceImpl extends ServiceImpl<RecruitmentMapper, Recruitment> implements RecruitmentService {
    @Autowired
    private RecruitmentMapper recruitmentMapper;

    @Override
    public IPage<Recruitment> queryPage(RecruitmentPageVo recruitmentPageVo) {

        IPage<Recruitment> page = new Page<>(recruitmentPageVo.getCurrentPage(),recruitmentPageVo.getPageSize());
        QueryWrapper<Recruitment> queryWrapper = new QueryWrapper<>();
        if (recruitmentPageVo.getDepartment() != null && recruitmentPageVo.getDepartment().length() != 0){
            queryWrapper.lambda().eq(Recruitment::getRDeptName,recruitmentPageVo.getDepartment());
        }
        if (recruitmentPageVo.getPosition() != null && recruitmentPageVo.getPosition().length() != 0){
            queryWrapper.lambda().eq(Recruitment::getRPosition,recruitmentPageVo.getPosition());
        }

        return recruitmentMapper.selectPage(page,queryWrapper);
    }


    @Override
    public Result<?> addOrUpdate(Recruitment recruitment) {
        if (recruitment.getId() != null){
            recruitmentMapper.updateById(recruitment);
            return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.UPDATE_SUCCESS_MSG);
        }else {
            recruitmentMapper.insert(recruitment);
            return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.INSERT_SUCCESS_MSG);
        }
    }


    @Override
    public void delete(int id) {
        recruitmentMapper.deleteById(id);
    }
}
