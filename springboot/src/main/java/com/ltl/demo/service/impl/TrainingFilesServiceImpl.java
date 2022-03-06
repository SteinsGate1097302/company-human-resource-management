package com.ltl.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ltl.demo.common.constant.ErrorConstant;
import com.ltl.demo.common.constant.StatusConstant;
import com.ltl.demo.entity.TrainingFiles;
import com.ltl.demo.entity.TrainingPlan;
import com.ltl.demo.exception.MyException;
import com.ltl.demo.mapper.TrainingFilesMapper;
import com.ltl.demo.mapper.TrainingPlanMapper;
import com.ltl.demo.service.TrainingFilesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ltl.demo.vo.Result;
import com.ltl.demo.vo.TrainingFilesPageVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Statement;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ltl
 * @since 2022-03-03
 */
@Service
public class TrainingFilesServiceImpl extends ServiceImpl<TrainingFilesMapper, TrainingFiles> implements TrainingFilesService {
    @Autowired
    private TrainingFilesMapper trainingFilesMapper;
    @Autowired
    private TrainingPlanMapper trainingPlanMapper;

    @Override
    public IPage<TrainingFiles> queryPage(TrainingFilesPageVo trainingFilesPageVo) {
        IPage<TrainingFiles> page = new Page<>(trainingFilesPageVo.getCurrentPage(),trainingFilesPageVo.getPageSize());
        QueryWrapper<TrainingFiles> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(trainingFilesPageVo.getPNo())){
            queryWrapper.lambda().like(TrainingFiles::getPNo,trainingFilesPageVo.getPNo());
        }

        IPage<TrainingFiles> page1 = trainingFilesMapper.selectPage(page,queryWrapper);
        for (TrainingFiles record : page1.getRecords()) {
            record.setTitle(trainingPlanMapper.selectOne(new QueryWrapper<TrainingPlan>().lambda().eq(TrainingPlan::getPNo,record.getPNo())).getTitle());
        }
        return page1;
    }


    @Override
    public Result<?> addOrUpdate(TrainingFiles trainingFiles) throws MyException{
        //校验pno是否存在
        if (trainingPlanMapper.selectCount(new QueryWrapper<TrainingPlan>().lambda().eq(TrainingPlan::getPNo,trainingFiles.getPNo())) == 0){
            throw new MyException(ErrorConstant.TRAINING_PLAN_NOT_EXIT_ERROR_CODE,ErrorConstant.TRAINING_PLAN_NOT_EXIT_ERROR_MSG+"pno: "+trainingFiles.getPNo());
        }
        //校验pno重复
        if (trainingFilesMapper.selectCount(new QueryWrapper<TrainingFiles>().lambda().eq(TrainingFiles::getPNo,trainingFiles.getPNo()).ne(TrainingFiles::getId,trainingFiles.getId())) > 0){
            throw new MyException(ErrorConstant.TRAINING_FILE_EXIT_ERROR_CODE,ErrorConstant.TRAINING_FILE_EXIT_ERROR_MSG);
        }

        if (trainingFiles.getId() != null){
            trainingFilesMapper.updateById(trainingFiles);
            return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.UPDATE_SUCCESS_MSG);
        }else {
            trainingFilesMapper.insert(trainingFiles);
            return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.INSERT_SUCCESS_MSG);
        }
    }


    @Override
    public void deleteById(int id) {
        trainingFilesMapper.deleteById(id);
    }
}
