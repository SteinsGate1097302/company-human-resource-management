package com.ltl.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ltl.demo.entity.TrainingPlan;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ltl.demo.exception.MyException;
import com.ltl.demo.vo.Result;
import com.ltl.demo.vo.TrainingPlanPageVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ltl
 * @since 2022-02-28
 */
public interface TrainingPlanService extends IService<TrainingPlan> {

    /**
     * 分页查询培训计划信息
     * @param trainingPlanPageVo
     * @return
     */
    IPage<TrainingPlan> queryPage(TrainingPlanPageVo trainingPlanPageVo);

    /**
     * 更新或新增培训计划信息
     * @return
     */
    Result<?> addOrUpdate(TrainingPlan trainingPlan) throws MyException;


    /**
     * 根据ID删除培训计划信息
     * @param id
     */
    void deleteById(int id);
}
