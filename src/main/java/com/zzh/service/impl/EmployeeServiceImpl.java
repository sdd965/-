package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzh.common.exception.BusinessException;
import com.zzh.dao.entity.Employee;
import com.zzh.dao.mapper.EmployeeMapper;
import com.zzh.service.EmployeeService;
import com.zzh.vo.baseVo.R;
import com.zzh.vo.reqVo.PageQueryReqVo;
import com.zzh.vo.respVo.PageQueryRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /*
    插入操作
     */
    @Override
    public R<String> insert(Employee employee) {
        try{
            employeeMapper.insert(employee);
            return R.success("插入成功");
        }catch (Exception e)
        {
            if(e.getMessage().contains("Duplicate"))
                throw new BusinessException("该用户已存在");
            throw new BusinessException("数据库异常");
        }
    }

    @Override
    public R<PageQueryRespVo<Employee>> pageQuery(PageQueryReqVo pageQueryReqVo) {
        String name = pageQueryReqVo.getName();
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Objects.nonNull(name),Employee::getName,name);
        IPage<Employee> page = new Page<>(pageQueryReqVo.getPage(),pageQueryReqVo.getPageSize());
        employeeMapper.selectPage(page,lambdaQueryWrapper);
        return R.success(new PageQueryRespVo<Employee>(page.getRecords(), new Long(page.getTotal()).intValue()));
    }
    /*
    更新操作
     */
    @Override
    public R uploadEmployee(Employee employee) {
        Long id = employee.getId();
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Employee::getId,id);
        employeeMapper.update(employee,lambdaQueryWrapper);
        return R.success("成功修改");

    }

    @Override
    public R<Employee> getById(Long id) {
        Employee employee = employeeMapper.selectById(id);
        if(Objects.nonNull(employee))
        {
            return R.success(employee);
        }
        return R.error("该用户不存在");
    }
}
