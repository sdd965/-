package com.zzh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.dao.entity.ShoppingCart;
import com.zzh.vo.baseVo.R;

import java.util.List;

public interface ShoppingCartService extends IService<ShoppingCart> {
    R<ShoppingCart> addShoppingCart(ShoppingCart shoppingCart);

    R<List<ShoppingCart>> getShoppingCart();

    R<ShoppingCart> subShoppingCart(ShoppingCart shoppingCart);

    R<String> deleteAll();
}
