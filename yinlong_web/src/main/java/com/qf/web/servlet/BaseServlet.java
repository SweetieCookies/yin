package com.qf.web.servlet;

import com.qf.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String methodName = request.getParameter("method");
        if(!StringUtils.isEmpty(methodName)){
            try {
                Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
                //获取方法的返回值
                String url = (String) method.invoke(this, request, response);
                //System.out.println(url);
                if(!StringUtils.isEmpty(url)){
                    if(url.startsWith("redirect:")){
                        response.sendRedirect(request.getContextPath()+url.split(":")[1]);
                    }else{
                        request.getRequestDispatcher(url).forward(request, response);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
