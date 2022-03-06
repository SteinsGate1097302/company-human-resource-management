package com.ltl.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author ltl
 * @since 2022-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Admin对象", description="")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "管理员类型 S-超级管理员 N-普通管理员")
    @TableField(fill = FieldFill.INSERT)
    private String accountType;

    @ApiModelProperty(value = "人事管理权限 0-不可用，1-可用")
    private String personnelStatus;

    @ApiModelProperty(value = "招聘管理权限 0-不可用，1-可用")
    private String recruitmentStatus;

    @ApiModelProperty(value = "培训管理权限 0-不可用，1-可用")
    private String trainingStatus;

    @ApiModelProperty(value = "薪酬管理权限 0-不可用，1-可用")
    private String salaryStatus;

    @ApiModelProperty(value = "逻辑删除，代表离职状态 0-在职 1-离职")
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleted;

    private String remark;

    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long lastUpdateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateTime;


}
