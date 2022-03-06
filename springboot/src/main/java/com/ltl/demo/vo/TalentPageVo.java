package com.ltl.demo.vo;

import com.ltl.demo.validation.constraints.NumScope;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
@Data
public class TalentPageVo {
    @ApiModelProperty(value = "当前页码")
    @NumScope(message = "请填写正确currentPage")
    private Integer currentPage;

    @ApiModelProperty(value = "每页显示数据量")
    @NumScope(message = "请填写正确pageSize")
    private Integer pageSize;

    @ApiModelProperty(value = "编号")
    @Length(max = 6,message = "编号长度不能超过6")
    private String talentNo;

    @ApiModelProperty(value = "姓名")
    @Length(max = 20,message = "姓名长度不能超过20")
    private String nickname;

    @ApiModelProperty(value = "国籍")
    private String nationality;

    @ApiModelProperty(value = "人才等级")
    private String level;
}
