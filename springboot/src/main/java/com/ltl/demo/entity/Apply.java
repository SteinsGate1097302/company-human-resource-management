package com.ltl.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
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
 * @since 2022-02-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Apply对象", description="")
public class Apply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "应聘者姓名")
    @Length(max = 10,message = "应聘者姓名长度不能超过20")
    @NotBlank(message = "应聘者姓名是必填项")
    private String name;

    @ApiModelProperty(value = "应聘者年龄")
    @NumScope(max = 65,message = "请填写正确年龄(<65)")
    private Integer age;

    @ApiModelProperty(value = "应聘者性别")
    @EnumString(value = {"M","F"})
    private String sex;

    @ApiModelProperty(value = "应聘者学历")
    @NotBlank(message = "应聘者学历是必填项")
    private String education;


    @ApiModelProperty(value = "应聘部门NO")
    @NotBlank(message = "应聘部门是必填项")
    private String deptno;

    @TableField(exist = false)
    @ApiModelProperty(value = "应聘部门名称")
    private String dname;

    @ApiModelProperty(value = "应聘者联系方式")
    @NotBlank(message = "应聘者联系方式是必填项")
    private String phone;

    @ApiModelProperty(value = "简历路径")
    @NotBlank(message = "简历路径是必填项")
    private String resume;

    @ApiModelProperty(value = "当前所处进度")
    @NumScope(max = 4,message = "请填写正确进度(<4)")
    private Integer progress;

    @ApiModelProperty(value = "当前状态 wait-审核中  error-未通过")
    @EnumString(value = {"wait","error"},message = "应聘者当前状态只能填wait/error")
    private String status;

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
