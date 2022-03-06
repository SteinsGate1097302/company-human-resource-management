package com.ltl.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ltl.demo.common.constant.ErrorConstant;
import com.ltl.demo.common.constant.StatusConstant;
import com.ltl.demo.entity.TrainingPerform;
import com.ltl.demo.entity.TrainingPlan;
import com.ltl.demo.exception.MyException;
import com.ltl.demo.mapper.TrainingPerformMapper;
import com.ltl.demo.mapper.TrainingPlanMapper;
import com.ltl.demo.service.TrainingPerformService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ltl.demo.vo.Result;
import com.ltl.demo.vo.TrainingPerformPageVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ltl
 * @since 2022-03-02
 */
@Service
public class TrainingPerformServiceImpl extends ServiceImpl<TrainingPerformMapper, TrainingPerform> implements TrainingPerformService {
    @Autowired
    private TrainingPerformMapper trainingPerformMapper;
    @Autowired
    private TrainingPlanMapper trainingPlanMapper;

    @Override
    public IPage<TrainingPerform> queryPage(TrainingPerformPageVo trainingPerformPageVo) {
        IPage<TrainingPerform> page = new Page<>(trainingPerformPageVo.getCurrentPage(),trainingPerformPageVo.getPageSize());
        QueryWrapper<TrainingPerform> queryWrapper =new QueryWrapper<>();

        if (StringUtils.isNotBlank(trainingPerformPageVo.getPNo())){
            queryWrapper.lambda().like(TrainingPerform::getPNo,trainingPerformPageVo.getPNo());
        }
//        if (StringUtils.isNotBlank(trainingPerformPageVo.getTitle())){
//            queryWrapper.lambda().like(TrainingPerform::getTitle,trainingPerformPageVo.getTitle());
//        }
        if (StringUtils.isNotBlank(trainingPerformPageVo.getSuccess())){
            queryWrapper.lambda().eq(TrainingPerform::getSuccess,trainingPerformPageVo.getSuccess());
        }

        //查询培训标题
        IPage<TrainingPerform> page1 = trainingPerformMapper.selectPage(page, queryWrapper);
        for (TrainingPerform record : page1.getRecords()) {
            record.setTitle(trainingPlanMapper.selectOne(new QueryWrapper<TrainingPlan>().lambda().eq(TrainingPlan::getPNo,record.getPNo())).getTitle());
        }

        return page1;
    }



    @Override
    public Result<?> addOrUpdate(TrainingPerform trainingPerform) throws MyException{
        //校验pno是否重复
        if (trainingPerformMapper.selectCount(new QueryWrapper<TrainingPerform>().lambda().eq(TrainingPerform::getPNo,trainingPerform.getPNo()).ne(TrainingPerform::getId,trainingPerform.getId())) > 0){
            throw new MyException(ErrorConstant.TRAINING_PLAN_NO_EXIT_ERROR_CODE,ErrorConstant.TRAINING_PLAN_NO_EXIT_ERROR_MSG+" -- "+trainingPerform.getPNo());
        }
        //查询pno是否存在于培训计划表数据中
        if (trainingPlanMapper.selectCount(new QueryWrapper<TrainingPlan>().lambda().eq(TrainingPlan::getPNo,trainingPerform.getPNo())) == 0){
            throw new MyException(ErrorConstant.TRAINING_PLAN_NOT_EXIT_ERROR_CODE,ErrorConstant.TRAINING_PLAN_NOT_EXIT_ERROR_MSG+" -- "+trainingPerform.getPNo());
        }

        if (trainingPerform.getId() != null){
            trainingPerformMapper.updateById(trainingPerform);
            return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.UPDATE_SUCCESS_MSG);
        }else {
            trainingPerformMapper.insert(trainingPerform);
            return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.INSERT_SUCCESS_MSG);
        }
    }



    @Override
    public void deleteById(int id) {
        trainingPerformMapper.deleteById(id);
    }
}
