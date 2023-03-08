package com.zzh;

import com.zzh.common.utils.RedisCache;
import com.zzh.service.SetmealDishService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class HmApplicationTests {

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private RedisCache redisCache;

    @Test
    void selectIdsBysetmeal_id()
    {
        List<Long> ids = setmealDishService.selectIdsBySetmealId(1415580119015145474L);
        System.out.println(ids);
    }

    @Test
    void redisTest1()
    {
        redisCache.setCacheObject("key","value", 10, TimeUnit.SECONDS );
    }

    @Test
    void redisTest2()
    {
//        redisCache.setCacheMapValue("hash1","age",14);
        Integer age = redisCache.getCacheMapValue("hash1","age");
        Map<String,Object> map = redisCache.getCacheMap("hash1");
        Set<Map.Entry<String, Object>> set = map.entrySet();
        for (Map.Entry<String, Object> stringObjectEntry : set) {
            System.out.println(stringObjectEntry.getKey()+":"+stringObjectEntry.getValue());

        }
        System.out.println(age);
    }

    @Test
    void redisTest3()
    {
//        List<String> list = new ArrayList<>();
//        list.add("1");
//        list.add("3");
//        list.add("58");
//        redisCache.setCacheList("list1",list);
        List<String> list = redisCache.getCacheList("list1");
        for (String s : list) {
            System.out.println(s);
        }
    }
}
