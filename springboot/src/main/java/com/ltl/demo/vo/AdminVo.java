package com.ltl.demo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ltl.demo.validation.ValidGroup;
import com.ltl.demo.validation.constraints.NumScope;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class AdminVo {
    @NumScope(groups = {ValidGroup.Crud.Update.class}, message = "请输入正确格式ID")
    private Long id;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名是必填项")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码是必填项")
    private String password;

    @ApiModelProperty(value = "昵称")
    @NotBlank(message = "密码是必填项")
    private String nickname;

    @ApiModelProperty(value = "权限列表")
    @NotEmpty(message = "至少选择一项权限")
    private List<String> power;
}
