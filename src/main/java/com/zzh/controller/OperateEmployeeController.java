package com.zzh.controller;


import com.zzh.dao.entity.Employee;
import com.zzh.service.EmployeeService;
import com.zzh.vo.baseVo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/employee")
@RestController
public class OperateEmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @PostMapping
    public R<String> addEmployee(@RequestBody Employee employee)
    {
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        return employeeService.insert(employee);
    }

    @PutMapping
    public R<String> updateState(@RequestBody Employee employee)
    {
        return employeeService.uploadEmployee(employee);
    }

    @GetMapping("/{id}")
    public R<Employee> editEmployee(@PathVariable Long id)
    {
        return employeeService.getById(id);
    }

}
