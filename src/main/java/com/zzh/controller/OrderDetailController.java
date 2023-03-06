package com.zzh.controller;

import com.zzh.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;
}
