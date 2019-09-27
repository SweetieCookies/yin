package com.qf.web.servlet;

import com.qf.utils.Md5Utils;
import com.qf.utils.StringUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "adminLogin", value = "/adminLogin")
public class adminLogin extends BaseServlet {
    public String login(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        System.out.println(username);
        String password = request.getParameter("password");

        if(StringUtils.isEmpty(username)){
            return null;
        }
        if(StringUtils.isEmpty(password)){
            return null;
        }

        password= Md5Utils.md5(password);
        System.out.println(password);
        if("admin".equals(username)&&"e10adc3949ba59abbe56e057f20f883e".equals(password)){
            return request.getContextPath()+"/admin.jsp";
        }
        return "../login.jsp";
    }
}
