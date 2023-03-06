package com.zzh.controller;

import com.zzh.dao.entity.Dish;
import com.zzh.service.DishService;
import com.zzh.vo.baseVo.R;
import com.zzh.vo.reqVo.AddDishReqVo;
import com.zzh.vo.reqVo.PageQueryReqVo;
import com.zzh.vo.respVo.DishListRespVo;
import com.zzh.vo.respVo.GetOneDishRespVo;
import com.zzh.vo.respVo.PageQueryRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/dish")
@RestController
public class DishController {
    @Autowired
    private DishService dishService;

    //添加菜品，包含口味和数量
    @PostMapping
    public R<String> addDish(@RequestBody AddDishReqVo addDishReqVo)
    {
        return dishService.addDish(addDishReqVo);
    }

    //查询菜品，包含种类名
    @RequestMapping("/page")
    @GetMapping
    public R<PageQueryRespVo<DishListRespVo>> selectDishList(PageQueryReqVo pageQueryReqVo){
        return dishService.selectAll(pageQueryReqVo);
    }

    //根据id查到单个菜品，修改菜品的时候用
    @GetMapping
    @RequestMapping("/{id}")
    public R<GetOneDishRespVo> getDishById(@PathVariable Long id){
        return dishService.getDishById(id);
    }

    //根据菜品的Id修改菜品
    @PutMapping
    public R<String> updateDishById(@RequestBody AddDishReqVo addDishReqVo)
    {
        return dishService.updateDishById(addDishReqVo);
    }

    //给出某个种类下的所有菜品
    @GetMapping
    @RequestMapping("/list")
    public R<List<GetOneDishRespVo>> getDishListByCategoryId(Long categoryId)
    {
        return dishService.selectByCategoryId(categoryId);
    }

    //停售
    @GetMapping
    @RequestMapping("/status/0")
    public R<String> changeStatus(@RequestParam List<Long> ids)
    {
        return dishService.changeStatusToDisAble(ids);
    }

    //起售
    @GetMapping
    @RequestMapping("/status/1")
    public R<String> changeStatus1(@RequestParam List<Long> ids)
    {
        return dishService.changeStatusToEnAble(ids);
    }

    @DeleteMapping
    public R<String> deleteDish(@RequestParam List<Long> ids)
    {
        return dishService.deleteByids(ids);
    }
}
