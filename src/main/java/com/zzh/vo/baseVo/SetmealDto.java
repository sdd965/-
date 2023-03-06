package com.zzh.vo.baseVo;

import com.zzh.dao.entity.Setmeal;
import com.zzh.dao.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
