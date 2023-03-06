package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzh.common.utils.RedisCache;
import com.zzh.dao.entity.ShoppingCart;
import com.zzh.dao.mapper.ShoppingCartMapper;
import com.zzh.service.ShoppingCartService;
import com.zzh.vo.baseVo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService
{

    @Autowired
    private RedisCache redisCache;
    @Override
    public R<ShoppingCart> addShoppingCart(ShoppingCart shoppingCart) {
        Long userId = redisCache.getCacheObject("user");
        shoppingCart.setUserId(userId);
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(!ObjectUtils.isEmpty(shoppingCart.getDishId())||!ObjectUtils.isEmpty(shoppingCart.getSetmealId()))
        {
        lambdaQueryWrapper.eq(!ObjectUtils.isEmpty(shoppingCart.getDishId()),ShoppingCart::getDishId,shoppingCart.getDishId());
        lambdaQueryWrapper.eq(!ObjectUtils.isEmpty(shoppingCart.getSetmealId()),ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        lambdaQueryWrapper.eq(ShoppingCart::getUserId,userId);
        ShoppingCart shoppingCart1 = this.getOne(lambdaQueryWrapper);
        if(ObjectUtils.isEmpty(shoppingCart1))
        {
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            this.save(shoppingCart);
            return R.success(shoppingCart);
        }
        else
        {
            Integer integer = shoppingCart1.getNumber();
            integer++;
            shoppingCart1.setNumber(integer);
            this.updateById(shoppingCart1);
            return R.success(shoppingCart1);
        }

    }
    else{
        return R.error("别乱搞可以不");
    }
    }

    @Override
    public R<List<ShoppingCart>> getShoppingCart() {
        Long userId = redisCache.getCacheObject("user");
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, userId);
        List<ShoppingCart> shoppingCarts = this.list(lambdaQueryWrapper);
        return R.success(shoppingCarts);
    }

    @Override
    public R<ShoppingCart> subShoppingCart(ShoppingCart shoppingCart) {
        Long userId = redisCache.getCacheObject("user");
        shoppingCart.setUserId(userId);
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(!ObjectUtils.isEmpty(shoppingCart.getDishId())||!ObjectUtils.isEmpty(shoppingCart.getSetmealId()))
        {
            lambdaQueryWrapper.eq(!ObjectUtils.isEmpty(shoppingCart.getDishId()),ShoppingCart::getDishId,shoppingCart.getDishId());
            lambdaQueryWrapper.eq(!ObjectUtils.isEmpty(shoppingCart.getSetmealId()),ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
            lambdaQueryWrapper.eq(ShoppingCart::getUserId,userId);
            ShoppingCart shoppingCart1 = this.getOne(lambdaQueryWrapper);
            Integer integer = shoppingCart1.getNumber();
            integer --;
            if(integer == 0){
                this.removeById(shoppingCart1);
            }
            else {
                shoppingCart1.setNumber(integer);
                this.updateById(shoppingCart1);

            }
            return R.success(shoppingCart1);
        }else
        {
            return R.error("别乱搞");
        }
    }

    @Override
    public R<String> deleteAll() {
        Long userId = redisCache.getCacheObject("user");
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId,userId);
        this.remove(lambdaQueryWrapper);
        return R.success("成功清空购物车");
    }
}