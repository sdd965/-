package com.zzh.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zzh.common.utils.RedisCache;
import com.zzh.dao.entity.Employee;
import com.zzh.dao.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Slf4j
@Component
public class NewMetaObjectHandler implements MetaObjectHandler {
    @Autowired
    private RedisCache redisCache;

    //公共元数据处理器
    @Override
    public void insertFill(MetaObject metaObject) {
        Employee nowUser = redisCache.getCacheObject("1");
        Long userId = redisCache.getCacheObject("user");
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("createUser", !ObjectUtils.isEmpty(nowUser) ?nowUser.getId():userId);
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", !ObjectUtils.isEmpty(nowUser) ?nowUser.getId():userId);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId = redisCache.getCacheObject("user");
        Employee nowUser = redisCache.getCacheObject("1");
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", !ObjectUtils.isEmpty(nowUser) ?nowUser.getId():userId);
    }
}
