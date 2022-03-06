package com.ltl.demo.util;

import com.ltl.demo.entity.Admin;
import com.ltl.demo.vo.AdminVo;

import java.util.ArrayList;
import java.util.List;

public  class AdminUtil{
    public static AdminVo adminToAdminVoConvert(Admin admin){
        AdminVo adminVo = new AdminVo();
        adminVo.setId(admin.getId());
        adminVo.setUsername(admin.getUsername());
        adminVo.setPassword(admin.getPassword());
        adminVo.setNickname(admin.getNickname());
        List<String> power = new ArrayList<>();
        if (admin.getPersonnelStatus().equals("1")){
            power.add("personnel");
        }
        if (admin.getRecruitmentStatus().equals("1")){
            power.add("recruitment");
        }
        if (admin.getTrainingStatus().equals("1")){
            power.add("training");
        }
        if (admin.getSalaryStatus().equals("1")){
            power.add("salary");
        }
        adminVo.setPower(power);
        return adminVo;
    }

    public static Admin adminVoToAdminConvert(AdminVo adminVo){
        Admin admin = new Admin();
        admin.setId(adminVo.getId());
        admin.setUsername(adminVo.getUsername());
        admin.setPassword(adminVo.getPassword());
        admin.setNickname(adminVo.getNickname());
        List<String> powers = adminVo.getPower();
        if (powers.contains("personnel")) {
            admin.setPersonnelStatus("1");
        } else {
            admin.setPersonnelStatus("0");
        }
        if (powers.contains("recruitment")) {
            admin.setRecruitmentStatus("1");
        } else {
            admin.setRecruitmentStatus("0");
        }
        if (powers.contains("training")) {
            admin.setTrainingStatus("1");
        } else {
            admin.setTrainingStatus("0");
        }
        if (powers.contains("salary")) {
            admin.setSalaryStatus("1");
        } else {
            admin.setSalaryStatus("0");
        }
        return admin;
    }
}
