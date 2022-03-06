package com.ltl.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.beans.Transient;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.ltl.demo.validation.constraints.EnumString;
import com.ltl.demo.validation.constraints.NumScope;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author ltl
 * @since 2022-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Employee对象", description="")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名是必填项")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码是必填项")
    @Length(min = 6,max = 20,message = "密码长度应为6-20")
    private String password;

    @ApiModelProperty(value = "昵称")
    @NotBlank(message = "昵称是必填项")
    @Length(min = 2,max = 20,message = "昵称长度应为2-20")
    private String nickname;

    @ApiModelProperty(value = "性别 M/F")
    @EnumString(value = {"M","F"},message = "性别只能是M/F")
    private String sex;

    @ApiModelProperty(value = "年龄")
    @NumScope(max = 65,message = "请填写正确格式年龄( < 65 )")
    private Integer age;

    @ApiModelProperty(value = "工资")
    @TableField(fill = FieldFill.INSERT)
    private double salary;

    @ApiModelProperty(value = "部门ID")
    private String department;

    @TableField(exist = false)
    @ApiModelProperty(value = "部门Name")
    private String departmentName;

    @ApiModelProperty(value = "入职时间")
    @TableField(fill = FieldFill.INSERT)
    private Date inductionTime;

    @ApiModelProperty(value = "离职时间")
    private Date departureTime;

    @ApiModelProperty(value = "逻辑删除，代表离职状态 0-在职 1-离职")
//    @TableLogic
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
