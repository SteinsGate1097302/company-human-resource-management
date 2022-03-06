package com.ltl.demo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ltl.demo.common.constant.StatusConstant;
import com.ltl.demo.entity.TalentPool;
import com.ltl.demo.exception.MyException;
import com.ltl.demo.service.TalentPoolService;
import com.ltl.demo.vo.Result;
import com.ltl.demo.vo.TalentPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ltl
 * @since 2022-02-23
 */
@RestController
@RequestMapping("/talentPool")
public class TalentPoolController {
    @Autowired
    private TalentPoolService talentPoolService;

    @GetMapping("/queryPage")
    public Result<IPage<TalentPool>> queryPage(TalentPageVo talentPageVo){
        IPage<TalentPool> page = talentPoolService.queryPage(talentPageVo);
        return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.QUERY_SUCCESS_MSG,page);
    }

    @PostMapping("/addOrUpdate")
    public Result<?> addOrUpdate(@Validated @RequestBody TalentPool talentPool) throws MyException {
        return talentPoolService.addOrUpdate(talentPool);

    }

    @DeleteMapping("/delete")
    public Result<?> delete(int id){
        talentPoolService.delete(id);
        return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.DELETE_SUCCESS_MSG);
    }
}

