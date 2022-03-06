package com.ltl.demo.vo;

import com.ltl.demo.validation.constraints.NumScope;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RecruitmentPageVo {
    @ApiModelProperty(value = "当前页码")
    @NumScope(message = "请填写正确currentPage")
    private Integer currentPage;

    @ApiModelProperty(value = "每页显示数据量")
    @NumScope(message = "请填写正确pageSize")
    private Integer pageSize;

    @ApiModelProperty(value = "部门名称")
    private String department;

    @ApiModelProperty(value = "职位名称")
    private String position;
}
