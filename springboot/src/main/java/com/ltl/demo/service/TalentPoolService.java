package com.ltl.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ltl.demo.entity.TalentPool;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ltl.demo.exception.MyException;
import com.ltl.demo.vo.Result;
import com.ltl.demo.vo.TalentPageVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ltl
 * @since 2022-02-23
 */
public interface TalentPoolService extends IService<TalentPool> {

    /**
     * 分页查询人才信息
     * @return
     */
    IPage<TalentPool> queryPage(TalentPageVo talentPageVo);

    /**
     * 新增或更新人才信息
     * @param talentPool
     * @return
     */
    Result<?> addOrUpdate(TalentPool talentPool) throws MyException;

    /**
     * 删除人才信息
     * @param id
     */
    void delete(int id);
}
