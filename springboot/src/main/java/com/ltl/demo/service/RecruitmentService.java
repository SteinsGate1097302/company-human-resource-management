package com.ltl.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ltl.demo.entity.Recruitment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ltl.demo.vo.RecruitmentPageVo;
import com.ltl.demo.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ltl
 * @since 2022-02-24
 */
public interface RecruitmentService extends IService<Recruitment> {

    /**
     * 分页查询招聘信息
     * @return
     */
    IPage<Recruitment> queryPage(RecruitmentPageVo recruitmentPageVo);


    /**
     * 新增或更新招聘信息
     * @param recruitment
     * @return
     */
    Result<?> addOrUpdate(Recruitment recruitment);

    /**
     * 根据id删除招聘信息
     * @param id
     */
    void delete(int id);
}
