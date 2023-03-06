package com.zzh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzh.common.utils.RedisCache;
import com.zzh.dao.entity.Employee;
import com.zzh.dao.mapper.EmployeeMapper;
import com.zzh.service.LogoutService;
import com.zzh.vo.baseVo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LogoutServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements LogoutService
{
    @Autowired
    private RedisCache redisCache;
    @Override
    public R<String> logout() {
        Employee employee = redisCache.getCacheObject("1");
        if(Objects.nonNull(employee))
        {
            redisCache.deleteObject("1");
            return R.success("成功退出");
        }
        return R.error("当前无用户登陆");
    }
}
