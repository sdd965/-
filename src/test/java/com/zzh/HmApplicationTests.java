package com.zzh;

import com.zzh.service.SetmealDishService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

@SpringBootTest
class HmApplicationTests {

    @Autowired
    private SetmealDishService setmealDishService;

    @Test
    void selectIdsBysetmeal_id()
    {
        List<Long> ids = setmealDishService.selectIdsBySetmealId(1415580119015145474L);
        System.out.println(ids);
    }


}
