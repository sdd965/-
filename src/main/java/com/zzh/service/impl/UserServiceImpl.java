package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzh.common.utils.RedisCache;
import com.zzh.common.utils.ValidateCodeUtils;
import com.zzh.dao.entity.User;
import com.zzh.dao.mapper.UserMapper;
import com.zzh.service.UserService;
import com.zzh.vo.baseVo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
    @Autowired
    private RedisCache redisCache;
    @Override
    public R<String> sendMsg(User user) {
        String phone = user.getPhone();
        if(!ObjectUtils.isEmpty(phone))
        {
            String code = ValidateCodeUtils.generateValidateCode4String(4);
            redisCache.setCacheObject(phone,code,5, TimeUnit.MINUTES);
            return R.success("验证码发送成功,有效期5分钟");
        }
        return R.error("验证码发送失败");
    }

    @Override
    public R<String> userLogin(Map<String, String> map) {
        String phone = map.get("phone");
        String code = redisCache.getCacheObject(phone);
        if(ObjectUtils.isEmpty(code))
        {
            return R.error("验证码已失效");
        }
        User user = new User();
        if(code.equals(map.get("code")))
        {
            redisCache.deleteObject(phone);
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(User::getPhone, phone);
            if(this.count(lambdaQueryWrapper)<1)
            {
                user.setPhone(phone);
                this.save(user);
                redisCache.setCacheObject("user",user.getId());
                return R.success("登陆成功");
            }
            user = this.getOne(lambdaQueryWrapper);
            redisCache.setCacheObject("user",user.getId());
            return R.success("登陆成功");
        }
        return R.error("登陆失败");
    }

    @Override
    public R<String> logout() {
        Long nowUsrId= redisCache.getCacheObject("user");
        if(!ObjectUtils.isEmpty(nowUsrId))
        {
            redisCache.deleteObject("user");
            return R.success("成功删除该用户");
        }
        return R.error("当前无用户登陆");
    }
}
