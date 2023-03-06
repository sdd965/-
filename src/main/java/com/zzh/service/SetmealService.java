package com.zzh.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.dao.entity.Setmeal;
import com.zzh.vo.baseVo.R;
import com.zzh.vo.baseVo.SetmealDto;
import com.zzh.vo.reqVo.PageQueryReqVo;
import com.zzh.vo.respVo.PageQueryRespVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface SetmealService extends IService<Setmeal> {
    Boolean isBelongToCategory(LambdaQueryWrapper<Setmeal> a);

    R<String> addMeal(SetmealDto setmealDto);

    R<PageQueryRespVo<SetmealDto>> pageQuery(PageQueryReqVo pageQueryReqVo);

    @Transactional
    R<String> setmealDelete(List<Long> ids);

    R<SetmealDto> getSetmealBySetmealId(Long id);

    R<List<Setmeal>> getSetmealByCategoryId(Long categoryId);
}
