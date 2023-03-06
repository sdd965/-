package com.zzh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.dao.entity.DishFlavor;

import java.util.List;

public interface DishFlavorService extends IService<DishFlavor> {

    List<Long> getIdsByDishId(Long id);
}
