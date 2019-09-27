package com.qf.web.filter;

import com.qf.domain.User;
import com.qf.service.UserService;
import com.qf.utils.Base64Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AutoLoginFilter",value = "/*")
public class AutoLoginFilter implements Filter {
    @Autowired
    private UserService userService= (UserService) ContextLoader.getCurrentWebApplicationContext().getBean("userServiceImpl");
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request= (HttpServletRequest) req;
        HttpServletResponse response= (HttpServletResponse) resp;
        User user = (User) request.getSession().getAttribute("user");

        //System.out.println(user.toString());
        if(user!=null){
            chain.doFilter(req, resp);
            return;
        }
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("userinfo")){
                    String value = cookie.getValue();
                    String decode = Base64Utils.decode(value);
                  //  System.out.println(decode);
                    String[] split = decode.split("#");
                    User user1 = userService.login(split[0], split[1]);
                    if(user1!=null){
                        if(user1.getFlag()==1) {
                            request.getSession().setAttribute("user", user1);
                            chain.doFilter(req, resp);
                            return;
                        }
                    }else {
                        Cookie cookie1=new Cookie("userinfo", "");
                        cookie1.setMaxAge(0);
                        cookie1.setPath("/");
                        cookie1.setHttpOnly(true);
                        response.addCookie(cookie1);
                    }
                }
            }
           // chain.doFilter(request, response);
        }
            chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }
}
