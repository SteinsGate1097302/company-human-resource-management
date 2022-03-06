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
 * @since 2022-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TalentPool对象", description="")
public class TalentPool implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "编号")
    @NotBlank(message = "编号不能为空")
    private String talentNo;

    @ApiModelProperty(value = "姓名")
    @NotBlank(message = "姓名不能为空")
    private String talentName;

    @ApiModelProperty(value = "年龄")
    @NumScope(min = 0,max = 100,message = "请填写正确格式年龄")
    private Integer talentAge;

    @ApiModelProperty(value = "性别")
    @NotBlank(message = "性别不能为空")
    private String talentSex;

    @ApiModelProperty(value = "照片")
    @NotBlank(message = "照片不能为空")
    private String talentPhoto;

    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "手机号不能为空")
    private String talentPhone;

    @ApiModelProperty(value = "所属国籍")
    @NotBlank(message = "所属国籍不能为空")
    private String talentNationality;

    @ApiModelProperty(value = "人才等级")
    @NotBlank(message = "人才等级不能为空")
    private String talentLevel;

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
