package com.zzh.vo.respVo;

import com.zzh.dao.entity.Dish;
import com.zzh.dao.entity.DishFlavor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOneDishRespVo extends Dish {
    private List<DishFlavor> flavors;
}
