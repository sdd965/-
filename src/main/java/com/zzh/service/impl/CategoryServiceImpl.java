package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzh.common.exception.BusinessException;
import com.zzh.common.exception.SystemException;
import com.zzh.dao.entity.Category;
import com.zzh.dao.entity.Dish;
import com.zzh.dao.entity.Setmeal;
import com.zzh.dao.mapper.CategoryMapper;
import com.zzh.service.CategoryService;
import com.zzh.service.DishService;
import com.zzh.service.SetmealService;
import com.zzh.vo.baseVo.PageResult;
import com.zzh.vo.baseVo.R;
import com.zzh.vo.reqVo.PageQueryReqVo;
import com.zzh.vo.respVo.PageQueryRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public R<String> addCategory(Category category) {
        try {
            categoryMapper.insert(category);
            return R.success("成功插入");
        }catch (Exception e)
        {
            if(e.getMessage().contains("Duplicate"))
            {
                throw new BusinessException("有重复的数据");
            }
            throw new SystemException("数据库异常");
        }
    }

    @Override
    public R<PageQueryRespVo<Category>> getPage(PageQueryReqVo pageQueryReqVo) {
        IPage<Category> page = new Page<>(pageQueryReqVo.getPage(), pageQueryReqVo.getPageSize());
        categoryMapper.selectPage(page,null);
        return R.success(new PageQueryRespVo<Category>(page.getRecords(), new Long(page.getTotal()).intValue()));
    }

    @Override
    public R<String> delete(Long id) {
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dish::getCategoryId,id);
        lambdaQueryWrapper1.eq(Setmeal::getCategoryId,id);
        if(dishService.isBelongToCategory(lambdaQueryWrapper))
        {
            throw new BusinessException("该种类下存在菜品");
        }
        if(setmealService.isBelongToCategory(lambdaQueryWrapper1))
        {
            throw new BusinessException("该种类下存在套餐");
        }
        if(categoryMapper.deleteById(id)>0)
        {
            return R.success("删除成功");
        }
        else {
            return R.error("删除失败");
        }

    }

    @Override
    public R<String> update(Category category) {
        if(this.updateById(category)){
            return R.success("修改成功");
        }
        return R.error("修改失败");
    }

    @Override
    public R<List<Category>> getAllCategory(Category category) {
        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(!ObjectUtils.isEmpty(category.getType())) {
            categoryLambdaQueryWrapper.eq(Category::getType, category.getType());
            categoryLambdaQueryWrapper.orderByAsc(Category::getSort);
            List<Category> categories = categoryMapper.selectList(categoryLambdaQueryWrapper);
            return R.success(categories);
        }
        else
        {
            List<Category> categories = categoryMapper.selectList(null);
            return R.success(categories);
        }
    }
}
