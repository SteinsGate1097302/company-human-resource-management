package com.ltl.demo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ltl.demo.common.constant.StatusConstant;
import com.ltl.demo.entity.TrainingPerform;
import com.ltl.demo.exception.MyException;
import com.ltl.demo.service.TrainingPerformService;
import com.ltl.demo.service.TrainingPlanService;
import com.ltl.demo.vo.Result;
import com.ltl.demo.vo.TrainingPerformPageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ltl
 * @since 2022-03-02
 */
@RestController
@RequestMapping("/trainingPerform")
@Validated
@Slf4j
public class TrainingPerformController {
    @Autowired
    private TrainingPerformService trainingPerformService;

    @GetMapping("/queryPage")
    public Result<IPage<TrainingPerform>> queryPage(TrainingPerformPageVo trainingPerformPageVo){
        IPage<TrainingPerform> page = trainingPerformService.queryPage(trainingPerformPageVo);
        return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.QUERY_SUCCESS_MSG,page);
    }

    @PostMapping("/addOrUpdate")
    public Result<?> addOrUpdate(@Validated @RequestBody TrainingPerform trainingPerform) throws MyException {
        return trainingPerformService.addOrUpdate(trainingPerform);
    }

    @DeleteMapping("/deleteById")
    public Result<?> deleteById(int id){
        trainingPerformService.deleteById(id);
        return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.DELETE_SUCCESS_MSG);
    }
}

