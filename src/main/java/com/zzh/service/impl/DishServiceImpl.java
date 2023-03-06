package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzh.dao.entity.Dish;
import com.zzh.dao.entity.DishFlavor;
import com.zzh.dao.entity.SetmealDish;
import com.zzh.dao.mapper.CategoryMapper;
import com.zzh.dao.mapper.DishMapper;
import com.zzh.service.DishFlavorService;
import com.zzh.service.DishService;
import com.zzh.service.SetmealDishService;
import com.zzh.vo.baseVo.R;
import com.zzh.vo.reqVo.AddDishReqVo;
import com.zzh.vo.reqVo.PageQueryReqVo;
import com.zzh.vo.respVo.DishListRespVo;
import com.zzh.vo.respVo.GetOneDishRespVo;
import com.zzh.vo.respVo.PageQueryRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishFlavorService dishFlavorService;
    public Boolean isBelongToCategory(LambdaQueryWrapper<Dish> lambdaQueryWrapper){
        return dishMapper.selectCount(lambdaQueryWrapper) > 0;
    }

    @Override
    public R<String> addDish(AddDishReqVo addDishReqVo) {
        //虽然只是继承了Dish，但还是能够存进去
        this.save(addDishReqVo);
        Long id = addDishReqVo.getId();
        List<DishFlavor> dishFlavor = addDishReqVo.getFlavors();
        List<DishFlavor> dishFlavors = dishFlavor.stream().peek(dishFlavor1 -> dishFlavor1.setDishId(id)).collect(Collectors.toList());
        dishFlavorService.saveBatch(dishFlavors);
        return R.success("插入成功");
    }

    @Override
    public R<PageQueryRespVo<DishListRespVo>> selectAll(PageQueryReqVo pageQueryReqVo) {
        IPage<Dish> page = new Page(pageQueryReqVo.getPage(), pageQueryReqVo.getPageSize());
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        PageQueryRespVo<DishListRespVo> pageQueryRespVo = new PageQueryRespVo<>();
        String name = pageQueryReqVo.getName();
        lambdaQueryWrapper.like(StringUtils.hasText(name), Dish::getName, name);
        dishMapper.selectPage(page,lambdaQueryWrapper);
        List<Dish> dishes = page.getRecords();
        List<DishListRespVo> dishListRespVos = dishes.stream().map(dish -> {
            DishListRespVo dishListRespVo = new DishListRespVo();
            BeanUtils.copyProperties(dish, dishListRespVo);
            String categoryName = categoryMapper.selectById(dish.getCategoryId()).getName();
            if(!StringUtils.hasText(categoryName))
            {
                dishListRespVo.setCategoryName("暂无分类");
            }
            dishListRespVo.setCategoryName(categoryName);
            return dishListRespVo;
        }).collect(Collectors.toList());
        pageQueryRespVo.setRecords(dishListRespVos);
        return R.success(pageQueryRespVo);
    }

    @Override
    public R<GetOneDishRespVo> getDishById(Long id) {
        GetOneDishRespVo getOneDishRespVo = new GetOneDishRespVo();
        Dish dish = this.getById(id);
        if(ObjectUtils.isEmpty(dish))
        {
            return R.error("没有该菜品");
        }
        BeanUtils.copyProperties(dish,getOneDishRespVo);
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> dishFlavors = dishFlavorService.list(lambdaQueryWrapper);
        if(ObjectUtils.isEmpty(dishFlavors))
        {
            R.error("该菜品下没有口味");
        }
        getOneDishRespVo.setFlavors(dishFlavors);
        return R.success(getOneDishRespVo);
    }

    @Override
    public R<String> updateDishById(AddDishReqVo addDishReqVo) {

        Dish dish = new Dish();

        BeanUtils.copyProperties(addDishReqVo,dish,"flavors");
        this.updateById(dish);
        List<DishFlavor> dishFlavors = addDishReqVo.getFlavors();
        for (DishFlavor dishFlavor : dishFlavors) {
            if(ObjectUtils.isEmpty(dishFlavor.getDishId()))
            {
                dishFlavor.setDishId(dish.getId());
                dishFlavorService.save(dishFlavor);
            }
            else{
                dishFlavorService.updateById(dishFlavor);
            }
        }
        return R.success("更新成功");
    }

    @Override
    public R<List<GetOneDishRespVo>> selectByCategoryId(Long categoryId) {
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dish::getCategoryId,categoryId).orderByAsc(Dish::getSort).eq(Dish::getStatus,1);
        List<Dish> dishes = dishMapper.selectList(lambdaQueryWrapper);
        if(ObjectUtils.isEmpty(dishes))
        {
            return R.success(null);
        }
        List<GetOneDishRespVo> getOneDishRespVos = dishes.stream().map(dish -> {
            GetOneDishRespVo getOneDishRespVo = new GetOneDishRespVo();
            LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
            dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId,dish.getId());
            getOneDishRespVo.setFlavors(dishFlavorService.list(dishFlavorLambdaQueryWrapper));
            BeanUtils.copyProperties(dish,getOneDishRespVo);
            return getOneDishRespVo;
        }).collect(Collectors.toList());
        return R.success(getOneDishRespVos);
    }

    @Override
    public R<String> changeStatusToDisAble(List<Long> ids) {
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getDishId, ids);
        if(setmealDishService.count(lambdaQueryWrapper)>0)
        {
            return R.error("有菜品正在套餐中");
        }
        LambdaUpdateWrapper<Dish> dishLambdaQueryWrapper =new LambdaUpdateWrapper<>();
        dishLambdaQueryWrapper.in(Dish::getId,ids);
        dishLambdaQueryWrapper.set(Dish::getStatus,0);
        this.update(dishLambdaQueryWrapper);
        return R.success("状态更改成功");
    }

    @Override
    public R<String> changeStatusToEnAble(List<Long> ids) {
        LambdaUpdateWrapper<Dish> dishLambdaQueryWrapper =new LambdaUpdateWrapper<>();
        dishLambdaQueryWrapper.in(Dish::getId,ids);
        dishLambdaQueryWrapper.set(Dish::getStatus,1);
        this.update(dishLambdaQueryWrapper);
        return R.success("状态更改成功");
    }

    @Override
    public R<String> deleteByids(List<Long> ids) {
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Dish::getId, ids);
        lambdaQueryWrapper.eq(Dish::getStatus, 1);
        if(this.count(lambdaQueryWrapper)>0)
        {
            return R.error("有菜品正在销售,不能删除");
        }
        LambdaQueryWrapper<Dish> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.in(Dish::getId,ids);
        this.remove(lambdaQueryWrapper1);
        return R.success("删除成功");
    }

}
