package com.zzh.controller;

import com.zzh.dao.entity.Setmeal;
import com.zzh.service.SetmealService;
import com.zzh.vo.baseVo.R;
import com.zzh.vo.baseVo.SetmealDto;
import com.zzh.vo.reqVo.PageQueryReqVo;
import com.zzh.vo.respVo.PageQueryRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/setmeal")
@RestController
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @PostMapping
    public R<String> addMeal(@RequestBody SetmealDto setmealDto)
    {
        return setmealService.addMeal(setmealDto);
    }

    @GetMapping("/page")
    public R<PageQueryRespVo<SetmealDto>> pageQuery(PageQueryReqVo pageQueryReqVo)
    {
        return setmealService.pageQuery(pageQueryReqVo);
    }

    @DeleteMapping
    public R<String> setmealDelete(@RequestParam List<Long> ids)
    {
        log.info(ids.toString());
        return setmealService.setmealDelete(ids);
    }

    @GetMapping
    @RequestMapping("/{id}")
    public R<SetmealDto> getSetmealBySetmealId(@PathVariable Long id)
    {
        return setmealService.getSetmealBySetmealId(id);
    }

    @PutMapping
    public R<String> updateBySetmealId(@RequestBody SetmealDto setmealDto)
    {
        setmealService.updateById(setmealDto);
        return R.success("更新成功");
    }

    @GetMapping
    @RequestMapping("/list")
    public R<List<Setmeal>> getSetmealByCategoryId(@RequestParam Long categoryId)
    {
        return setmealService.getSetmealByCategoryId(categoryId);
    }

}
