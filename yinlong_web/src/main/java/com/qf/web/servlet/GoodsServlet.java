package com.qf.web.servlet;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.domain.Goods;
import com.qf.service.GoodsService;
import com.qf.utils.StringUtils;
import org.springframework.web.context.ContextLoader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(name = "GoodsServlet", value = "/goodsservlet")
public class GoodsServlet extends BaseServlet {
    private GoodsService goodsService= (GoodsService) ContextLoader.getCurrentWebApplicationContext().getBean("goodsServiceImpl");
  public  String getGoodsListByTypeId(HttpServletRequest request, HttpServletResponse response){
      String typeId = request.getParameter("typeId");
      //System.out.println(typeId);
      String _pageNum = request.getParameter("pageNum");
     // System.out.println(_pageNum);
      String _pageSize = request.getParameter("pageSize");
      //System.out.println(_pageSize);
      int pageNum=1;
      int pageSize=8;
      if(!StringUtils.isEmpty(_pageNum)){
          pageNum=Integer.parseInt(_pageNum);
          if(pageNum<1){
              pageNum=1;
          }
      }
      if(!StringUtils.isEmpty(_pageSize)){
          pageSize=Integer.parseInt(_pageSize);
          if(pageSize<1){
              pageSize=8;
          }
      }
      String condition="";
      if(!StringUtils.isEmpty(typeId)) {
          condition = "typeid=" + typeId;
      }
      try {
          PageHelper.startPage(pageNum, pageSize);
          List<Goods> pageBean = goodsService.findPageByWhere(condition);
          PageInfo<Goods> pageInfo=new PageInfo<>(pageBean);
          //  System.out.println(pageBean.toString());
          request.setAttribute("pageinfo", pageInfo);
          request.setAttribute("typeId", typeId);
          return "/goodsList.jsp";
      } catch (Exception e) {
          e.printStackTrace();
          return "/index.jsp";
      }
  }
  public  String getGoodsById(HttpServletRequest request, HttpServletResponse response){
      String id = request.getParameter("id");
      if(StringUtils.isEmpty(id)){
          return "redirect:/index.jsp";
      }
      Goods goods=goodsService.findById(Integer.parseInt(id));
      request.setAttribute("goods", goods);
      return "/goodsDetail.jsp";
  }
}
