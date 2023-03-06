package com.zzh.common.filter;

import com.alibaba.fastjson.JSON;
import com.zzh.common.utils.RedisCache;
import com.zzh.vo.baseVo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class LoginCheckFliter implements Filter {
    @Autowired
    private RedisCache redisCache;
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestUrl = request.getRequestURI();
        String [] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
        if(check(urls,requestUrl))
        {
            filterChain.doFilter(request, response);
            return;
        }
        if(Objects.nonNull(redisCache.getCacheObject("1")))
        {
            filterChain.doFilter(request, response);
            return;
        }
        if(Objects.nonNull(redisCache.getCacheObject("user")))
        {
            filterChain.doFilter(request, response);
            return;
        }
        //对象转成json字符串
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    public boolean check(String[] urls, String requestUrl)
    {
        for (String url : urls) {
            if(PATH_MATCHER.match(url,requestUrl))
                return true;
        }
        return false;
    }
}
