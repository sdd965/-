package com.zzh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.dao.entity.Orders;
import com.zzh.vo.baseVo.R;
import org.springframework.transaction.annotation.Transactional;

public interface OrdersService extends IService<Orders> {
    @Transactional
    R<String> submit(Orders orders);
}
