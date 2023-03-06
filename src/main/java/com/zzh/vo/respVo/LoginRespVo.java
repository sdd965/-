package com.zzh.vo.respVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRespVo {
    private String username;
    private String sex;
    private String token;
}
