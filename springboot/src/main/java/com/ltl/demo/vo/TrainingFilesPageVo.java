package com.ltl.demo.vo;

import com.ltl.demo.validation.constraints.NumScope;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TrainingFilesPageVo {
    @ApiModelProperty(value = "当前页码")
    @NumScope(message = "请填写正确currentPage")
    private Integer currentPage;

    @ApiModelProperty(value = "每页显示数据量")
    @NumScope(message = "请填写正确pageSize")
    private Integer pageSize;

    @ApiModelProperty(value = "培训编号")
    @NotBlank(message = "培训编号是必填项")
    private String pNo;
}
