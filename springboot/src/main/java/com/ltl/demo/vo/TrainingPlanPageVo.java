package com.ltl.demo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ltl.demo.validation.constraints.NumScope;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class TrainingPlanPageVo {
    @ApiModelProperty(value = "当前页码")
    @NumScope(message = "请填写正确currentPage")
    private Integer currentPage;

    @ApiModelProperty(value = "每页显示数据量")
    @NumScope(message = "请填写正确pageSize")
    private Integer pageSize;

    @ApiModelProperty(value = "培训标题")
    private String title;

    @ApiModelProperty(value = "培训地址")
    private String location;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;
}
