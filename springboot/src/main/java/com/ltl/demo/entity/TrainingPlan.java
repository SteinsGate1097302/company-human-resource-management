package com.ltl.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author ltl
 * @since 2022-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TrainingPlan对象", description="")
public class TrainingPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "培训编号")
    @NotBlank(message = "培训编号是必填项")
    private String pNo;

    @ApiModelProperty(value = "培训标题")
    @NotBlank(message = "培训标题是必填项")
    private String title;

    @ApiModelProperty(value = "培训事件时间线")
    @NotBlank(message = "培训事件时间线是必填项")
    private String timeline;

    @ApiModelProperty(value = "培训开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future(message = "开始时间不能小于当前时间")
    private Date startTime;

    @ApiModelProperty(value = "培训结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future(message = "结束时间不能小于当前时间")
    private Date endTime;

    @ApiModelProperty(value = "培训地址")
    @NotBlank(message = "培训地址是必填项")
    private String location;

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
