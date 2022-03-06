package com.ltl.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ltl.demo.common.constant.ErrorConstant;
import com.ltl.demo.common.constant.StatusConstant;
import com.ltl.demo.entity.TrainingPlan;
import com.ltl.demo.exception.MyException;
import com.ltl.demo.mapper.TrainingPlanMapper;
import com.ltl.demo.service.TrainingPlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ltl.demo.vo.Result;
import com.ltl.demo.vo.TrainingPlanPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ltl
 * @since 2022-02-28
 */
@Service
public class TrainingPlanServiceImpl extends ServiceImpl<TrainingPlanMapper, TrainingPlan> implements TrainingPlanService {
    @Autowired
    private TrainingPlanMapper trainingPlanMapper;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public IPage<TrainingPlan> queryPage(TrainingPlanPageVo trainingPlanPageVo) {
        IPage<TrainingPlan> page = new Page<>(trainingPlanPageVo.getCurrentPage(),trainingPlanPageVo.getPageSize());
        QueryWrapper<TrainingPlan> queryWrapper = new QueryWrapper<>();

        if (trainingPlanPageVo.getTitle() != null && trainingPlanPageVo.getTitle().length() != 0){
            queryWrapper.lambda().like(TrainingPlan::getTitle,trainingPlanPageVo.getTitle());
        }
        if (trainingPlanPageVo.getLocation() != null && trainingPlanPageVo.getLocation().length() != 0){
            queryWrapper.lambda().like(TrainingPlan::getLocation,trainingPlanPageVo.getLocation());
        }

        //判断时间在区间内
        log.error(trainingPlanPageVo.toString());
        try {
            if (trainingPlanPageVo.getStartTime() != null) {
                queryWrapper.apply("date_format('" + format.format(trainingPlanPageVo.getStartTime()) + "','%Y-%m-%d') <= date_format(start_time,'%Y-%m-%d')");
            }
            if (trainingPlanPageVo.getEndTime() != null){
                queryWrapper.apply("date_format('" + format.format(trainingPlanPageVo.getEndTime()) + "','%Y-%m-%d') >= date_format(end_time,'%Y-%m-%d')");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return trainingPlanMapper.selectPage(page,queryWrapper);
    }

    /**
     * 更新或新增培训计划信息
     *
     * @param trainingPlan
     * @return
     */
    @Override
    public Result<?> addOrUpdate(TrainingPlan trainingPlan) throws MyException{
        //校验pNo和title是否重复
        if (trainingPlanMapper.selectCount(new QueryWrapper<TrainingPlan>().lambda().eq(TrainingPlan::getTitle,trainingPlan.getTitle()).ne(TrainingPlan::getId,trainingPlan.getId())) > 0){
            throw new MyException(ErrorConstant.TRAINING_PLAN_TITLE_EXIT_ERROR_CODE,ErrorConstant.TRAINING_PLAN_TITLE_EXIT_ERROR_MSG);
        }
        if (trainingPlanMapper.selectCount(new QueryWrapper<TrainingPlan>().lambda().eq(TrainingPlan::getPNo,trainingPlan.getPNo()).ne(TrainingPlan::getId,trainingPlan.getId())) > 0){
            throw new MyException(ErrorConstant.TRAINING_PLAN_NO_EXIT_ERROR_CODE,ErrorConstant.TRAINING_PLAN_NO_EXIT_ERROR_MSG);
        }

        if (trainingPlan.getId() != null){
            trainingPlanMapper.updateById(trainingPlan);
            return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.UPDATE_SUCCESS_MSG);
        }else {
            trainingPlanMapper.insert(trainingPlan);
            return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.INSERT_SUCCESS_MSG);
        }
    }


    @Override
    public void deleteById(int id) {
        trainingPlanMapper.deleteById(id);
    }
}
