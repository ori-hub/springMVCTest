package com.ori.interceptor;

import com.ori.domain.TokenBean;
import com.ori.token.JWT;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class myInterceptor implements HandlerInterceptor {

    //请求进入controller层前执行的方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception{

        //获取请求头
        String referer = request.getHeader("requestToken");
        String token = request.getHeader("tokenID");

        if (!"".equals(referer) && "".equals(token)){

            return true;
        }
        else if("".equals(referer) && !"".equals(token)){

            //获取token
            //验证token是否通过
            try{

                TokenBean tokenIP = JWT.unsign(token, TokenBean.class);

                if (null == tokenIP || "".equals(tokenIP)){

                    return false;
                }

                return true;

            }catch (Exception e){

                e.printStackTrace();
                return false;
            }
        }
        else {

            return false;
        }
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
