package com.zzh.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzh.dao.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
