package com.zzh.vo.respVo;

import com.zzh.dao.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageQueryRespVo<E> {
    private List<E> records;
    private Integer total;
}
