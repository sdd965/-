package com.zzh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.dao.entity.User;
import com.zzh.vo.baseVo.R;

import java.util.Map;

public interface UserService extends IService<User> {
    R<String> sendMsg(User user);

    R<String> userLogin(Map<String,String> map);

    R<String> logout();
}
