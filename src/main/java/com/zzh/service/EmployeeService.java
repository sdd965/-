package com.zzh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.dao.entity.Employee;
import com.zzh.vo.baseVo.R;
import com.zzh.vo.reqVo.PageQueryReqVo;
import com.zzh.vo.respVo.PageQueryRespVo;

public interface EmployeeService extends IService<Employee> {
    R<String> insert(Employee employee);

    R<PageQueryRespVo<Employee>> pageQuery(PageQueryReqVo reqVo);

    R<String> uploadEmployee(Employee employee);

    R<Employee> getById(Long id);
}
