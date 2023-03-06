package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzh.common.exception.BusinessException;
import com.zzh.dao.entity.Setmeal;
import com.zzh.dao.entity.SetmealDish;
import com.zzh.dao.mapper.CategoryMapper;
import com.zzh.dao.mapper.SetmealMapper;
import com.zzh.service.SetmealDishService;
import com.zzh.service.SetmealService;
import com.zzh.vo.baseVo.R;
import com.zzh.vo.baseVo.SetmealDto;
import com.zzh.vo.reqVo.PageQueryReqVo;
import com.zzh.vo.respVo.PageQueryRespVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private SetmealDishService setmealDishService;

    @Override
    public Boolean isBelongToCategory(LambdaQueryWrapper<Setmeal> a) {
            return setmealMapper.selectCount(a) > 0;
        }

    @Override
    @Transactional
    public R<String> addMeal(SetmealDto setmealDto) {
        try
        {
            setmealMapper.insert(setmealDto);
        }catch (Exception e)
        {
            if(e.getMessage().contains("Duplicate"))
            {
                throw new BusinessException("有重复值");
            }
                e.printStackTrace();
                throw new BusinessException("有未填项");
        }
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes().stream().peek(setmealDish -> setmealDish.setSetmealId(setmealDto.getId())).collect(Collectors.toList());
        try{
        setmealDishService.saveBatch(setmealDishes);
            return R.success("成功插入");
        }catch (Exception e)
        {
            return R.error("插入失败");
        }

    }

    @Override
    public R<PageQueryRespVo<SetmealDto>> pageQuery(PageQueryReqVo pageQueryReqVo) {
        IPage<Setmeal> page = new Page<>(pageQueryReqVo.getPage(), pageQueryReqVo.getPageSize());
        String name = pageQueryReqVo.getName();
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.hasText(name), Setmeal::getName, name);
        setmealMapper.selectPage(page, lambdaQueryWrapper);
        List<Setmeal> setmeals = page.getRecords();
        Integer total = new Long(page.getTotal()).intValue();
        List<SetmealDto> setmealDtos = setmeals.stream().map(setmeal -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(setmeal, setmealDto);
            setmealDto.setCategoryName(categoryMapper.selectById(setmeal.getCategoryId()).getName());
            return setmealDto;
        }).collect(Collectors.toList());
        PageQueryRespVo<SetmealDto> pageQueryRespVo = new PageQueryRespVo<>();
        pageQueryRespVo.setRecords(setmealDtos);
        pageQueryRespVo.setTotal(total);
        return R.success(pageQueryRespVo);
    }

    @Override
    public R<String> setmealDelete(List<Long> ids) {

            LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Setmeal::getStatus,1);
            lambdaQueryWrapper.in(Setmeal::getId, ids);
            if(this.count(lambdaQueryWrapper)>0)
            {
                throw new BusinessException("当前有套餐正在售卖!");
            }
        try {
            setmealMapper.deleteBatchIds(ids);
            for (Long id : ids) {
                List<Long> setmealDishIds = setmealDishService.selectIdsBySetmealId(id);
                setmealDishService.removeByIds(setmealDishIds);
            }
            return R.success("删除成功");
        }catch (Exception e)
        {
            throw new BusinessException("删除失败");
        }

    }

    @Override
    public R<SetmealDto> getSetmealBySetmealId(Long id) {
        Setmeal setmeal = this.getById(id);
        SetmealDto setmealDto = new SetmealDto();
        if(ObjectUtils.isEmpty(setmeal))
        {
            return R.error("未找到该菜品");
        }
        BeanUtils.copyProperties(setmeal,setmealDto);
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> setmealDishes = setmealDishService.selectSetmealDishesById(lambdaQueryWrapper);
        setmealDto.setSetmealDishes(setmealDishes);
        return R.success(setmealDto);
    }

    @Override
    public R<List<Setmeal>> getSetmealByCategoryId(Long categoryId) {
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(!ObjectUtils.isEmpty(categoryId),Setmeal::getCategoryId,categoryId);
        List<Setmeal> setmeals = this.list(setmealLambdaQueryWrapper);
        if(ObjectUtils.isEmpty(setmeals))
        {
            return R.error("该类套餐下暂无商品");
        }
        return  R.success(setmeals);

    }
}
