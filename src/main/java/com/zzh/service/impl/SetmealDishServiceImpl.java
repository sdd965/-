package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzh.dao.entity.SetmealDish;
import com.zzh.dao.mapper.SetmealDishMapper;
import com.zzh.service.SetmealDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Override
    public List<Long> selectIdsBySetmealId(Long id) {
        return setmealDishMapper.selectIdsBySetmealId(id);
    }

    @Override
    public List<SetmealDish> selectSetmealDishesById(LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper) {
        return setmealDishMapper.selectList(lambdaQueryWrapper);
    }
}
