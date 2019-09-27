package com.qf.web.servlet;

import cn.dsna.util.images.ValidateCode;
import com.qf.domain.Address;
import com.qf.domain.User;
import com.qf.service.AddressService;
import com.qf.service.UserService;
import com.qf.service.impl.AddressServiceImpl;
import com.qf.utils.Base64Utils;
import com.qf.utils.RandomUtils;
import com.qf.utils.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.context.ContextLoader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "userServlet", value = "/userservlet")
public class userServlet extends BaseServlet {
    UserService userService = (UserService) ContextLoader.getCurrentWebApplicationContext().getBean("userServiceImpl");
    public String register(HttpServletRequest request, HttpServletResponse response) {
        String repassword = request.getParameter("repassword");
        User user = new User();
        try {
            BeanUtils.populate(user, request.getParameterMap());
            if (StringUtils.isEmpty(user.getUsername())) {
                request.setAttribute("registerMsg", "用户名不能为空");
                return "/register.jsp";
            }
            if (StringUtils.isEmpty(user.getPassword())) {
                request.setAttribute("registerMsg", "密码不能为空");
                return "/register.jsp";
            }
            if (!user.getPassword().equals(repassword)) {
                request.setAttribute("registerMsg", "两次密码不一致");
                return "/register.jsp";
            }
            if (StringUtils.isEmpty(user.getEmail())) {
                request.setAttribute("registerMsg", "邮箱不能为空");
                return "/register.jsp";
            }
            user.setFlag(0);
            user.setRole(1);
            user.setCode(RandomUtils.createActive());
            //System.out.println(userService.toString());
            userService.register(user);
            return "redirect:/registerSuccess.jsp";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/register.jsp";
    }

    public String checkUserName(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        User user = userService.findByName(username);
        System.out.println(user);
        try {
            if (user != null) {
                response.getWriter().write("1");
            } else {
                response.getWriter().write("0");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String login(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String auto = request.getParameter("auto");
       // String valcode = request.getParameter("valcode");
        if (StringUtils.isEmpty(username)) {
            request.setAttribute("msg", "用户名不能为空");
            return "/login.jsp";
        }
        if (StringUtils.isEmpty(password)) {
            request.setAttribute("msg", "密码不能为空");
            return "/login.jsp";
        }
        User user = userService.login(username, password);
       // System.out.println(user.toString());
        System.out.println(user.toString());
        if (user == null) {
            request.setAttribute("msg", "账号密码错误");
            return "/login.jsp";
        } else {
            if (user.getFlag() != 1) {
                request.setAttribute("msg", "用户尚未激活或禁用");
                return "/login.jsp";
            }
            if (user.getRole() != 1) {
                request.setAttribute("msg", "用户没有权限");
                return "/login.jsp";
            }
            request.getSession().setAttribute("user", user);
            if (auto!=null) {
                String str = username + "#" + password;
                Cookie cookie = new Cookie("userinfo", Base64Utils.encode(str));
                cookie.setMaxAge(60 * 60 * 24 * 14);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
            }
            return "redirect:/index.jsp";
        }
    }

    public String code(HttpServletRequest request, HttpServletResponse response) {
        ValidateCode validateCode = new ValidateCode(150, 45, 4, 25);
        String vcode = validateCode.getCode();
        request.getSession().setAttribute("vcode", vcode);
        try {
            validateCode.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String checkCode(HttpServletRequest request, HttpServletResponse response) {
        String vcode = (String) request.getSession().getAttribute("vcode");
        String code = request.getParameter("code");
        try {
            if(!code.equalsIgnoreCase(vcode)) {
                    response.getWriter().write("1");
            }else {
               // System.out.println("------------");
                response.getWriter().write("0");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String logOut(HttpServletRequest request, HttpServletResponse response){
        request.getSession().removeAttribute("user");
        request.getSession().invalidate();
        Cookie cookie=new Cookie("userinfo", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return "redirect:/index.jsp";
    }
    public String getAddress(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        AddressService addressService=new AddressServiceImpl();
        List<Address> addList = addressService.findByUid(user.getId());
        request.setAttribute("addList", addList);
        return "/self_info.jsp";
    }
    public String addAddress(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String detail = request.getParameter("detail");
        if(StringUtils.isEmpty(name)){
            request.setAttribute("msg", "收件人不能为空");
            return getAddress(request, response);
        }
        if(StringUtils.isEmpty(phone)){
            request.setAttribute("msg", "手机号不能为空");
            return getAddress(request, response);
        }
        if(StringUtils.isEmpty(detail)){
            request.setAttribute("msg", "详细地址不能为空");
            return getAddress(request, response);
        }
        Address address=new Address(null, detail, name, phone, user.getId(), 0);
        AddressService addressService=new AddressServiceImpl();
        addressService.add(address);
        return getAddress(request, response);
    }
    public String defaultAddress(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        String id = request.getParameter("id");
        AddressService addressService=new AddressServiceImpl();
        addressService.updateDefault(Integer.parseInt(id),user.getId());
        return getAddress(request, response);
    }
    public  String deleteAddress(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        String id = request.getParameter("id");
        AddressService addressService=new AddressServiceImpl();
        addressService.delete(Integer.parseInt(id));
        return getAddress(request, response);
    }
    public String updateAddress(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        response.setContentType("text/html;charset=utf-8");
        if(user==null){
            return "redirect:/login.jsp";
        }
        String id = request.getParameter("id");
        String level = request.getParameter("level");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String detail = request.getParameter("detail");
        if(StringUtils.isEmpty(name)){
            try {
                response.getWriter().write("<script type='text/javascript' >alert('收件人不能为空！');windows.location='userservlet?method=getAddress'</script>");
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(StringUtils.isEmpty(phone)){
            try {
                response.getWriter().write("<script type='text/javascript' >alert('电话不能为空！');windows.location='userservlet?method=getAddress'</script>");
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(StringUtils.isEmpty(detail)){
            try {
                response.getWriter().write("<script type='text/javascript' >alert('详细地址不能为空！');windows.location='userservlet?method=getAddress'</script>");
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Address address=new Address(Integer.parseInt(id), detail, name, phone, user.getId(), Integer.parseInt(level));
       // System.out.println(address.toString());
        AddressService addressService=new AddressServiceImpl();
        addressService.update(address);
        return getAddress(request, response);
    }
}
