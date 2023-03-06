package com.zzh.vo.respVo;

import com.zzh.dao.entity.Dish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishListRespVo extends Dish {
    private String categoryName;
}
