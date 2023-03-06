package com.zzh.common.exception;

import com.zzh.vo.baseVo.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProjectExceptionAdvcie {
    @ExceptionHandler(SystemException.class)
    public R doException(SystemException ex)
    {
        //记录日志
        //发消息给运维
        //返回消息
        System.out.println("捕获到了异常");
        return R.error(ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public R doException(BusinessException ex)
    {
        return R.error(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public R doException(Exception ex)
    {
        //记录日志
        //发消息给运维
        //返回消息
        return R.error(ex.getMessage());
    }
}
