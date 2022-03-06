package com.ltl.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ltl.demo.entity.TrainingPerform;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ltl.demo.exception.MyException;
import com.ltl.demo.vo.Result;
import com.ltl.demo.vo.TrainingPerformPageVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ltl
 * @since 2022-03-02
 */
public interface TrainingPerformService extends IService<TrainingPerform> {

    /**
     * 分页查询培训实施信息
     * @param trainingPerformPageVo
     * @return
     */
    IPage<TrainingPerform> queryPage(TrainingPerformPageVo trainingPerformPageVo);


    /**
     * 新增或更新实施信息
     * @param trainingPerform
     * @return
     */
    Result<?> addOrUpdate(TrainingPerform trainingPerform) throws MyException;

    /**
     * 根据id删除培训实施信息
     * @param id
     */
    void deleteById(int id);
}
