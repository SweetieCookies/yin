package com.qf.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "payServlet", value = "/payservlet")
public class payServlet extends BaseServlet {
    public String pay(HttpServletRequest request, HttpServletResponse response) throws Exception{

        return null;
    }

}
