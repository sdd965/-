package com.zzh.controller;

import com.zzh.dao.entity.Employee;
import com.zzh.service.EmployeeService;
import com.zzh.vo.baseVo.R;
import com.zzh.vo.reqVo.PageQueryReqVo;
import com.zzh.vo.respVo.PageQueryRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/employee")
@RestController
public class QueryEmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/page")
    public R<PageQueryRespVo<Employee>> pageQueryEmployee(PageQueryReqVo pageQueryReqVo)
    {
        return employeeService.pageQuery(pageQueryReqVo);
    }


}
