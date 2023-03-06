package com.zzh.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzh.dao.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
    List<Long> getIdsByDishId(Long id);
}
