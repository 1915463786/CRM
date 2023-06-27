package com.yjxxt.crm.interceptor;


import com.yjxxt.crm.dao.UserMapper;
import com.yjxxt.crm.exceptions.NoLoginException;
import com.yjxxt.crm.utils.LoginUserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 非法访问拦截
 */
public class NoLoginInterceptor extends HandlerInterceptorAdapter {

    //注入UserMapper
    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取cookie中的用户ID
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        //判断用户ID是否为空，且数据库中存在该ID的用户记录
        if (null == userId || userMapper.selectByPrimaryKey(userId) == null){
            //抛出未登录异常
            throw new NoLoginException();
        }
        return true;
    }
}
