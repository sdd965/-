package com.zzh.controller;

import com.zzh.dao.entity.User;
import com.zzh.service.UserService;
import com.zzh.vo.baseVo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/user")
@RestController
public class userController {

    @Autowired
    private UserService userService;

    @PostMapping
    @RequestMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user)
    {
        return userService.sendMsg(user);
    }

    @PostMapping
    @RequestMapping("/login")
    public R<String> userLogin(@RequestBody Map<String,String> map)
    {
        return userService.userLogin(map);
    }

    @RequestMapping("/loginout")
    @PostMapping
    public R<String> userLogout()
    {
        return userService.logout();
    }
}
