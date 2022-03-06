package com.ltl.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.ltl.demo.validation.constraints.NumScope;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author ltl
 * @since 2022-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Recruitment对象", description="")
public class Recruitment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "招聘部门")
    @NotBlank(message = "招聘部门是必填项")
    private String rDeptName;

    @ApiModelProperty(value = "招聘职位")
    @NotBlank(message = "招聘职位是必填项")
    private String rPosition;

    @ApiModelProperty(value = "招聘人数")
    @NumScope(message = "请填写正确人数")
    private Integer rNum;

    @ApiModelProperty(value = "薪资范围")
    @NotBlank(message = "薪资范围是必填项")
    private String salaryScope;

    @ApiModelProperty(value = "年龄要求")
    @NotBlank(message = "年龄要求是必填项")
    private String ageScope;

    @ApiModelProperty(value = "学历要求")
    @NotBlank(message = "学历要求是必填项")
    private String education;

    @ApiModelProperty(value = "工作年限要求")
    @NotBlank(message = "工作年限要求是必填项")
    private String workingYears;

    @ApiModelProperty(value = "工作技能要求")
    @NotBlank(message = "工作技能要求是必填项")
    private String workingSkill;

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
