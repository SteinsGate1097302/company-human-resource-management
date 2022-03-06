package com.ltl.demo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ltl.demo.common.constant.StatusConstant;
import com.ltl.demo.entity.Recruitment;
import com.ltl.demo.service.RecruitmentService;
import com.ltl.demo.vo.RecruitmentPageVo;
import com.ltl.demo.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ltl
 * @since 2022-02-24
 */
@RestController
@RequestMapping("/recruitment")
public class RecruitmentController {
    @Autowired
    private RecruitmentService recruitmentService;

    @GetMapping("/queryPage")
    public Result<IPage<Recruitment>> queryPage(RecruitmentPageVo recruitmentPageVo){
        IPage<Recruitment> page = recruitmentService.queryPage(recruitmentPageVo);
        return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.QUERY_SUCCESS_MSG,page);
    }

    @PostMapping("/addOrUpdate")
    public Result<?> addOrUpdate(@Validated @RequestBody Recruitment recruitment){
        return recruitmentService.addOrUpdate(recruitment);

    }

    @DeleteMapping("/delete")
    public Result<?> delete(int id){
        recruitmentService.delete(id);
        return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.DELETE_SUCCESS_MSG);
    }
}

