package com.zzh.controller;

import com.zzh.dao.entity.ShoppingCart;
import com.zzh.service.ShoppingCartService;
import com.zzh.vo.baseVo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/shoppingCart")
@RestController
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @RequestMapping("/list")
    @GetMapping
    public R<List<ShoppingCart>> getShoppingCart()
    {
        return shoppingCartService.getShoppingCart();
    }

    @RequestMapping("/add")
    @PostMapping
    public R<ShoppingCart> addShoppingCart(@RequestBody ShoppingCart shoppingCart)
    {
        return shoppingCartService.addShoppingCart(shoppingCart);
    }

    @RequestMapping("/sub")
    public R<ShoppingCart> subShoppingCart(@RequestBody ShoppingCart shoppingCart){
        return shoppingCartService.subShoppingCart(shoppingCart);
    }

    @RequestMapping("/clean")
    @DeleteMapping
    public R<String> deleteShoppingCart()
    {
        return shoppingCartService.deleteAll();
    }
}
