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
    @Autowired
    private DiscoveryClient discoveryClient;
    @Override
    public Order createOrder(long productId, Long userId) {
        log.info("接收到{}号商品的下单请求，接下来调用商品微服务查询此商品信息",productId);
        //从nacos中获取服务地址
        ServiceInstance instance = discoveryClient.getInstances("product-service").get(0);
        String url = instance.getHost()+":"+instance.getPort();
        //远程调用商品微服务，查询商品信息
        Product product =  restTemplate.getForObject("http://"+url+"/product"+productId,Product.class);
        log.info("查询到{}号商品信息,内容是:{}",productId, JSON.toJSONString(product));
        Order order = new Order();
        order.setUid(userId);
        order.setPid(productId);
        order.setPname(product.getPname());
        order.setPprice(product.getPprice());
        order.setNumber(1);
        orderDao.save(order);
        log.info("创建订单成功,订单信息为{}",JSON.toJSONString(order));
        return order;
        }
}
