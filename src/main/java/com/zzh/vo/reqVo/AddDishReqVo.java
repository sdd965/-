package com.zzh.vo.reqVo;

import com.zzh.dao.entity.Dish;
import com.zzh.dao.entity.DishFlavor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddDishReqVo extends Dish {

    private Integer copies;

    private List<DishFlavor> flavors = new ArrayList<>();
}
