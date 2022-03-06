package com.ltl.demo.vo;

import com.ltl.demo.entity.Employee;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BatchUpdateEmpVo {
    @ApiModelProperty(value = "需要调整薪资的员工列表")
    List<Employee> employees;

    @ApiModelProperty(value = "薪资调整的百分比")
    Integer percentum;

    @ApiModelProperty(value = "薪资调整的数字")
    Integer salaryNum;
}
