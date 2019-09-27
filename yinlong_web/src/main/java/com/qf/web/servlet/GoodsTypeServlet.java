package com.qf.web.servlet;

import com.alibaba.fastjson.JSON;
import com.qf.domain.GoodsType;
import com.qf.service.GoodsTypeService;
import org.springframework.web.context.ContextLoader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GoodsTypeServlet", value = "/goodstypeservlet")
public class GoodsTypeServlet extends BaseServlet {
    public String goodstypelist(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("applicatin/json;charset=utf-8");
        GoodsTypeService goodsTypeService = (GoodsTypeService) ContextLoader.getCurrentWebApplicationContext().getBean("goodsTypeServiceImpl");
        List<GoodsType> goodsTypeList = goodsTypeService.getGoodsTypeByLevel(1);
        String json = JSON.toJSONString(goodsTypeList);
        try {
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
