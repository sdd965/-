package com.zzh.controller;


import com.zzh.dao.entity.Employee;
import com.zzh.service.LoginService;
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
public class loginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public R<Employee> login(@RequestBody LoginReqVo loginReqVo){
        return loginService.login(loginReqVo);
    }

}
