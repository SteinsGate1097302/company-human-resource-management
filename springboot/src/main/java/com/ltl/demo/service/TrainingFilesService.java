package com.ltl.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ltl.demo.entity.TrainingFiles;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ltl.demo.exception.MyException;
import com.ltl.demo.vo.Result;
import com.ltl.demo.vo.TrainingFilesPageVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ltl
 * @since 2022-03-03
 */
public interface TrainingFilesService extends IService<TrainingFiles> {

    /**
     * 分页查询培训文件信息
     * @param trainingFilesPageVo
     * @return
     */
    IPage<TrainingFiles> queryPage(TrainingFilesPageVo trainingFilesPageVo);


    /**
     * 新增或更新培训文件信息
     * @param trainingFiles
     * @return
     */
    Result<?> addOrUpdate(TrainingFiles trainingFiles) throws MyException;

    /**
     * 根据ID删除培训文件信息
     * @param id
     */
    void deleteById(int id);
}
