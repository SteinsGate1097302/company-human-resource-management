package com.ltl.demo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ltl.demo.common.constant.StatusConstant;
import com.ltl.demo.entity.Apply;
import com.ltl.demo.exception.MyException;
import com.ltl.demo.service.ApplyService;
import com.ltl.demo.vo.ApplyPageVo;
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
 * @since 2022-02-25
 */
@RestController
@RequestMapping("/apply")
public class ApplyController {
    @Autowired
    private ApplyService applyService;

    @GetMapping("/queryPage")
    public Result<IPage<Apply>> queryPage(ApplyPageVo applyPageVo){
        IPage<Apply> page = applyService.queryPage(applyPageVo);
        return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.QUERY_SUCCESS_MSG,page);
    }

    @PostMapping("/addOrUpdate")
    public Result<?> addOrUpdate(@Validated @RequestBody Apply apply) throws MyException {
        return applyService.addOrUpdate(apply);

    }
}

