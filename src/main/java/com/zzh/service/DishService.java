package com.zzh.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.dao.entity.Dish;
import com.zzh.vo.baseVo.R;
import com.zzh.vo.reqVo.AddDishReqVo;
import com.zzh.vo.reqVo.PageQueryReqVo;
import com.zzh.vo.respVo.DishListRespVo;
import com.zzh.vo.respVo.GetOneDishRespVo;
import com.zzh.vo.respVo.PageQueryRespVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface DishService extends IService<Dish> {
    Boolean isBelongToCategory(LambdaQueryWrapper<Dish> a);
    R<String> addDish(AddDishReqVo addDishReqVo);

    R<PageQueryRespVo<DishListRespVo>> selectAll(PageQueryReqVo pageQueryReqVo);

    R<GetOneDishRespVo> getDishById(Long id);

    R<String> updateDishById(AddDishReqVo addDishReqVo);

    R<List<GetOneDishRespVo>> selectByCategoryId(Long categoryId);

    R<String> changeStatusToDisAble(List<Long> ids);

    R<String> changeStatusToEnAble(List<Long> ids);

    R<String> deleteByids(List<Long> ids);
}
