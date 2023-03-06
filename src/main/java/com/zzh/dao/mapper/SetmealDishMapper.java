package com.zzh.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzh.dao.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SetmealDishMapper extends BaseMapper<SetmealDish> {
    List<Long> selectIdsBySetmealId(@Param("id") Long id);
}
