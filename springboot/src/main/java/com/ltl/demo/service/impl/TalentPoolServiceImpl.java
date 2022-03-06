package com.ltl.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ltl.demo.common.constant.ErrorConstant;
import com.ltl.demo.common.constant.StatusConstant;
import com.ltl.demo.entity.TalentPool;
import com.ltl.demo.exception.MyException;
import com.ltl.demo.mapper.TalentPoolMapper;
import com.ltl.demo.service.TalentPoolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ltl.demo.vo.Result;
import com.ltl.demo.vo.TalentPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ltl
 * @since 2022-02-23
 */
@Service
public class TalentPoolServiceImpl extends ServiceImpl<TalentPoolMapper, TalentPool> implements TalentPoolService {
    @Autowired
    private TalentPoolMapper talentPoolMapper;

    @Override
    public IPage<TalentPool> queryPage(TalentPageVo talentPageVo) {
        IPage<TalentPool> page= new Page<>(talentPageVo.getCurrentPage(),talentPageVo.getPageSize());
        QueryWrapper<TalentPool> queryWrapper = new QueryWrapper<>();
        if (talentPageVo.getTalentNo() != null && talentPageVo.getTalentNo().length() != 0){
            queryWrapper.lambda().like(TalentPool::getTalentNo,talentPageVo.getTalentNo());
        }
        if (talentPageVo.getNickname() != null && talentPageVo.getNickname().length() != 0){
            queryWrapper.lambda().like(TalentPool::getTalentName,talentPageVo.getNickname());
        }
        if (talentPageVo.getNationality() != null && talentPageVo.getNationality().length()!= 0){
            queryWrapper.lambda().eq(TalentPool::getTalentNationality,talentPageVo.getNationality());
        }
        if (talentPageVo.getLevel() != null && talentPageVo.getLevel().length() != 0){
            queryWrapper.lambda().eq(TalentPool::getTalentLevel,talentPageVo.getLevel());
        }

        return talentPoolMapper.selectPage(page,queryWrapper);
    }


    @Override
    public Result<?> addOrUpdate(TalentPool talentPool) throws MyException{
        //校验no，phone是否重复
        if (talentPoolMapper.selectCount(new QueryWrapper<TalentPool>().eq("talent_no",talentPool.getTalentNo()).ne("id",talentPool.getId())) > 0){
            throw new MyException(ErrorConstant.TALENT_NO_EXIT_ERROR_CODE,ErrorConstant.TALENT_NO_ERROR_MSG);
        }
        if (talentPoolMapper.selectCount(new QueryWrapper<TalentPool>().eq("talent_phone",talentPool.getTalentPhone()).ne("id",talentPool.getId())) > 0){
            throw new MyException(ErrorConstant.TALENT_PHONE_EXIT_ERROR_CODE,ErrorConstant.TALENT_PHONE_ERROR_MSG);
        }

        if (talentPool.getId() != null){
            talentPoolMapper.updateById(talentPool);
            return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.UPDATE_SUCCESS_MSG);
        }else {
            talentPoolMapper.insert(talentPool);
            return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.INSERT_SUCCESS_MSG);
        }
    }


    @Override
    public void delete(int id) {
        talentPoolMapper.deleteById(id);
    }
}
