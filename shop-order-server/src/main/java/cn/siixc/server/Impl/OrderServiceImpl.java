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
    @Autowired
    private DiscoveryClient discoveryClient;
    int i = 0;

    @Override
    public Order createOrder(Long productId, Long userId) {
        log.info("接收到{}号商品的下单请求，接下来调用商品微服务查询此商品信息", productId);
        List<ServiceInstance> instances = discoveryClient.getInstances("product-server");
        i++;
        int index = i% instances.size();
        ServiceInstance serviceInstance = instances.get(index);
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        String url = "http://" + host + ":" + port + "/product/" + productId;
        log.info("服务的地址:{}", url);
        Product product = restTemplate.getForObject(url, Product.class);
        log.info("查询到{}号商品的信息,内容是:{}", product, JSON.toJSONString(product));
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
