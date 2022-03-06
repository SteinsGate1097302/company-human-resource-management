package com.ltl.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ltl.demo.common.constant.ErrorConstant;
import com.ltl.demo.entity.Admin;
import com.ltl.demo.exception.MyException;
import com.ltl.demo.mapper.AdminMapper;
import com.ltl.demo.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ltl.demo.util.AdminUtil;
import com.ltl.demo.util.Des3Util;
import com.ltl.demo.vo.AdminPageVo;
import com.ltl.demo.vo.AdminVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ltl
 * @since 2022-01-14
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private Des3Util des3Util;

    @Override
    public void login(Admin admin) throws MyException {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Admin::getUsername,admin.getUsername());
        Admin admin1 = adminMapper.selectOne(queryWrapper);
        if (admin1 == null){
            throw new MyException(ErrorConstant.USERNAME_OR_PWD_ERROR_CODE,ErrorConstant.USERNAME_OR_PWD_ERROR_MSG);
        }else if (! des3Util.undoDes3(admin1.getPassword()).equals(admin.getPassword())){
            throw new MyException(ErrorConstant.USERNAME_OR_PWD_ERROR_CODE,ErrorConstant.USERNAME_OR_PWD_ERROR_MSG);
        }

    }

    @Override
    public int addOrUpdate(AdminVo adminVo) throws MyException {
        //数据校验
        if (adminMapper.selectCntByUserName(adminVo) > 0){
            throw new MyException(ErrorConstant.USERNAME_EXIST_CODE,ErrorConstant.USERNAME_EXIST_MSG);
        }

        if (adminVo.getId() != null){
            Admin admin = AdminUtil.adminVoToAdminConvert(adminVo);
            return adminMapper.updateById(admin);
        }else {
            Admin admin = AdminUtil.adminVoToAdminConvert(adminVo);
            admin.setPassword(des3Util.doDes3(admin.getPassword()));
            adminMapper.insert(admin);
            return 0;
        }

    }


    @Override
    public IPage<AdminVo> queryPage(AdminPageVo adminPageVo) {
        IPage<Admin> page= new Page<>(adminPageVo.getCurrentPage(),adminPageVo.getPageSize());
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        //只查询普通管理员
        queryWrapper.lambda().eq(Admin::getAccountType,"N");
        //校验用户名和昵称
        if (adminPageVo.getUsername() != null){
            queryWrapper.lambda().like(Admin::getUsername,adminPageVo.getUsername());
        }
        if (adminPageVo.getNickname() != null){
            queryWrapper.lambda().like(Admin::getNickname,adminPageVo.getNickname());
        }

        //把分页结果的Admin对象转换成AdminVo对象
        IPage<Admin> adminPage = adminMapper.selectPage(page, queryWrapper);
        IPage<AdminVo> adminVoIPage = new Page<>();
        //复制page对象
        BeanUtils.copyProperties(adminPage,adminVoIPage);
        //替换page的records
        List<AdminVo> list = new ArrayList<>();
        for (Admin admin : adminPage.getRecords()) {
            admin.setPassword(des3Util.undoDes3(admin.getPassword()));
            list.add(AdminUtil.adminToAdminVoConvert(admin));
        }
        adminVoIPage.setRecords(list);
        return adminVoIPage;
    }


    @Override
    public AdminVo queryById(int id) {
        Admin admin = adminMapper.selectById(id);
        return AdminUtil.adminToAdminVoConvert(admin);
    }


    @Override
    public AdminVo queryByUsername(String username) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Admin::getUsername,username);
        return AdminUtil.adminToAdminVoConvert(adminMapper.selectOne(queryWrapper));
    }


    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }


}
