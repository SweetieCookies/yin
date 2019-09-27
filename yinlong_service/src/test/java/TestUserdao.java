import com.qf.domain.Cart;
import com.qf.domain.Goods;
import com.qf.domain.GoodsType;
import com.qf.domain.User;
import com.qf.service.CartService;
import com.qf.service.GoodsService;
import com.qf.service.GoodsTypeService;
import com.qf.service.UserService;
import com.qf.utils.RandomUtils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.List;

public class TestUserdao {
    @Test
    public void testFind(){
       ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = (UserService) applicationContext.getBean("userServiceImpl");
        User user = userService.findByName("yinlong");
        System.out.println(user.toString());
    }
    @Test
    public  void testlogin(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userServicel = (UserService) context.getBean("userServiceImpl");
        User yinlong = userServicel.login("yinlong", "123456");
        System.out.println(yinlong.toString());
    }
    @Test
    public void testAdd(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userServicel = (UserService) context.getBean("userServiceImpl");
        userServicel.register(new User(null, "qiusi", "123456", "3255193457@qq.com","女", 1, 1, RandomUtils.createActive()));
    }
    @Test
    public  void testFindGoodByTypeId(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        GoodsTypeService goodsTypeService = (GoodsTypeService) context.getBean("goodsTypeServiceImpl");
        List<GoodsType> goodsTypeByLevel = goodsTypeService.getGoodsTypeByLevel(1);
        for (GoodsType goodsType : goodsTypeByLevel) {
            System.out.println(goodsType.toString());
        }
    }
    @Test
    public void testGoods(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        GoodsService goodsService = (GoodsService) context.getBean("goodsServiceImpl");
    /*    List<Goods> pageByWhere = goodsService.findPageByWhere("typeId=1");
        for (Goods goods : pageByWhere) {
            System.out.println(goods.toString());
        }*/
        Goods goods = goodsService.findById(2);
        System.out.println(goods.toString());
    }
    @Test
    public void testAdd1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        CartService cartService = (CartService) context.getBean("cartServiceImpl");
        cartService.addcarts(new Cart(30, 14, 1, new BigDecimal(2563)));
    }
}
