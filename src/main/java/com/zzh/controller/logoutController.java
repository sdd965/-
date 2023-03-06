package com.zzh.controller;

import com.zzh.dao.entity.Employee;
import com.zzh.service.LoginService;
import com.zzh.service.LogoutService;
import com.zzh.vo.baseVo.R;
import com.zzh.vo.reqVo.LoginReqVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/employee")
public class logoutController {
    @Autowired
    private LogoutService logoutService;

    @PostMapping("/logout")
    public R<String> logout(){
        return logoutService.logout();
    }
}

