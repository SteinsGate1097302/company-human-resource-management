package com.ltl.demo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ltl.demo.common.constant.StatusConstant;
import com.ltl.demo.entity.TrainingPlan;
import com.ltl.demo.exception.MyException;
import com.ltl.demo.service.TrainingPlanService;
import com.ltl.demo.vo.Result;
import com.ltl.demo.vo.TrainingPlanPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ltl
 * @since 2022-02-28
 */
@RestController
@RequestMapping("/trainingPlan")
@Validated
public class TrainingPlanController {
    @Autowired
    private TrainingPlanService trainingPlanService;

    @GetMapping("/queryPage")
    public Result<IPage<TrainingPlan>> queryPage(TrainingPlanPageVo trainingPlanPageVo){
        IPage<TrainingPlan> page = trainingPlanService.queryPage(trainingPlanPageVo);
        return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.QUERY_SUCCESS_MSG,page);
    }

    @PostMapping("/addOrUpdate")
    public Result<?> addOrUpdate(@Validated @RequestBody TrainingPlan trainingPlan) throws MyException {
        return trainingPlanService.addOrUpdate(trainingPlan);
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteById(@PathVariable int id){
        trainingPlanService.deleteById(id);
        return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.DELETE_SUCCESS_MSG);
    }
}

