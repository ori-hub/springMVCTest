package com.ori.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class myInterceptor implements HandlerInterceptor {

    //请求进入controller层前执行的方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception{

        System.out.println("拦截器输出");
        return true;
    }

    //controller层执行完毕后，返回数据前执行的方法
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception{

    }

    //一个请求出来完毕，即将销毁的时候执行的方法
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception ex) throws Exception{

    }
}
