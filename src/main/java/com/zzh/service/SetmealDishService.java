package com.zzh.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.dao.entity.SetmealDish;

import java.util.List;

public interface SetmealDishService extends IService<SetmealDish> {
    List<Long> selectIdsBySetmealId(Long id);

    List<SetmealDish> selectSetmealDishesById(LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper);
}
