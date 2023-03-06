package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzh.common.utils.RedisCache;
import com.zzh.dao.entity.Employee;
import com.zzh.dao.mapper.EmployeeMapper;
import com.zzh.service.LoginService;
import com.zzh.vo.baseVo.R;
import com.zzh.vo.reqVo.LoginReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Objects;

@Service
public class loginServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements LoginService {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Override
    public R<Employee> login(LoginReqVo loginReqVo) {
        String password = loginReqVo.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Employee::getUsername,loginReqVo.getUsername());
        //这里是不是要异常处理一下
        Employee employee = employeeMapper.selectOne(lambdaQueryWrapper);
        if(Objects.isNull(employee))
        {
            return R.error("用户不存在");
        }else if(!password.equals(employee.getPassword()))
        {
            return R.error("密码错误");
        }
        else if(employee.getStatus()==0)
        {
            return R.error("该用户已经停用");
        }
        else{
            redisCache.setCacheObject("1",employee);
            return R.success(employee);
        }

    }
}
