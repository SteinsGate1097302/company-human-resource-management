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

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author ltl
 * @since 2022-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TrainingPerform对象", description="")
public class TrainingPerform implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "培训编号")
    @NotBlank(message = "培训编号是必填项")
    private String pNo;

    @TableField(exist = false)
    @ApiModelProperty(value = "培训标题")
    private String title;

    @ApiModelProperty(value = "主持人")
    @NotBlank(message = "培训编号是必填项")
    private String host;

    @ApiModelProperty(value = "是否成功召开 T-成功，F-未召开")
    @EnumString(value = {"T","F"},message = "T-成功，F-未召开")
    private String success;

    @ApiModelProperty(value = "参与人数")
    @NumScope(message = "请输入正确参与人数")
    private Integer participationNum;

    @ApiModelProperty(value = "培训花销")
    private Double cost;

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
