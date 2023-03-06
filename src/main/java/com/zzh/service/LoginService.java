package com.zzh.service;

import com.zzh.dao.entity.Employee;
import com.zzh.vo.baseVo.R;
import com.zzh.vo.reqVo.LoginReqVo;

public interface LoginService {
    R<Employee> login(LoginReqVo loginReqVo);
}
