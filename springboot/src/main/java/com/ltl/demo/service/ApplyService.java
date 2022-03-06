package com.ltl.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ltl.demo.entity.Apply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ltl.demo.exception.MyException;
import com.ltl.demo.vo.ApplyPageVo;
import com.ltl.demo.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ltl
 * @since 2022-02-25
 */
public interface ApplyService extends IService<Apply> {

    /**
     * 分页查询应聘信息
     * @param applyPageVo
     * @return
     */
    IPage<Apply> queryPage(ApplyPageVo applyPageVo);


    /**
     * 新增或更新应聘信息
     * @param apply
     * @return
     */
    Result<?> addOrUpdate(Apply apply) throws MyException;

}
