package cn.siixc.server.Impl;

import cn.siixc.dao.OrderDao;
import cn.siixc.domain.Order;
import cn.siixc.domain.Product;
import cn.siixc.server.IOrderService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author siixc
 * @date 2023/6/5 14:38
 */
@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public Order createOrder(Long productId, Long userId) {
        Product product = restTemplate.getForObject("http://product-service/product/"+productId,Product.class);
        log.info("查询到{}号商品的信息,内容是:{}", productId, JSON.toJSONString(product));
        Order order = new Order();
        order.setUid(userId);
        order.setPid(productId);
        order.setPname(product.getPname());
        order.setPprice(product.getPprice());
        order.setNumber(1);
        orderDao.save(order);
        log.info("创建订单成功,订单信息为{}", JSON.toJSONString(order));
        return order;
    }

}
