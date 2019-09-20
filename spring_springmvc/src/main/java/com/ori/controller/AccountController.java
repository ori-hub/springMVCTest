package com.ori.controller;

import com.ori.domain.AccountBean;
import com.ori.domain.TokenBean;
import com.ori.service.IAccountService;
import com.ori.token.JWT;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("accountController")
@RequestMapping(path = "/account")
public class AccountController {

    @Resource(name = "accountService")
    private IAccountService accountService;

    @RequestMapping(path = "/first")
    public void getToken(HttpServletRequest request, HttpServletResponse response){

        //统一设置返回数据格式
        response.setContentType("application/json");
        response.setHeader("Pragma", "no-cache");
        response.setCharacterEncoding("UTF-8");

        /**
         * 生成token
         */
        //获取请求头
        String referer = request.getHeader("requestToken");
        TokenBean tokenInfo = new TokenBean();
        tokenInfo.setTokenIp(referer);

        String tokenID = JWT.sign(tokenInfo, 60L* 1000L* 30L);

        //返回token
        PrintWriter out = null;
        try {

            out = response.getWriter();

        } catch (IOException e) {
            e.printStackTrace();
        }

        out.print(tokenID);
        out.flush();
        out.close();
    }

    @RequestMapping(path = "/findAllAccount")
    public void findAllAccountController(HttpServletRequest request, HttpServletResponse response){

        //统一设置返回数据格式
        response.setContentType("application/json");
        response.setHeader("Pragma", "no-cache");
        response.setCharacterEncoding("UTF-8");

        List<AccountBean> list = accountService.findAllAccount();

        JSONArray jsonArray = JSONArray.fromObject(list);

        PrintWriter out = null;
        try {

            out = response.getWriter();

        } catch (IOException e) {
            e.printStackTrace();
        }
        out.print(jsonArray);
        out.close();
    }

    @RequestMapping(path = "/updateAccount")
    public void updateAccountController(HttpServletRequest request, HttpServletResponse response) throws Exception{

        //统一设置返回数据格式
        response.setContentType("application/json");
        response.setHeader("Pragma", "no-cache");
        response.setCharacterEncoding("UTF-8");

        int id = Integer.valueOf(request.getParameter("id"));
        String name = request.getParameter("name");
        int age = Integer.valueOf(request.getParameter("age"));

        AccountBean accountBean = new AccountBean();
        accountBean.setId(id);
        accountBean.setName(name);
        accountBean.setAge(age);
        int resultNum = accountService.updateAccountById(accountBean);

        PrintWriter out = null;
        try {

            out = response.getWriter();

        } catch (IOException e) {
            e.printStackTrace();
        }
        out.print(resultNum);
        out.close();
    }

    @RequestMapping(path = "/deleteAccount")
    public void deleteAccountController(HttpServletRequest request, HttpServletResponse response) throws Exception{

        //统一设置返回数据格式
        response.setContentType("application/json");
        response.setHeader("Pragma", "no-cache");
        response.setCharacterEncoding("UTF-8");

        int id = Integer.valueOf(request.getParameter("id"));

        int resultNum = accountService.deleteAccountById(id);

        PrintWriter out = null;
        try {

            out = response.getWriter();

        } catch (IOException e) {
            e.printStackTrace();
        }
        out.print(resultNum);
        out.close();
    }

    @RequestMapping(path = "/insertAccount")
    public void insertAccountController(HttpServletRequest request, HttpServletResponse response) throws Exception{

        //统一设置返回数据格式
        response.setContentType("application/json");
        response.setHeader("Pragma", "no-cache");
        response.setCharacterEncoding("UTF-8");

        int id = Integer.valueOf(request.getParameter("id"));
        String name = request.getParameter("name");
        int age = Integer.valueOf(request.getParameter("age"));

        AccountBean accountBean = new AccountBean();
        accountBean.setId(id);
        accountBean.setName(name);
        accountBean.setAge(age);
        int resultNum = accountService.insertAccount(accountBean);

        PrintWriter out = null;
        try {

            out = response.getWriter();

        } catch (IOException e) {
            e.printStackTrace();
        }
        out.print(resultNum);
        out.close();
    }
}
