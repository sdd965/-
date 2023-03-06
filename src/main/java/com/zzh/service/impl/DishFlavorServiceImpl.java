package com.zzh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzh.dao.entity.DishFlavor;
import com.zzh.dao.mapper.DishFlavorMapper;
import com.zzh.service.DishFlavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Override
    public List<Long> getIdsByDishId(Long id) {
        return dishFlavorMapper.getIdsByDishId(id);
    }
}
