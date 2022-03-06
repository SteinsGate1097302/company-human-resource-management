package com.ltl.demo.vo;

import com.ltl.demo.validation.constraints.NumScope;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ApplyPageVo {
    @ApiModelProperty(value = "当前页码")
    @NumScope(message = "请填写正确currentPage")
    private Integer currentPage;

    @ApiModelProperty(value = "每页显示数据量")
    @NumScope(message = "请填写正确pageSize")
    private Integer pageSize;

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "姓名")
    @Length(max = 10,message = "姓名长度不能>10")
    private String name;

    @ApiModelProperty(value = "手机号")
    @Length(max = 10,message = "手机号长度不能>11")
    private String phone;

    @ApiModelProperty(value = "应聘结果")
    private String result;
}
