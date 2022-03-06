package com.ltl.demo.mapper;

import com.ltl.demo.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ltl.demo.vo.AdminVo;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ltl
 * @since 2022-01-14
 */

@Component
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 根据用户名查询数据条数
     * @param adminVo
     * @return
     */
    Integer selectCntByUserName(AdminVo adminVo);
}
