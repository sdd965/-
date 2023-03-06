package com.zzh.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzh.common.exception.BusinessException;
import com.zzh.common.utils.RedisCache;
import com.zzh.dao.entity.*;
import com.zzh.dao.mapper.OrdersMapper;
import com.zzh.service.*;
import com.zzh.vo.baseVo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrdersServiceIpml extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private AddressBookService addressBookService;
    @Autowired
    private UserService userService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Override
    public R<String> submit(Orders orders) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Long id = redisCache.getCacheObject("user");
        long id1 = IdWorker.getId();
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId,id);
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(lambdaQueryWrapper);
        if(ObjectUtils.isEmpty(shoppingCarts))
        {
            throw new BusinessException("该用户未下单");
        }
        List<OrderDetail> orderDetails = shoppingCarts.stream().map(new Function<ShoppingCart, OrderDetail>() {
            @Override
            public OrderDetail apply(ShoppingCart shoppingCart) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(id1);
                orderDetail.setImage(shoppingCart.getImage());
                orderDetail.setName(shoppingCart.getName());
                orderDetail.setDishId(shoppingCart.getDishId());
                orderDetail.setDishFlavor(shoppingCart.getDishFlavor());
                orderDetail.setNumber(shoppingCart.getNumber());
                orderDetail.setSetmealId(shoppingCart.getSetmealId());
                orderDetail.setAmount(shoppingCart.getAmount());
                atomicInteger.addAndGet(shoppingCart.getAmount().multiply(new BigDecimal(shoppingCart.getNumber())).intValue());
                return orderDetail;
            }
        }).collect(Collectors.toList());
        User user = userService.getById(id);
        AddressBook addressBook = addressBookService.getById(orders.getAddressBookId());
        orders.setId(id1);
        orders.setUserId(id);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setPhone(user.getPhone());
        orders.setAddress(addressBook.getDetail());
        orders.setConsignee(addressBook.getConsignee());
        orders.setAmount(BigDecimal.valueOf(atomicInteger.get()));
        this.save(orders);
        orderDetailService.saveBatch(orderDetails);

        shoppingCartService.deleteAll();
        return R.success("下单成功");
    }
}
