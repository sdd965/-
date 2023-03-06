package com.zzh.vo.reqVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQueryReqVo {
    private Integer page;
    private Integer pageSize;
    private String name;
}
