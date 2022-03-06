package com.ltl.demo.vo;

import com.ltl.demo.validation.constraints.NumScope;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class AdminPageVo {
    @ApiModelProperty(value = "当前页码")
    @NumScope(message = "请填写正确currentPage")
    private Integer currentPage;

    @ApiModelProperty(value = "每页显示数据量")
    @NumScope(message = "请填写正确pageSize")
    private Integer pageSize;

    @ApiModelProperty(value = "用户名")
    @Length(max = 20,message = "username长度不能超过20")
    private String username;

    @ApiModelProperty(value = "昵称")
    @Length(max = 20,message = "nickname长度不能超过20")
    private String nickname;
}
