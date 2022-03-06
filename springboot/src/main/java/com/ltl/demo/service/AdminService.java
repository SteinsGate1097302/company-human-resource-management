package com.ltl.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ltl.demo.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ltl.demo.exception.MyException;
import com.ltl.demo.vo.AdminPageVo;
import com.ltl.demo.vo.AdminVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ltl
 * @since 2022-01-14
 */
public interface AdminService extends IService<Admin> {

    /**
     * 管理员登陆
     * @param admin
     */
    void login(Admin admin) throws MyException;

    /**
     * 新增或更新管理员
     * @param adminVo
     */
    int addOrUpdate(AdminVo adminVo) throws MyException;


    /**
     * 分页查询
     * @param adminPageVo
     * @return
     */
    IPage<AdminVo> queryPage(AdminPageVo adminPageVo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    AdminVo queryById(int id);

    /**
     * 根据username查询
     * @param username
     * @return
     */
    AdminVo queryByUsername(String username);

    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(Integer id);
}
